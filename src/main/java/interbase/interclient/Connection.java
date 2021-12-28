package interbase.interclient;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Struct;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.Executor;

public final class Connection implements java.sql.Connection {
   String database_;
   String serverName_;
   int port_;
   Properties properties_;
   boolean open_ = true;
   private boolean retaining_ = false;
   boolean transactionStartedOnServer_ = false;
   DatabaseMetaData databaseMetaData_;
   Vector openStatements_;
   Vector openPreparedStatements_;
   private Vector openSavepoints;
   private int savepointId = 0;
   private interbase.interclient.IBException sqlWarnings_ = null;
   JavaNIOByteToCharConverters btc_;
   Charset ctb_;
   interbase.interclient.IBConnectionRequestInfo ibCri = new interbase.interclient.IBConnectionRequestInfo();
   final interbase.interclient.Ibase ibase_ = new interbase.interclient.Ibase();
   IscDbHandle db_;
   interbase.interclient.IscTrHandle tra_;
   interbase.interclient.IscTrHandle traDdl_ = null;
   private TransactionProperties mainTraProps = null;
   private TransactionProperties ddlTraProps = null;
   Set tpb_ = new HashSet();
   private int resultType_ = 1003;
   int resultSetConcurrency_ = 1007;
   String ianaCharacterEncoding_;
   boolean charsetWasNone;
   private boolean createDatabase_;
   private static final String defaultEncoding__ = "8859_1";
   public static final int TRANSACTION_NONE = 0;
   public static final int TRANSACTION_READ_UNCOMMITTED = 1;
   public static final int TRANSACTION_READ_COMMITTED = 2;
   public static final int TRANSACTION_REPEATABLE_READ = 4;
   public static final int TRANSACTION_SERIALIZABLE = 8;
   public static final int TRANSACTION_SNAPSHOT = 8;
   public static final int TRANSACTION_SNAPSHOT_TABLE_STABILITY = 16;
   public static final int LOCK_RESOLUTION_WAIT = 0;
   public static final int LOCK_RESOLUTION_NO_WAIT = 1;
   public static final int IGNORE_UNCOMMITTED_RECORD_VERSIONS_ON_READ = 1;
   public static final int RECOGNIZE_UNCOMMITTED_RECORD_VERSIONS_ON_READ = 0;
   public static final int TABLELOCK_SHARED_WRITE = 0;
   public static final int TABLELOCK_SHARED_READ = 1;
   public static final int TABLELOCK_PROTECTED_WRITE = 2;
   public static final int TABLELOCK_PROTECTED_READ = 4;

   Connection(int var1, String var2, int var3, String var4, Properties var5) throws SQLException {
      this.serverName_ = var2;
      this.port_ = var3;
      this.database_ = var4;
      this.properties_ = (Properties)var5.clone();
      this.println("CONNECT{4.8.1}: server:" + var2 + " database:" + var4 + " port:" + var3 + " socket time out:" + var1, this);
      if (this.properties_ == null) {
         throw new interbase.interclient.InvalidArgumentException(interbase.interclient.ErrorKey.invalidArgument__connection_properties__null__);
      } else {
         this.addRequiredPropertiesAndSetConverters();
         this.openStatements_ = new Vector();
         this.openPreparedStatements_ = new Vector();
         this.connect(var1);
      }
   }

   private void addRequiredPropertiesAndSetConverters() {
      this.ianaCharacterEncoding_ = (String)this.properties_.get("charSet");
      if (this.ianaCharacterEncoding_ != null && !this.ianaCharacterEncoding_.equals("NONE")) {
         this.charsetWasNone = false;
      } else {
         this.ianaCharacterEncoding_ = "8859_1";
         this.properties_.put("charSet", "NONE");
         this.charsetWasNone = true;
      }

      Boolean var1 = null;
      var1 = new Boolean(this.properties_.getProperty("createDatabase"));
      if (var1 != null && var1.booleanValue()) {
         this.createDatabase_ = true;
      } else {
         this.createDatabase_ = false;
      }

   }

   void checkForClosedConnection() throws SQLException {
      if (!this.open_) {
         throw new InvalidOperationException(interbase.interclient.ErrorKey.invalidOperation__connection_closed__);
      }
   }

   private void connect(int var1) throws SQLException {
      try {
         this.btc_ = new JavaNIOByteToCharConverters(this.ianaCharacterEncoding_, true, true);
         this.ctb_ = Charset.forName(this.ianaCharacterEncoding_);
      } catch (UnsupportedEncodingException var7) {
         throw new UnsupportedCharacterSetException(interbase.interclient.ErrorKey.unsupportedCharacterSet__0__, this.ianaCharacterEncoding_);
      }

      new Date();

      try {
         this.databaseMetaData_ = new DatabaseMetaData(this);
         this.remote_ATTACH_DATABASE(var1);
         this.mainTraProps = new TransactionProperties();
      } catch (interbase.interclient.IBException var5) {
         throw new SQLException(var5.getMessage());
      } catch (SQLException var6) {
         throw var6;
      }
   }

   static int setConnectionRequestInfo(Properties var0, interbase.interclient.IBConnectionRequestInfo var1) throws SQLException {
      int var2 = 0;
      int var3 = 0;

      for(Enumeration var4 = var0.propertyNames(); var4.hasMoreElements(); ++var2) {
         var4.nextElement();
      }

      Enumeration var5 = var0.propertyNames();

      while(var5.hasMoreElements()) {
         String var6 = (String)var5.nextElement();
         if (var6.equals("user")) {
            var1.setUser(var0.getProperty(var6).toUpperCase());
         } else if (var6.equals("password")) {
            var1.setPassword((String)var0.get("password"));
         } else if (var6.equals("roleName")) {
            var1.setProperty(60, (String)var0.getProperty(var6).toUpperCase());
         } else if (var6.equals("charSet")) {
            var1.setProperty(48, (String) CharacterEncodings.getInterBaseCharacterSetName(var0.getProperty(var6)));
         } else {
            int var11;
            if (var6.equalsIgnoreCase("sqlDialect")) {
               boolean var7 = false;

               try {
                  var11 = Integer.parseInt(var0.getProperty(var6));
               } catch (NumberFormatException var10) {
                  var11 = ((Integer)var0.get(var6)).intValue();
               }

               if (var11 < 0) {
                  var11 = 1;
               } else if (var11 > 3) {
                  var11 = 3;
               }

               var1.setProperty(63, var11);
            } else if (var6.equals("portNumber")) {
               try {
                  var3 = Integer.parseInt(var0.getProperty(var6));
               } catch (NumberFormatException var9) {
                  var3 = ((Integer)var0.get(var6)).intValue();
               }
            } else if (var6.equals("interBaseLicense")) {
               var1.setProperty(18, (String)var0.getProperty(var6));
            } else if (var6.equals("sweepOnConnect")) {
               if (var0.getProperty(var6).toLowerCase().equals("true")) {
                  var1.setProperty(10, 1);
               }
            } else if (var6.equals("suggestedCachePages")) {
               var11 = Integer.parseInt(var0.getProperty(var6));
               if (var11 > 255) {
                  var11 = 255;
               }

               if (var11 != 0) {
                  var1.setProperty(5, var11);
               }
            }
         }
      }

      return var3;
   }

   private void remote_ATTACH_DATABASE(int var1) throws SQLException, interbase.interclient.IBException {
      try {
         if (this.charsetWasNone) {
            this.db_ = this.ibase_.getNewIscDbHandle((String)null);
         } else {
            this.db_ = this.ibase_.getNewIscDbHandle(this.ianaCharacterEncoding_);
         }

         int var2 = setConnectionRequestInfo(this.properties_, this.ibCri);
         if (var2 != 0) {
            this.port_ = var2;
         }

         this.databaseMetaData_.userName_ = this.ibCri.getUser();
         byte[] var3 = this.ibCri.getProperty(63);
         if (var3 != null) {
            this.databaseMetaData_.attachmentSQLDialect_ = var3[0];
         } else {
            this.databaseMetaData_.attachmentSQLDialect_ = 0;
         }

         try {
            this.println("Attach database", this);
            this.ibase_.iscAttachDatabase(this.database_, this.serverName_, this.port_, var1, this.db_, this.ibCri.getDpb(), this.sqlWarnings_);
         } catch (interbase.interclient.IBException var10) {
            if (!this.createDatabase_) {
               throw var10;
            }

            this.println("Create database", this);
            this.ibase_.iscCreateDatabase(this.database_, this.serverName_, this.port_, var1, this.db_, this.ibCri.getDpb(), this.sqlWarnings_);
         }

         byte[] var10000 = new byte[10];
         interbase.interclient.Ibase var10003 = this.ibase_;
         var10000[0] = 12;
         var10003 = this.ibase_;
         var10000[1] = 32;
         var10003 = this.ibase_;
         var10000[2] = 33;
         var10003 = this.ibase_;
         var10000[3] = 14;
         var10003 = this.ibase_;
         var10000[4] = 21;
         var10003 = this.ibase_;
         var10000[5] = 13;
         var10003 = this.ibase_;
         var10000[6] = 62;
         var10003 = this.ibase_;
         var10000[7] = 63;
         var10003 = this.ibase_;
         var10000[8] = 71;
         var10003 = this.ibase_;
         var10000[9] = 1;
         byte[] var4 = var10000;
         var10003 = this.ibase_;
         byte[] var5 = this.ibase_.iscDatabaseInfo(this.db_, var4, 1024, this.sqlWarnings_);
         byte var13 = var5[0];
         interbase.interclient.Ibase var10001 = this.ibase_;
         if (var13 != 3) {
            var13 = var5[0];
            var10001 = this.ibase_;
            if (var13 == 2) {
               ;
            }
         }

         int var6 = 0;

         while(true) {
            var13 = var5[var6];
            var10001 = this.ibase_;
            int var8;
            if (var13 == 1) {
               if (this.databaseMetaData_.odsMajorVersion_ < 10) {
                  this.databaseMetaData_.databaseReadOnly_ = false;
                  this.databaseMetaData_.databaseSQLDialect_ = 1;
                  this.databaseMetaData_.attachmentSQLDialect_ = this.databaseMetaData_.databaseSQLDialect_;
               } else {
                  byte[] var12 = this.ibCri.getProperty(63);
                  if (var12 != null) {
                     var8 = interbase.interclient.Ibase.iscVaxInteger(var12, 0, 4);
                     if (var8 <= 3 && var8 >= 1) {
                        this.databaseMetaData_.attachmentSQLDialect_ = var8;
                     } else {
                        this.databaseMetaData_.attachmentSQLDialect_ = this.databaseMetaData_.databaseSQLDialect_;
                     }
                  } else {
                     this.databaseMetaData_.attachmentSQLDialect_ = this.databaseMetaData_.databaseSQLDialect_;
                  }
               }

               if (!this.isCompatibleIBVersion(this.databaseMetaData_.databaseProductVersion_)) {
                  throw new UnlicensedComponentException(interbase.interclient.ErrorKey.unlicensedComponent__incompatible_ibversion_0__, this.databaseMetaData_.databaseProductVersion_);
               } else {
                  return;
               }
            }

            interbase.interclient.Ibase var14 = this.ibase_;
            int var7 = interbase.interclient.Ibase.iscVaxInteger(var5, var6 + 1, 2);
            var14 = this.ibase_;
            if (12 == var5[var6]) {
               try {
                  var14 = this.ibase_;
                  var8 = interbase.interclient.Ibase.iscVaxInteger(var5, var6 + 4, 1);
                  this.databaseMetaData_.databaseProductVersion_ = new String(var5, var6 + 3 + 3 + var8, var7 - 3 - var8, "8859_1");
               } catch (UnsupportedEncodingException var9) {
                  throw new SQLException("unsupported encoding ");
               }
            } else {
               var14 = this.ibase_;
               if (32 == var5[var6]) {
                  var10001 = this.ibase_;
                  this.databaseMetaData_.odsMajorVersion_ = interbase.interclient.Ibase.iscVaxInteger(var5, var6 + 3, var7);
               } else {
                  var14 = this.ibase_;
                  if (33 == var5[var6]) {
                     var10001 = this.ibase_;
                     this.databaseMetaData_.odsMinorVersion_ = interbase.interclient.Ibase.iscVaxInteger(var5, var6 + 3, var7);
                  } else {
                     var14 = this.ibase_;
                     if (14 == var5[var6]) {
                        var10001 = this.ibase_;
                        this.databaseMetaData_.pageSize_ = interbase.interclient.Ibase.iscVaxInteger(var5, var6 + 3, var7);
                     } else {
                        var14 = this.ibase_;
                        if (21 == var5[var6]) {
                           var10001 = this.ibase_;
                           this.databaseMetaData_.pageAllocation_ = interbase.interclient.Ibase.iscVaxInteger(var5, var6 + 3, var7);
                        } else {
                           var14 = this.ibase_;
                           if (13 == var5[var6]) {
                              this.databaseMetaData_.ibMajorVersion_ = var5[var6 + 3 + 1];
                           } else {
                              var14 = this.ibase_;
                              if (62 == var5[var6]) {
                                 var10001 = this.ibase_;
                                 this.databaseMetaData_.databaseSQLDialect_ = interbase.interclient.Ibase.iscVaxInteger(var5, var6 + 3, var7);
                              } else {
                                 var14 = this.ibase_;
                                 if (63 == var5[var6]) {
                                    var14 = this.ibase_;
                                    var8 = interbase.interclient.Ibase.iscVaxInteger(var5, var6 + 3, var7);
                                    this.databaseMetaData_.databaseReadOnly_ = var8 > 0;
                                 } else {
                                    var14 = this.ibase_;
                                    if (71 == var5[var6]) {
                                       this.databaseMetaData_.ibMinorVersion_ = var5[var6 + 3 + 1];
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }

            var6 = var6 + 3 + var7;
         }
      } catch (interbase.interclient.IBException var11) {
         throw new SQLException(var11.toString(), interbase.interclient.ErrorKey.getIBSQLState(), var11.getErrorCode());
      }
   }

   private boolean isCompatibleIBVersion(String var1) {
      for(int var2 = 0; var2 < Globals.compatibleIBVersions__.length; ++var2) {
         if (this.databaseMetaData_.ibMajorVersion_ == Globals.compatibleIBVersions__[var2]) {
            return true;
         }
      }

      int var3 = var1.indexOf(45);

      for(int var4 = 0; var4 < Globals.compatibleIBVersions__.length; ++var4) {
         if (var1.charAt(var3 + 2) == String.valueOf(Globals.compatibleIBVersions__[var4]).charAt(0)) {
            return true;
         }
      }

      return false;
   }

   protected void finalize() throws Throwable {
      if (this.open_) {
         this.close();
      }

      super.finalize();
   }

   public synchronized java.sql.Statement createStatement() throws SQLException {
      return this.createStatement(1003, 1007);
   }

   public synchronized java.sql.PreparedStatement prepareStatement(String var1) throws SQLException {
      return this.prepareStatement(var1, 1003, 1007);
   }

   protected java.sql.PreparedStatement prepareStatement(int var1, String var2) throws SQLException {
      return this.prepareStatement(this.getAttachmentSQLDialect(), var2, 1003, 1007);
   }

   public synchronized java.sql.PreparedStatement prepareStatement(String var1, int[] var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized java.sql.PreparedStatement prepareStatement(String var1, String[] var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   @Override
   public Clob createClob() throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   @Override
   public Blob createBlob() throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   @Override
   public NClob createNClob() throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   @Override
   public SQLXML createSQLXML() throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   @Override
   public boolean isValid(int timeout) throws SQLException {
      //TODO::implement real method
      return true;
//      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   @Override
   public void setClientInfo(String name, String value) throws SQLClientInfoException {
      throw new SQLClientInfoException("decompiled", Map.of());
   }

   @Override
   public void setClientInfo(Properties properties) throws SQLClientInfoException {
      throw new SQLClientInfoException("decompiled", Map.of());
   }

   @Override
   public String getClientInfo(String name) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   @Override
   public Properties getClientInfo() throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   @Override
   public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   @Override
   public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   @Override
   public void setSchema(String schema) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   @Override
   public String getSchema() throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   @Override
   public void abort(Executor executor) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   @Override
   public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   @Override
   public int getNetworkTimeout() throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized java.sql.PreparedStatement prepareStatement(String var1, int var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized java.sql.CallableStatement prepareCall(String var1, int var2, int var3, int var4) throws SQLException {
      if (var4 == 2) {
         return this.prepareCall(var1, var2, var3);
      } else {
         throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
      }
   }

   public synchronized java.sql.CallableStatement prepareCall(String var1) throws SQLException {
      return this.prepareCall(var1, 1003, 1007);
   }

   public synchronized String nativeSQL(String var1) throws SQLException {
      interbase.interclient.EscapeProcessor var2 = new interbase.interclient.EscapeProcessor();
      return var2.doEscapeProcessing(var1, this.getAttachmentSQLDialect());
   }

   public synchronized void setAutoCommit(boolean var1) throws SQLException {
      this.checkForClosedConnection();
      this.mainTraProps.setEnableAutoCommit(var1);
      if (this.transactionStartedOnServer_) {
         this.remote_COMMIT(false);
         this.local_CloseResultSets(this.openStatements_);
         this.local_CloseResultSets(this.openPreparedStatements_);
         this.transactionStartedOnServer_ = false;
      }
   }

   public synchronized boolean getAutoCommit() throws SQLException {
      return this.mainTraProps.isEnableAutoCommit();
   }

   private void local_CloseStatements(Vector var1) throws SQLException {
      while(var1.size() != 0) {
         ((Statement)var1.lastElement()).local_Close();
      }

   }

   private void local_CloseResultSets(Vector var1) throws SQLException {
      Enumeration var2 = var1.elements();

      while(var2.hasMoreElements()) {
         ResultSet var3 = ((Statement)var2.nextElement()).resultSet_;
         if (var3 != null) {
            var3.local_Close();
         }
      }

   }

   private void closeResultSets(Vector var1) throws SQLException {
      Enumeration var2 = var1.elements();

      while(var2.hasMoreElements()) {
         Statement var3 = (Statement)var2.nextElement();
         if (var3.resultSet_ != null) {
            var3.resultSet_.close();
            var3.resultSet_ = null;
            var3.resultSetStack_ = null;
         }
      }

   }

   void local_Close() throws SQLException {
      this.local_CloseStatements(this.openStatements_);
      this.local_CloseStatements(this.openPreparedStatements_);
      this.open_ = false;
      Object var1 = null;
      Object var2 = null;
   }

   public synchronized void commit() throws SQLException {
      this.checkForClosedConnection();
      if (!this.getAutoCommit()) {
         if (this.transactionStartedOnServer_) {
            if (this.traDdl_ != null) {
               this.remoteCommitDdl(false);
            }

            this.remote_COMMIT(this.retaining_);
            this.clearSavepoints();
         }
      }
   }

   private void clearSavepoints() throws interbase.interclient.IBException, SQLException {
      if (this.openSavepoints != null) {
         this.openSavepoints.removeAllElements();
         this.openSavepoints = null;
      }

   }

   void remoteCommitDdl(boolean var1) throws SQLException {
      if (this.traDdl_ != null) {
         if (var1) {
            this.ibase_.iscCommitRetaining(this.traDdl_, this.sqlWarnings_);
         } else {
            this.ibase_.iscCommitTransaction(this.traDdl_, this.sqlWarnings_);
            this.traDdl_ = null;
         }
      }

   }

   void remoteRollbackDdl(boolean var1) throws SQLException {
      if (this.traDdl_ != null) {
         if (var1) {
            this.ibase_.iscRollbackRetaining(this.traDdl_, this.sqlWarnings_);
         } else {
            this.ibase_.iscRollbackTransaction(this.traDdl_, this.sqlWarnings_);
            this.traDdl_ = null;
         }
      }

   }

   void remote_COMMIT(boolean var1) throws SQLException {
      if (this.tra_ == null) {
         this.transactionStartedOnServer_ = false;
         this.tra_ = null;
      } else {
         try {
            this.closeResultSets(this.openStatements_);
            this.closeResultSets(this.openPreparedStatements_);
            if (!var1) {
               this.ibase_.iscCommitTransaction(this.tra_, this.sqlWarnings_);
               this.transactionStartedOnServer_ = false;
               this.tra_ = null;
            } else {
               this.ibase_.iscCommitRetaining(this.tra_, this.sqlWarnings_);
            }

         } catch (interbase.interclient.IBException var3) {
            throw new SQLException(var3.toString());
         }
      }
   }

   public synchronized void rollback() throws SQLException {
      this.checkForClosedConnection();
      if (!this.getAutoCommit()) {
         if (this.transactionStartedOnServer_) {
            if (this.traDdl_ != null) {
               this.remoteRollbackDdl(false);
            }

            this.remote_ROLLBACK(this.retaining_);
            this.clearSavepoints();
         }
      }
   }

   public synchronized void rollbackRetaining() throws SQLException {
      this.checkForClosedConnection();
      if (this.getAutoCommit()) {
         throw new InvalidOperationException(interbase.interclient.ErrorKey.invalidOperation__commit_or_rollback_under_autocommit__);
      } else if (this.transactionStartedOnServer_) {
         this.remote_ROLLBACK(true);
      }
   }

   void remote_ROLLBACK(boolean var1) throws SQLException {
      if (this.tra_ == null) {
         this.transactionStartedOnServer_ = false;
      } else {
         try {
            this.closeResultSets(this.openStatements_);
            this.closeResultSets(this.openPreparedStatements_);
            if (!var1) {
               this.ibase_.iscRollbackTransaction(this.tra_, this.sqlWarnings_);
               this.transactionStartedOnServer_ = false;
               this.tra_ = null;
            } else {
               this.ibase_.iscRollbackRetaining(this.tra_, this.sqlWarnings_);
            }

         } catch (interbase.interclient.IBException var3) {
            throw new SQLException(var3.toString());
         }
      }
   }

   public synchronized void close() throws SQLException {
      this.println("CLOSE", this);
      if (this.open_) {
         SQLException var1 = null;

         try {
            if (this.transactionStartedOnServer_ && this.tra_ != null) {
               this.ibase_.iscRollbackTransaction(this.tra_, this.sqlWarnings_);
               this.tra_ = null;
               this.transactionStartedOnServer_ = false;
            }

            if (this.traDdl_ != null) {
               this.remoteRollbackDdl(false);
            }

            this.remote_DETACH_DATABASE();
         } catch (interbase.interclient.IBException var4) {
            SQLException var3 = new SQLException(var4.getMessage());
            var1 = Utils.accumulateSQLExceptions(var1, var3);
         } catch (SQLException var5) {
            var1 = Utils.accumulateSQLExceptions(var1, var5);
         }

         this.local_Close();
         if (var1 != null) {
            throw var1;
         }
      }
   }

   private void remote_DETACH_DATABASE() throws SQLException, interbase.interclient.IBException {
      try {
         this.ibase_.iscDetachDatabase(this.db_, this.sqlWarnings_);
      } catch (interbase.interclient.IBException var2) {
         throw var2;
      }
   }

   public synchronized boolean isClosed() throws SQLException {
      return !this.open_;
   }

   public synchronized java.sql.DatabaseMetaData getMetaData() throws SQLException {
      return this.databaseMetaData_;
   }

   public synchronized void setReadOnly(boolean var1) throws SQLException {
      this.checkForClosedConnection();
      if (this.transactionStartedOnServer_) {
         throw new InvalidOperationException(interbase.interclient.ErrorKey.invalidOperation__transaction_in_progress__);
      } else {
         this.mainTraProps.setReadOnly(var1);
      }
   }

   public synchronized boolean isReadOnly() throws SQLException {
      return this.mainTraProps.isReadOnly();
   }

   public void setCatalog(String var1) throws SQLException {
   }

   public synchronized String getCatalog() throws SQLException {
      return null;
   }

   public synchronized void setTransactionIsolation(int var1) throws SQLException {
      this.mainTraProps.setIsolation(var1);
   }

   public synchronized int getTransactionIsolation() throws SQLException {
      return this.mainTraProps.getIsolation();
   }

   public synchronized java.sql.SQLWarning getWarnings() throws SQLException {
      return this.sqlWarnings_ != null ? new java.sql.SQLWarning(this.sqlWarnings_.getMessage()) : null;
   }

   public synchronized void clearWarnings() throws SQLException {
      this.sqlWarnings_ = null;
   }

   void setTransaction() throws SQLException {
      try {
         if (!this.transactionStartedOnServer_ && this.tra_ == null) {
            this.tra_ = new interbase.interclient.IscTrHandle();
            this.mainTraProps.setTransactionConfigData();
            this.ibase_.iscStartTransaction(this.tra_, this.db_, this.tpb_, this.sqlWarnings_);
            this.transactionStartedOnServer_ = true;
         }

      } catch (interbase.interclient.IBException var2) {
         throw new SQLException(var2.toString());
      }
   }

   void setDdlTransaction() throws SQLException {
      try {
         if (this.traDdl_ == null) {
            this.traDdl_ = new interbase.interclient.IscTrHandle();
            if (this.ddlTraProps == null) {
               this.ddlTraProps = new TransactionProperties(false, 2, true, 0, false);
            }

            this.ddlTraProps.setTransactionConfigData();
            this.ibase_.iscStartTransaction(this.traDdl_, this.db_, this.tpb_, this.sqlWarnings_);
         }

      } catch (interbase.interclient.IBException var2) {
         throw new SQLException(var2.toString());
      }
   }

   public synchronized java.sql.Statement createStatement(int var1, int var2) throws SQLException {
      this.checkForClosedConnection();
      if (var1 != 1005 && var2 == 1007) {
         this.println("createStatement", this);
         this.resultType_ = var1;
         this.resultSetConcurrency_ = var2;
         Statement var3 = new Statement(this);
         this.openStatements_.addElement(var3);
         var3.resultSetType_ = var1;
         return var3;
      } else {
         throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__invalid_conccurency_scrolling__);
      }
   }

   public synchronized java.sql.Statement createStatement(int var1, int var2, int var3) throws SQLException {
      if (var3 == 2) {
         return this.createStatement(var1, var2);
      } else {
         throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
      }
   }

   public synchronized java.sql.PreparedStatement prepareStatement(String var1, int var2, int var3) throws SQLException {
      return this.prepareStatement(this.getAttachmentSQLDialect(), var1, var2, var3);
   }

   protected java.sql.PreparedStatement prepareStatement(int var1, String var2, int var3, int var4) throws SQLException {
      if (var3 != 1005 && var4 == 1007) {
         this.resultType_ = var3;
         this.resultSetConcurrency_ = var4;
         this.println("prepareStatement sql:" + var2, this);
         this.checkForClosedConnection();
         interbase.interclient.PreparedStatement var5 = new PreparedStatement(this, var2, var1);
         this.openPreparedStatements_.addElement(var5);
         return var5;
      } else {
         throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__invalid_conccurency_scrolling__);
      }
   }

   public synchronized java.sql.PreparedStatement prepareStatement(String var1, int var2, int var3, int var4) throws SQLException {
      if (var4 == 2) {
         return this.prepareStatement(var1, var2, var3);
      } else {
         throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
      }
   }

   public synchronized java.sql.CallableStatement prepareCall(String var1, int var2, int var3) throws SQLException {
      if (var2 != 1005 && var3 == 1007) {
         this.resultType_ = var2;
         this.resultSetConcurrency_ = var3;
         this.println("prepareCall sql:" + var1, this);
         this.checkForClosedConnection();
         CallableStatement var4 = new CallableStatement(this, var1);
         this.openPreparedStatements_.addElement(var4);
         return var4;
      } else {
         throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__invalid_conccurency_scrolling__);
      }
   }

   public synchronized Map getTypeMap() throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setTypeMap(Map var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setLockResolution(int var1) throws SQLException {
      this.mainTraProps.setLockResolution(var1);
   }

   public synchronized int getLockResolution() throws SQLException {
      return this.mainTraProps.getLockResolution();
   }

   public synchronized void setVersionAcknowledgement(int var1) throws SQLException {
      this.checkForClosedConnection();
      if (this.transactionStartedOnServer_) {
         throw new InvalidOperationException(interbase.interclient.ErrorKey.invalidOperation__transaction_in_progress__);
      } else {
         if (var1 == 1) {
            this.mainTraProps.setEnableRecVersion(true);
         } else {
            if (var1 != 0) {
               throw new interbase.interclient.InvalidArgumentException(interbase.interclient.ErrorKey.invalidArgument__version_acknowledgement_mode__);
            }

            this.mainTraProps.setEnableRecVersion(false);
         }

      }
   }

   public synchronized int getVersionAcknowledgement() throws SQLException {
      return this.mainTraProps.isEnableRecVersion() ? 1 : 0;
   }

   public synchronized void setTableLock(String var1, int var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized int getTableLock(String var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized void commitRetain() throws SQLException {
      this.checkForClosedConnection();
      if (!this.mainTraProps.isEnableAutoCommit()) {
         if (this.transactionStartedOnServer_) {
            this.remote_COMMIT(true);
            this.closeResultSets(this.openStatements_);
            this.closeResultSets(this.openPreparedStatements_);
         }
      }
   }

   public synchronized int getTransactionId() throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized boolean inTransaction() throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized int getAttachmentSQLDialect() throws SQLException {
      return this.databaseMetaData_.attachmentSQLDialect_;
   }

   public synchronized void setCommitMode(boolean var1) {
      this.retaining_ = var1;
   }

   final void println(String var1, Connection var2) {
      PrintWriter var3 = DriverManager.getLogWriter();
      if (var3 != null) {
         var3.println("  \"  :" + var1 + " [" + var2 + "]");
         var3.flush();
      }

   }

   public synchronized void setHoldability(int var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized int getHoldability() throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Savepoint setSavepoint() throws SQLException {
      this.setTransaction();
      if (this.getAutoCommit()) {
         throw new interbase.interclient.IBException(225544004);
      } else {
         Savepoint var1 = IBSavepoint.setSavepoint(++this.savepointId, this);
         if (this.openSavepoints == null) {
            this.openSavepoints = new Vector();
         }

         this.openSavepoints.addElement(var1);
         return var1;
      }
   }

   public synchronized Savepoint setSavepoint(String var1) throws SQLException {
      this.setTransaction();
      if (this.getAutoCommit()) {
         throw new interbase.interclient.IBException(225544004);
      } else {
         Savepoint var2 = IBSavepoint.setSavepoint(var1, this);
         if (this.openSavepoints == null) {
            this.openSavepoints = new Vector();
         }

         this.openSavepoints.addElement(var2);
         return var2;
      }
   }

   public synchronized void rollback(Savepoint var1) throws SQLException {
      ((IBSavepoint)var1).rollbackSavepoint();
   }

   public synchronized void releaseSavepoint(Savepoint var1) throws SQLException {
      try {
         ((IBSavepoint)var1).closeSavepoint();
      } catch (SQLException var7) {
         throw var7;
      } finally {
         this.openSavepoints.remove(var1);
      }

   }

   @Override
   public <T> T unwrap(Class<T> iface) throws SQLException {
      return null;
   }

   @Override
   public boolean isWrapperFor(Class<?> iface) throws SQLException {
      return false;
   }

   private class TransactionProperties {
      private boolean readOnly_ = false;
      private int isolation_ = 8;
      private boolean enableRecVersion_ = true;
      private int lockResolution_ = 0;
      private boolean enableAutoCommit_ = true;

      TransactionProperties() {
      }

      TransactionProperties(boolean var2, int var3, boolean var4, int var5, boolean var6) {
         this.readOnly_ = var2;
         this.isolation_ = var3;
         this.enableRecVersion_ = var4;
         this.lockResolution_ = var5;
         this.enableAutoCommit_ = var6;
      }

      private boolean isEnableAutoCommit() {
         return this.enableAutoCommit_;
      }

      private int getLockResolution() {
         return this.lockResolution_;
      }

      private boolean isEnableRecVersion() {
         return this.enableRecVersion_;
      }

      private int getIsolation() {
         return this.isolation_;
      }

      private boolean isReadOnly() {
         return this.readOnly_;
      }

      private void setEnableAutoCommit(boolean var1) {
         this.enableAutoCommit_ = var1;
      }

      private void setLockResolution(int var1) throws SQLException {
         Connection.this.checkForClosedConnection();
         if (Connection.this.transactionStartedOnServer_) {
            throw new InvalidOperationException(interbase.interclient.ErrorKey.invalidOperation__transaction_in_progress__);
         } else {
            switch(var1) {
            case 0:
            case 1:
               this.lockResolution_ = var1;
               return;
            default:
               throw new interbase.interclient.InvalidArgumentException(interbase.interclient.ErrorKey.invalidArgument__lock_resolution__);
            }
         }
      }

      private void setEnableRecVersion(boolean var1) {
         this.enableRecVersion_ = var1;
      }

      private void setIsolation(int var1) throws SQLException {
         Connection.this.checkForClosedConnection();
         if (this.enableAutoCommit_) {
            Connection.this.remote_COMMIT(false);
         }

         if (Connection.this.transactionStartedOnServer_) {
            throw new InvalidOperationException(interbase.interclient.ErrorKey.invalidOperation__transaction_in_progress__);
         } else {
            switch(var1) {
            case 0:
            case 3:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            default:
               throw new InvalidArgumentException(interbase.interclient.ErrorKey.invalidArgument__isolation_0__, String.valueOf(var1));
            case 1:
               throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__isolation__);
            case 2:
            case 4:
            case 8:
            case 16:
               this.isolation_ = var1;
            }
         }
      }

      private void setReadOnly(boolean var1) {
         this.readOnly_ = var1;
      }

      private void setTransactionConfigData() {
         Connection.this.tpb_.clear();
         if (this.readOnly_) {
            Connection.this.tpb_.add(new Integer(8));
         } else {
            Connection.this.tpb_.add(new Integer(9));
         }

         if (this.enableAutoCommit_) {
            Connection.this.tpb_.add(new Integer(16));
            Connection.this.tpb_.add(new Integer(15));
            Connection.this.tpb_.add(new Integer(17));
         } else {
            switch(this.isolation_) {
            case 2:
               Connection.this.tpb_.add(new Integer(15));
               break;
            case 4:
            case 8:
               Connection.this.tpb_.add(new Integer(2));
               break;
            case 16:
               Connection.this.tpb_.add(new Integer(1));
            }

            if (this.enableRecVersion_) {
               Connection.this.tpb_.add(new Integer(17));
            } else {
               Connection.this.tpb_.add(new Integer(18));
            }
         }

         if (this.lockResolution_ == 0) {
            Connection.this.tpb_.add(new Integer(6));
         } else {
            Connection.this.tpb_.add(new Integer(7));
         }

      }
   }
}
