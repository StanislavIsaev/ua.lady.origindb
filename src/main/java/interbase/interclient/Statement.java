package interbase.interclient;

import java.io.PrintWriter;
import java.sql.BatchUpdateException;
import java.sql.DriverManager;
import java.util.ArrayList;

public class Statement implements java.sql.Statement {
   int statementRef_ = 0;
   interbase.interclient.Connection connection_;
   ResultSet resultSet_ = null;
   Integer updateCountStack_ = null;
   ResultSet resultSetStack_ = null;
   boolean openOnClient_ = true;
   boolean openOnServer_ = false;
   int timeout_ = 0;
   int maxRows_ = 0;
   int maxFieldSize_ = 0;
   private int fetchSize_ = 200;
   boolean cancelable = false;
   private boolean isDdl_ = false;
   interbase.interclient.IBException sqlWarnings_ = null;
   protected boolean sqlWarningsCleared_ = false;
   String cursorName_;
   interbase.interclient.IscStmtHandle stmtHandle_ = null;
   int resultSetType_ = 1003;
   boolean escapeProcessingEnabled_ = true;
   private ArrayList batchArray = null;
   private boolean cursorNameSet_ = false;
   private static final String createString = "CREATE ";
   private static final String alterString = "ALTER ";
   private static final String dropString = "DROP ";
   int CLOSE_CURRENT_RESULT = 1;
   int KEEP_CURRENT_RESULT = 2;
   int CLOSE_ALL_RESULTS = 3;
   int SUCCESS_NO_INFO = -2;
   int EXECUTE_FAILED = -3;
   int RETURN_GENERATED_KEYS = 1;
   int NO_GENERATED_KEYS = 2;

   Statement(Connection var1) {
      this.connection_ = var1;
      this.cursorName_ = "";
   }

   void setCannedRows(ArrayList var1) {
      this.stmtHandle_ = new interbase.interclient.IscStmtHandle(var1, this.connection_.databaseMetaData_.ibMajorVersion_, this.connection_.databaseMetaData_.ibMinorVersion_);
   }

   void checkForClosedStatement() throws java.sql.SQLException {
      if (!this.openOnClient_) {
         throw new InvalidOperationException(interbase.interclient.ErrorKey.invalidOperation__statement_closed__);
      }
   }

   void checkForEmptySQL(String var1) throws java.sql.SQLException {
      if (var1 == null || "".equals(var1)) {
         throw new interbase.interclient.InvalidArgumentException(interbase.interclient.ErrorKey.invalidArgument__sql_empty_or_null__);
      }
   }

   protected void finalize() throws Throwable {
      this.println("finalize:", this);
      if (this.openOnServer_) {
         this.close();
      }

      super.finalize();
   }

   public synchronized java.sql.ResultSet executeQuery(String var1) throws java.sql.SQLException {
      this.println("executeQuery: " + var1, this);
      this.checkForClosedStatement();
      this.clearWarnings();
      if (this.resultSet_ != null) {
         this.resultSet_.close();
         this.resultSet_ = null;
         this.resultSetStack_ = null;
      }

      this.updateCountStack_ = null;
      this.checkForEmptySQL(var1);
      if (this.escapeProcessingEnabled_) {
         interbase.interclient.EscapeProcessor var2 = new interbase.interclient.EscapeProcessor();
         var1 = var2.doEscapeProcessing(var1, this.connection_.getAttachmentSQLDialect());
      }

      this.remote_EXECUTE_QUERY_STATEMENT(var1, false);
      this.openOnServer_ = true;
      this.resultSetStack_ = this.resultSet_;
      return this.resultSet_;
   }

   protected void allocateStatement() {
      try {
         if (this.stmtHandle_ == null) {
            if (this.resultSet_ != null) {
               this.resultSet_.close();
               this.resultSet_ = null;
               this.resultSetStack_ = null;
            }

            this.stmtHandle_ = this.connection_.ibase_.getNewIscStmtHandle(this.connection_.databaseMetaData_.ibMajorVersion_, this.connection_.databaseMetaData_.ibMinorVersion_);
            this.sqlWarningsCleared_ = false;
            this.connection_.ibase_.iscDsqlAllocateStatement(this.connection_.db_, this.stmtHandle_, this.sqlWarnings_);
            this.println("allocated statement:", this);
            this.openOnServer_ = true;
         } else if (this.stmtHandle_.out_sqlda != null && this.stmtHandle_.out_sqlda.sqld > 0 && this.resultSet_ != null) {
            this.resultSet_.close();
            this.resultSet_ = null;
            this.resultSetStack_ = null;
            this.stmtHandle_.clearRows();
            this.stmtHandle_.clearSqlda();
            this.println("reuse statement:", this);
         } else if (this.cursorNameSet_) {
            interbase.interclient.Ibase var10002 = this.connection_.ibase_;
            this.connection_.ibase_.iscDsqlFreeStatement(this.stmtHandle_, 1, this.sqlWarnings_);
            this.cursorNameSet_ = false;
            this.println("close cursor:", this);
         }

         if (!"".equals(this.cursorName_)) {
            this.cursorNameSet_ = true;
            this.connection_.ibase_.iscDsqlSetCursorName(this.stmtHandle_, this.cursorName_, 0, this.sqlWarnings_);
            this.println("set cursor name: " + this.cursorName_, this);
         }
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public synchronized interbase.interclient.IscStmtHandle getStmtHandle() {
      return this.stmtHandle_;
   }

   protected void remote_EXECUTE_QUERY_STATEMENT(String var1, boolean var2) throws java.sql.SQLException {
      try {
         this.connection_.setTransaction();
         interbase.interclient.XSQLDA var3;
         if (var2) {
            var3 = this.stmtHandle_.getOutSqlda();
         } else {
            this.allocateStatement();
            var3 = this.connection_.ibase_.iscDsqlPrepare(this.connection_.tra_, this.stmtHandle_, var1, this.connection_.getAttachmentSQLDialect(), this.sqlWarnings_);
         }

         if (var3 != null && var3.sqld > 0) {
            this.run_execute_query(var3);
         } else {
            if (this.resultSet_ != null) {
               this.resultSet_.close();
            }

            throw new InvalidOperationException(interbase.interclient.ErrorKey.invalidOperation__execute_query_on_an_update_statement__);
         }
      } catch (interbase.interclient.IBException var5) {
         if (this.resultSet_ != null) {
            this.resultSet_.close();
         }

         this.resultSet_ = null;
         this.resultSetStack_ = null;
         this.cancelable = false;
         throw new java.sql.SQLException(var5.getMessage(), var5.getSQLState(), var5.getErrorCode());
      } catch (java.sql.SQLException var6) {
         if (this.resultSet_ != null) {
            this.resultSet_.close();
         }

         this.resultSet_ = null;
         this.resultSetStack_ = null;
         this.cancelable = false;
         throw var6;
      }
   }

   private void run_execute_query(interbase.interclient.XSQLDA var1) throws java.sql.SQLException {
      this.cancelable = true;
      this.sqlWarningsCleared_ = false;
      if (this.stmtHandle_.getStmtType() == 8) {
         this.connection_.ibase_.iscDsqlExecute2(this.connection_.tra_, this.stmtHandle_, this.stmtHandle_.getInSqlda(), var1, this.sqlWarnings_);
      } else {
         this.connection_.ibase_.iscDsqlExecute(this.connection_.tra_, this.stmtHandle_, this.stmtHandle_.getInSqlda(), this.sqlWarnings_);
      }

      var1 = this.stmtHandle_.getOutSqlda();
      if (this.stmtHandle_.getOutSqlda().sqld > 0) {
         if (this.resultSet_ != null) {
            this.resultSet_.close();
         }

         this.resultSet_ = new ResultSet(this, true, var1.sqld, var1, this.resultSetType_);
      }

   }

   public synchronized int executeUpdate(String var1) throws java.sql.SQLException {
      this.println("executeUpdate: " + var1, this);
      this.checkForClosedStatement();
      this.clearWarnings();
      if (this.resultSet_ != null) {
         this.resultSet_.close();
         this.resultSet_ = null;
         this.resultSetStack_ = null;
      }

      this.updateCountStack_ = null;
      this.checkForEmptySQL(var1);
      if (this.escapeProcessingEnabled_) {
         interbase.interclient.EscapeProcessor var2 = new interbase.interclient.EscapeProcessor();
         var1 = var2.doEscapeProcessing(var1, this.connection_.getAttachmentSQLDialect());
      }

      int var3 = this.remote_EXECUTE_UPDATE_STATEMENT(var1, false);
      this.openOnServer_ = true;
      this.resultSetStack_ = null;
      this.println("return from executeUpdate: " + var3, this);
      return var3;
   }

   private String checkForBrackets(String var1) {
      StringBuffer var2 = new StringBuffer();
      int var3 = var1.indexOf("{");
      if (var3 != -1) {
         var2.append(var1.substring(0, var3 - 1));
         int var4 = var1.lastIndexOf("}");
         var2.append(var1.substring(var3 + 1, var4));
         var2.append(var1.substring(var4 + 1, var1.length()));
      } else {
         var2.append(var1);
      }

      return var2.toString();
   }

   private boolean checkCommitRollback(String var1) throws java.sql.SQLException {
      if ("COMMIT".equalsIgnoreCase(var1)) {
         if (!this.connection_.getAutoCommit()) {
            this.connection_.commit();
         }

         return true;
      } else if ("ROLLBACK".equalsIgnoreCase(var1)) {
         if (!this.connection_.getAutoCommit()) {
            this.connection_.rollback();
         }

         return true;
      } else {
         return false;
      }
   }

   protected int remote_EXECUTE_UPDATE_STATEMENT(String var1, boolean var2) throws java.sql.SQLException {
      try {
         if (this.checkCommitRollback(var1)) {
            return 0;
         } else {
            interbase.interclient.IscTrHandle var4;
            if (!var2) {
               if (this.doDllStatement(var1)) {
                  return 0;
               }

               this.connection_.setTransaction();
               var4 = this.connection_.tra_;
            } else if (this.isDdl_) {
               this.connection_.setDdlTransaction();
               var4 = this.connection_.traDdl_;
            } else {
               this.connection_.setTransaction();
               var4 = this.connection_.tra_;
            }

            interbase.interclient.XSQLDA var3;
            if (var2) {
               var3 = this.stmtHandle_.getOutSqlda();
            } else {
               this.allocateStatement();
               var3 = this.connection_.ibase_.iscDsqlPrepare(var4, this.stmtHandle_, var1, this.connection_.getAttachmentSQLDialect(), this.sqlWarnings_);
            }

            if (var3.sqld > 0) {
               if (this.resultSet_ != null) {
                  this.resultSet_.close();
                  this.resultSet_ = null;
                  this.resultSetStack_ = null;
               }

               throw new interbase.interclient.InvalidArgumentException(interbase.interclient.ErrorKey.invalidOperation__execute_update_on_an_select_statement__);
            } else {
               return this.run_execute_update(var3, var4);
            }
         }
      } catch (interbase.interclient.IBException var5) {
         if (this.resultSet_ != null) {
            this.resultSet_.close();
         }

         this.resultSet_ = null;
         this.resultSetStack_ = null;
         throw new java.sql.SQLException(var5.getMessage(), var5.getSQLState(), var5.getErrorCode());
      } catch (java.sql.SQLException var6) {
         if (this.resultSet_ != null) {
            this.resultSet_.close();
         }

         this.resultSet_ = null;
         this.resultSetStack_ = null;
         throw var6;
      }
   }

   private boolean doDllStatement(String var1) throws interbase.interclient.IBException, java.sql.SQLException {
      return false;
   }

   protected boolean isDdlStatement(String var1) {
      this.isDdl_ = false;
      return false;
   }

   private int run_execute_update(interbase.interclient.XSQLDA var1, interbase.interclient.IscTrHandle var2) throws java.sql.SQLException {
      this.cancelable = true;
      this.sqlWarningsCleared_ = false;
      this.connection_.ibase_.iscDsqlExecute2(var2, this.stmtHandle_, this.stmtHandle_.getInSqlda(), this.stmtHandle_.getOutSqlda(), this.sqlWarnings_);
      var1 = this.stmtHandle_.getOutSqlda();
      if (var1.sqld > 0) {
         if (this.resultSet_ != null) {
            this.resultSet_.close();
            this.resultSet_ = null;
            this.resultSetStack_ = null;
         }

         throw new interbase.interclient.InvalidArgumentException(interbase.interclient.ErrorKey.invalidOperation__execute_update_on_an_select_statement__);
      } else if (this.stmtHandle_.getStmtType() == 5) {
         if (this.isDdl_) {
            this.connection_.remoteCommitDdl(true);
         } else {
            this.connection_.remote_COMMIT(true);
         }

         boolean var9 = false;
         return 0;
      } else {
         byte[] var4 = new byte[]{23, 1};
         this.sqlWarningsCleared_ = false;
         SqlInfo var6 = new SqlInfo(this.connection_.ibase_.iscDsqlSqlInfo(this.stmtHandle_, var4.length, var4, 128, this.sqlWarnings_), this.connection_.ibase_);
         int var3 = var6.getTotalChangesCount();
         if (this.connection_.getAutoCommit()) {
            try {
               this.connection_.ibase_.iscCommitRetaining(var2, this.sqlWarnings_);
            } catch (interbase.interclient.IBException var8) {
               this.connection_.ibase_.iscRollbackRetaining(var2, this.sqlWarnings_);
            }
         }

         this.cancelable = false;
         if (this.resultSet_ != null) {
            this.resultSet_.close();
            this.resultSet_ = null;
            this.resultSetStack_ = null;
         }

         Object var10 = null;
         var6 = null;
         return var3;
      }
   }

   public synchronized void close() throws java.sql.SQLException {
      this.println("CLOSE", this);
      if (this.openOnClient_) {
         if (this.openOnServer_ || this.stmtHandle_ != null) {
            this.remote_CLOSE_STATEMENT();
         }

         this.local_Close();
      }
   }

   void local_Close() throws java.sql.SQLException {
      if (this.resultSet_ != null) {
         this.resultSet_.local_Close();
      }

      this.openOnClient_ = false;
      this.connection_.openStatements_.removeElement(this);
      this.resultSet_ = null;
      this.resultSetStack_ = null;
      this.sqlWarnings_ = null;
      this.connection_ = null;
      this.batchArray = null;
   }

   void remote_CLOSE_STATEMENT() throws java.sql.SQLException {
      try {
         if (this.stmtHandle_ != null) {
            if (this.resultSet_ != null) {
               this.resultSet_.close();
               this.resultSet_ = null;
               this.resultSetStack_ = null;
            }

            this.sqlWarningsCleared_ = false;
            this.println("free statement:", this);
            interbase.interclient.Ibase var10002 = this.connection_.ibase_;
            this.connection_.ibase_.iscDsqlFreeStatement(this.stmtHandle_, 2, this.sqlWarnings_);
            this.stmtHandle_.clearSqlda();
            this.stmtHandle_.clearRows();
            this.stmtHandle_ = null;
            this.openOnServer_ = false;
         }

      } catch (interbase.interclient.IBException var2) {
         throw new java.sql.SQLException(var2.getMessage(), var2.getSQLState(), var2.getErrorCode());
      }
   }

   void remote_CANCEL_STATEMENT() throws java.sql.SQLException {
      try {
         this.sqlWarningsCleared_ = false;
         interbase.interclient.Ibase var10002 = this.connection_.ibase_;
         this.connection_.ibase_.iscDsqlFreeStatement(this.stmtHandle_, 4, this.sqlWarnings_);
      } catch (interbase.interclient.IBException var2) {
         throw new java.sql.SQLException(var2.getMessage(), var2.getSQLState(), var2.getErrorCode());
      }
   }

   public synchronized int getMaxFieldSize() throws java.sql.SQLException {
      return this.maxFieldSize_;
   }

   public synchronized void setMaxFieldSize(int var1) throws java.sql.SQLException {
      this.checkForClosedStatement();
      if (var1 >= 0) {
         this.maxFieldSize_ = var1;
      } else {
         interbase.interclient.IBException var2 = new interbase.interclient.IBException(225544006);
         throw var2.getSQLExceptionFromIBE();
      }
   }

   public synchronized int getMaxRows() throws java.sql.SQLException {
      return this.maxRows_;
   }

   public synchronized void setMaxRows(int var1) throws java.sql.SQLException {
      this.checkForClosedStatement();
      if (var1 < 0) {
         throw new interbase.interclient.InvalidArgumentException(interbase.interclient.ErrorKey.invalidArgument__negative_max_rows__);
      } else {
         this.maxRows_ = var1;
      }
   }

   public synchronized void setEscapeProcessing(boolean var1) throws java.sql.SQLException {
      this.checkForClosedStatement();
      this.escapeProcessingEnabled_ = var1;
   }

   public synchronized int getQueryTimeout() throws java.sql.SQLException {
      return this.timeout_;
   }

   public synchronized void setQueryTimeout(int var1) throws java.sql.SQLException {
      this.checkForClosedStatement();
      this.timeout_ = var1;
   }

   public void cancel() throws java.sql.SQLException {
      this.checkForClosedStatement();
      if (this.cancelable) {
         this.remote_CANCEL_STATEMENT();
         this.cancelable = false;
      } else {
         throw new InvalidOperationException(interbase.interclient.ErrorKey.invalidOperation__statement_notcancelable__);
      }
   }

   public synchronized java.sql.SQLWarning getWarnings() throws java.sql.SQLException {
      if (this.sqlWarnings_ == null) {
         return this.sqlWarningsCleared_ ? null : new java.sql.SQLWarning();
      } else {
         return new java.sql.SQLWarning(this.sqlWarnings_.getMessage());
      }
   }

   public synchronized void clearWarnings() throws java.sql.SQLException {
      this.sqlWarnings_ = null;
      this.sqlWarningsCleared_ = true;
   }

   public synchronized void setCursorName(String var1) throws java.sql.SQLException {
      this.println("setCursorName: " + var1, this);
      this.checkForClosedStatement();
      if (var1 == null) {
         this.cursorName_ = "";
      } else {
         this.cursorName_ = var1;
      }

   }

   public synchronized String getCursorName() throws java.sql.SQLException {
      this.println("getCursorName: " + this.cursorName_, this);
      return this.cursorName_;
   }

   public synchronized boolean execute(String var1) throws java.sql.SQLException {
      this.println("execute: " + var1, this);
      this.checkForClosedStatement();
      this.clearWarnings();
      if (this.resultSet_ != null) {
         this.resultSet_.close();
         this.resultSet_ = null;
         this.resultSetStack_ = null;
      }

      this.updateCountStack_ = null;
      this.checkForEmptySQL(var1);
      if (this.escapeProcessingEnabled_) {
         interbase.interclient.EscapeProcessor var2 = new interbase.interclient.EscapeProcessor();
         var1 = var2.doEscapeProcessing(var1, this.connection_.getAttachmentSQLDialect());
      }

      boolean var3 = this.remote_EXECUTE_STATEMENT(var1, false);
      this.openOnServer_ = true;
      this.resultSetStack_ = this.resultSet_;
      return var3;
   }

   protected boolean remote_EXECUTE_STATEMENT(String var1, boolean var2) throws java.sql.SQLException {
      try {
         if (this.checkCommitRollback(var1)) {
            return false;
         } else if (!var2 && this.doDllStatement(var1)) {
            this.updateCountStack_ = new Integer(0);
            return false;
         } else {
            interbase.interclient.IscTrHandle var4;
            if (!this.isDdl_) {
               this.connection_.setTransaction();
               var4 = this.connection_.tra_;
            } else {
               this.connection_.setDdlTransaction();
               var4 = this.connection_.traDdl_;
            }

            interbase.interclient.XSQLDA var3;
            if (var2) {
               var3 = this.stmtHandle_.getOutSqlda();
            } else {
               this.allocateStatement();
               var3 = this.connection_.ibase_.iscDsqlPrepare(var4, this.stmtHandle_, var1, this.connection_.getAttachmentSQLDialect(), this.sqlWarnings_);
            }

            if (var3 != null && var3.sqld > 0) {
               this.run_execute_query(var3);
               return true;
            } else {
               this.updateCountStack_ = new Integer(this.run_execute_update(var3, var4));
               return false;
            }
         }
      } catch (interbase.interclient.IBException var5) {
         if (this.resultSet_ != null) {
            this.resultSet_.close();
         }

         this.resultSet_ = null;
         this.resultSetStack_ = null;
         this.updateCountStack_ = null;
         this.cancelable = false;
         throw new java.sql.SQLException(var5.getMessage(), var5.getSQLState(), var5.getErrorCode());
      } catch (java.sql.SQLException var6) {
         if (this.resultSet_ != null) {
            this.resultSet_.close();
         }

         this.resultSet_ = null;
         this.resultSetStack_ = null;
         this.updateCountStack_ = null;
         throw var6;
      }
   }

   public synchronized java.sql.ResultSet getResultSet() throws java.sql.SQLException {
      this.checkForClosedStatement();
      return this.resultSetStack_;
   }

   public synchronized int getUpdateCount() throws java.sql.SQLException {
      this.checkForClosedStatement();
      if (this.updateCountStack_ == null) {
         return -1;
      } else {
         int var1 = this.updateCountStack_.intValue();
         this.updateCountStack_ = null;
         return var1;
      }
   }

   public synchronized boolean getMoreResults() throws java.sql.SQLException {
      this.checkForClosedStatement();
      if (this.resultSet_ != null) {
         this.resultSet_.close();
      }

      this.resultSetStack_ = null;
      return false;
   }

   public synchronized void setFetchDirection(int var1) throws java.sql.SQLException {
      if (var1 != 1000) {
         throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
      }
   }

   public synchronized int getFetchDirection() throws java.sql.SQLException {
      return this.resultSet_ != null ? this.resultSet_.getFetchDirection() : 1000;
   }

   public synchronized void setFetchSize(int var1) throws java.sql.SQLException {
      this.checkForClosedStatement();
      if (var1 < 0) {
         throw new interbase.interclient.InvalidArgumentException(interbase.interclient.ErrorKey.invalidArgument__negative_row_fetch_size__);
      } else if (this.maxRows_ > 0 && var1 > this.maxRows_) {
         throw new InvalidArgumentException(interbase.interclient.ErrorKey.invalidArgument__fetch_size_exceeds_max_rows__);
      } else {
         this.fetchSize_ = var1;
      }
   }

   public synchronized int getFetchSize() throws java.sql.SQLException {
      return this.fetchSize_;
   }

   public synchronized int getResultSetConcurrency() throws java.sql.SQLException {
      return this.resultSet_ != null ? this.resultSet_.getConcurrency() : this.connection_.resultSetConcurrency_;
   }

   public synchronized int getResultSetType() throws java.sql.SQLException {
      return this.resultSet_ != null ? this.resultSet_.getType() : this.resultSetType_;
   }

   public synchronized void addBatch(String var1) throws java.sql.SQLException {
      this.println("addBatch: " + var1, this);
      if (this.batchArray == null) {
         this.batchArray = new ArrayList();
      }

      this.batchArray.add(var1);
   }

   public synchronized void clearBatch() throws java.sql.SQLException {
      this.println("clearBatch: ", this);
      if (this.batchArray != null) {
         this.batchArray.clear();
         this.batchArray = null;
      }

   }

   public synchronized int[] executeBatch() throws BatchUpdateException, java.sql.SQLException {
      this.println("executeBatch: Batch size: " + this.batchArray.size(), this);
      if (this.batchArray == null) {
         int[] var8 = new int[0];
         return var8;
      } else {
         int var1 = this.batchArray.size();
         int[] var2 = new int[var1];
         int var3 = 0;

         try {
            while(var3 < var1) {
               var2[var3] = this.executeUpdate(this.batchArray.get(var3).toString());
               ++var3;
            }

            return var2;
         } catch (java.sql.SQLException var7) {
            int[] var5 = new int[var3];

            for(int var6 = 0; var6 < var3; ++var6) {
               var5[var6] = var2[var6];
            }

            throw new BatchUpdateException(var7.getMessage(), var7.getSQLState(), var5);
         }
      }
   }

   public synchronized java.sql.Connection getConnection() throws java.sql.SQLException {
      return this.connection_;
   }

   final void println(String var1, Statement var2) {
      PrintWriter var3 = DriverManager.getLogWriter();
      if (var3 != null) {
         var3.println("  \"  :  \"  :" + var1 + " [" + var2 + "]");
         var3.flush();
      }

   }

   public synchronized boolean getMoreResults(int var1) throws interbase.interclient.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized java.sql.ResultSet getGeneratedKeys() throws interbase.interclient.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized int executeUpdate(String var1, int var2) throws interbase.interclient.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized int executeUpdate(String var1, int[] var2) throws interbase.interclient.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized int executeUpdate(String var1, String[] var2) throws interbase.interclient.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized boolean execute(String var1, int var2) throws interbase.interclient.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized boolean execute(String var1, int[] var2) throws interbase.interclient.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized boolean execute(String var1, String[] var2) throws interbase.interclient.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized int getResultSetHoldability() throws interbase.interclient.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   @Override
   public boolean isClosed() throws java.sql.SQLException {
      return false;
   }

   @Override
   public void setPoolable(boolean poolable) throws java.sql.SQLException {

   }

   @Override
   public boolean isPoolable() throws java.sql.SQLException {
      return false;
   }

   @Override
   public void closeOnCompletion() throws java.sql.SQLException {

   }

   @Override
   public boolean isCloseOnCompletion() throws java.sql.SQLException {
      return false;
   }

   @Override
   public <T> T unwrap(Class<T> iface) throws java.sql.SQLException {
      return null;
   }

   @Override
   public boolean isWrapperFor(Class<?> iface) throws java.sql.SQLException {
      return false;
   }

   static class SqlInfo {
      private int statementType = -1;
      private int insertCount;
      private int updateCount;
      private int deleteCount;
      private int selectCount;

      SqlInfo(byte[] var1, interbase.interclient.Ibase var2) {
         int var3 = 0;

         while(true) {
            byte var5;
            while((var5 = var1[var3++]) != 1) {
               int var4 = interbase.interclient.Ibase.iscVaxInteger(var1, var3, 2);
               var3 += 2;
               int var6;
               byte var7;
               switch(var5) {
               case 21:
                  this.statementType = interbase.interclient.Ibase.iscVaxInteger(var1, var3, var4);
                  var3 += var4;
                  break;
               case 23:
                  for(; (var7 = var1[var3++]) != 1; var3 += var6) {
                     var6 = interbase.interclient.Ibase.iscVaxInteger(var1, var3, 2);
                     var3 += 2;
                     switch(var7) {
                     case 13:
                        this.selectCount = interbase.interclient.Ibase.iscVaxInteger(var1, var3, var6);
                        break;
                     case 14:
                        this.insertCount = interbase.interclient.Ibase.iscVaxInteger(var1, var3, var6);
                        break;
                     case 15:
                        this.updateCount = interbase.interclient.Ibase.iscVaxInteger(var1, var3, var6);
                        break;
                     case 16:
                        this.deleteCount = interbase.interclient.Ibase.iscVaxInteger(var1, var3, var6);
                     }
                  }
                  break;
               default:
                  var3 += var4;
               }
            }

            return;
         }
      }

      int getStatementType() {
         return this.statementType;
      }

      int getInsertCount() {
         return this.insertCount;
      }

      int getUpdateCount() {
         return this.updateCount;
      }

      int getDeleteCount() {
         return this.deleteCount;
      }

      int getSelectCount() {
         return this.selectCount;
      }

      int getTotalChangesCount() {
         return this.insertCount + this.deleteCount + this.updateCount;
      }
   }
}
