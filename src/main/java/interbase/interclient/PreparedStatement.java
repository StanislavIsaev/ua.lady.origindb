package interbase.interclient;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.BatchUpdateException;
import java.sql.DataTruncation;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class PreparedStatement extends Statement implements java.sql.PreparedStatement {
   String sql_;
   private boolean isEscapedProcedureCall_ = false;
   boolean sendInputs_ = false;
   int inputCols_;
   boolean[] inputNullables_;
   int[] inputTypes_;
   int[] inputPrecisions_;
   int[] inputScales_;
   int[] inputCharSets_;
   int[] inputCharLengths_;
   interbase.interclient.ArrayDescriptor[] arrayDescriptors_;
   char[] cbuf_;
   byte[] encodingBuf_;
   java.sql.ParameterMetaData parameterMetaData_ = null;
   interbase.interclient.ParameterMetaData ibParameterMetaData = null;
   Object[] inputs_;
   private static final BigDecimal bdMaxShortValue = new BigDecimal(32767.0D);
   private static final BigDecimal bdMinShortValue = new BigDecimal(-32768.0D);
   private static final BigDecimal bdMaxIntValue = new BigDecimal(2.147483647E9D);
   private static final BigDecimal bdMinIntValue = new BigDecimal(-2.147483648E9D);
   private static final BigDecimal bdMaxFloatValue = new BigDecimal(3.4028234663852886E38D);
   private static final BigDecimal bdMinFloatValue = new BigDecimal(-3.4028234663852886E38D);
   private static final BigDecimal bdMaxDoubleValue = new BigDecimal(Double.MAX_VALUE);
   private static final BigDecimal bdMinDoubleValue = new BigDecimal(-1.7976931348623157E308D);
   private static final BigDecimal bdMaxLongValue = new BigDecimal(9.223372036854776E18D);
   private static final BigDecimal bdMinLongValue = new BigDecimal(-9.223372036854776E18D);
   static final String selectPrepend = "Select * from ";
   static final String executePrepend = "Execute Procedure ";
   private ArrayList batchInputs_ = null;

   void local_Close() throws SQLException {
      if (this.resultSet_ != null) {
         this.resultSet_.local_Close();
      }

      this.resultSet_ = null;
      this.resultSetStack_ = null;
      this.openOnClient_ = false;
      this.openOnServer_ = false;
      this.connection_.openPreparedStatements_.removeElement(this);
      this.arrayDescriptors_ = null;
      this.batchInputs_ = null;
      Globals.cache__.returnBuffer(this.encodingBuf_);
      Globals.cache__.returnCharBuffer(this.cbuf_);
      this.inputCharLengths_ = null;
      this.inputCharSets_ = null;
      this.inputNullables_ = null;
      this.inputPrecisions_ = null;
      this.inputPrecisions_ = this.inputScales_ = this.inputTypes_ = null;
      this.inputs_ = null;
   }

   PreparedStatement(Connection var1, String var2, int var3) throws SQLException {
      super(var1);
      this.checkForEmptySQL(var2);
      if (this.escapeProcessingEnabled_) {
         interbase.interclient.EscapeProcessor var4 = new interbase.interclient.EscapeProcessor();
         this.sql_ = var4.doEscapeProcessing(var2, var3);
         this.isEscapedProcedureCall_ = var4.isEscapedProcedureCall();
      } else {
         this.sql_ = var2;
      }

      this.remote_PREPARE_STATEMENT();
      this.inputs_ = new Object[this.inputCols_];
   }

   void setInputMetaData(interbase.interclient.XSQLDA var1) throws SQLException {
      for(int var2 = 0; var2 < this.inputCols_; ++var2) {
         if ((var1.sqlvar[var2].sqltype & 1) == 1) {
            this.inputNullables_[var2] = true;
         } else {
            this.inputNullables_[var2] = false;
         }

         this.inputTypes_[var2] = IBTypes.getResultTypes(var1.sqlvar[var2].sqltype, var1.sqlvar[var2]);
         this.inputScales_[var2] = Math.abs(var1.sqlvar[var2].sqlscale);
         this.inputCharSets_[var2] = var1.sqlvar[var2].sqlsubtype % 128;
         this.inputCharLengths_[var2] = IBTypes.getCharacterLength(var1.sqlvar[var2]);
         if (this.inputTypes_[var2] == 14) {
            this.arrayDescriptors_[var2] = new interbase.interclient.ArrayDescriptor(this.connection_, var1.sqlvar[var2].relname, var1.sqlvar[var2].sqlname);
         }
      }

   }

   private void remote_PREPARE_STATEMENT() throws SQLException {
      Object var1 = null;

      try {
         super.allocateStatement();
         IscTrHandle var2;
         if (super.isDdlStatement(this.sql_)) {
            this.connection_.setDdlTransaction();
            var2 = this.connection_.traDdl_;
         } else {
            this.connection_.setTransaction();
            var2 = this.connection_.tra_;
         }

         this.sqlWarningsCleared_ = false;
         if (!this.isEscapedProcedureCall_) {
            this.connection_.ibase_.iscDsqlPrepare(var2, this.stmtHandle_, this.sql_, this.connection_.getAttachmentSQLDialect(), this.sqlWarnings_);
         } else {
            try {
               this.connection_.ibase_.iscDsqlPrepare(var2, this.stmtHandle_, "Select * from " + this.sql_, this.connection_.getAttachmentSQLDialect(), this.sqlWarnings_);
            } catch (interbase.interclient.IBException var10) {
               int var10000 = var10.getIntParam();
               Ibase var10001 = this.connection_.ibase_;
               if (var10000 != 335544569) {
                  throw new SQLException(var10.getMessage());
               }

               this.connection_.ibase_.iscDsqlPrepare(var2, this.stmtHandle_, "Execute Procedure " + this.sql_, this.connection_.getAttachmentSQLDialect(), this.sqlWarnings_);
            } finally {
               ;
            }
         }

         this.resultSet_ = new ResultSet(this, false, this.stmtHandle_.getOutSqlda().sqld, this.stmtHandle_.getOutSqlda(), super.resultSetType_);
         this.sqlWarningsCleared_ = false;
         this.connection_.ibase_.iscDsqlDescribeBind(this.stmtHandle_, this.sqlWarnings_);
         this.inputCols_ = this.stmtHandle_.in_sqlda.sqld;
         this.inputNullables_ = new boolean[this.inputCols_];
         this.inputTypes_ = new int[this.inputCols_];
         this.inputPrecisions_ = new int[this.inputCols_];
         this.inputScales_ = new int[this.inputCols_];
         this.inputCharSets_ = new int[this.inputCols_];
         this.inputCharLengths_ = new int[this.inputCols_];
         this.arrayDescriptors_ = new interbase.interclient.ArrayDescriptor[this.inputCols_];
         this.setInputMetaData(this.stmtHandle_.in_sqlda);
         this.allocateEncodingBufs();
      } catch (interbase.interclient.IBException var12) {
         if (this.resultSet_ != null) {
            this.resultSet_.close();
            this.resultSet_ = null;
            this.resultSetStack_ = null;
         }

         throw new SQLException(var12.getMessage());
      } catch (SQLException var13) {
         if (this.resultSet_ != null) {
            this.resultSet_.close();
            this.resultSet_ = null;
            this.resultSetStack_ = null;
         }

         throw var13;
      }
   }

   synchronized void allocateEncodingBufs() {
      int var1 = 0;
      int var2 = 0;

      for(int var3 = 0; var3 < this.inputCols_; ++var3) {
         if (this.inputPrecisions_[var3] > var1) {
            var1 = this.inputPrecisions_[var3];
         }

         if (this.inputCharLengths_[var3] > var2) {
            var2 = this.inputCharLengths_[var3];
         }
      }

      this.encodingBuf_ = Globals.cache__.takeBuffer(var1 + 1);
      this.cbuf_ = Globals.cache__.takeCharBuffer(var2);
   }

   public synchronized java.sql.ResultSet executeQuery() throws SQLException {
      this.println("executeQuery:", this);
      this.checkForClosedStatement();
      this.clearWarnings();
      if (this.stmtHandle_.getOutSqlda().sqld <= 0) {
         throw new InvalidOperationException(interbase.interclient.ErrorKey.invalidOperation__execute_query_on_an_update_statement__);
      } else {
         if (this.resultSet_ != null) {
            this.resultSet_.close();
            this.resultSet_ = null;
            this.resultSetStack_ = null;
         }

         this.updateCountStack_ = null;
         this.remote_EXECUTE_PREPARED_QUERY_STATEMENT();
         this.resultSetStack_ = this.resultSet_;
         return this.resultSet_;
      }
   }

   private void remote_EXECUTE_PREPARED_QUERY_STATEMENT() throws SQLException {
      this.setInputParameters(this.inputs_);
      this.remote_EXECUTE_QUERY_STATEMENT((String)null, true);
   }

   private void setInputParameters(Object[] var1) throws SQLException {
      interbase.interclient.XSQLDA var2 = this.stmtHandle_.getInSqlda();
      if (this.inputCols_ == var2.sqld) {
         for(int var3 = 0; var3 < this.inputCols_; ++var3) {
            if (var1[var3] instanceof Array) {
               Array var4 = (Array)var1[var3];
               var4.putArraySlice();
               var2.sqlvar[var3].sqldata = new Long(var4.id_);
            } else {
               var2.sqlvar[var3].sqldata = var1[var3];
            }

            if (var1[var3] == null) {
               this.setNull(var3 + 1, 0);
               var2.sqlvar[var3].sqlind = -1;
            }
         }

      }
   }

   public synchronized int executeUpdate() throws SQLException {
      this.println("executeUpdate:", this);
      return this.executeUpdate(this.inputs_);
   }

   private synchronized int executeUpdate(Object[] var1) throws SQLException {
      this.checkForClosedStatement();
      this.clearWarnings();
      if (this.resultSet_ != null) {
         this.resultSet_.close();
         this.resultSet_ = null;
         this.resultSetStack_ = null;
      }

      this.updateCountStack_ = null;
      int var2 = this.remote_EXECUTE_PREPARED_UPDATE_STATEMENT(var1);
      this.resultSetStack_ = null;
      return var2;
   }

   public synchronized void clearBatch() throws SQLException {
      this.println("clearBatch:", this);
      if (this.batchInputs_ != null) {
         this.batchInputs_.clear();
         this.batchInputs_ = null;
      }

   }

   public synchronized int[] executeBatch() throws BatchUpdateException, SQLException {
      this.println("executeBatch:", this);
      this.checkForClosedStatement();
      this.clearWarnings();
      Object var1 = null;
      int[] var2 = new int[0];
      int var3 = 0;
      if (this.batchInputs_ != null) {
         int var4 = this.batchInputs_.size();
         var2 = new int[var4];

         try {
            while(var3 < var4) {
               var2[var3] = this.executeUpdate((Object[])this.batchInputs_.get(var3));
               ++var3;
            }
         } catch (SQLException var8) {
            int[] var6 = new int[var3];

            for(int var7 = 0; var7 < var3; ++var7) {
               var6[var7] = var2[var7];
            }

            throw new BatchUpdateException(var8.getMessage(), var8.getSQLState(), var6);
         }
      }

      return var2;
   }

   private int remote_EXECUTE_PREPARED_UPDATE_STATEMENT(Object[] var1) throws SQLException {
      this.allocateStatement();
      this.setInputParameters(var1);
      return this.remote_EXECUTE_UPDATE_STATEMENT((String)null, true);
   }

   private boolean allNonNullablesAreSet(Object[] var1) {
      for(int var2 = 0; var2 < this.inputCols_; ++var2) {
         if (!this.inputNullables_[var2] && var1[var2] == null) {
            return false;
         }
      }

      return true;
   }

   public synchronized void setNull(int var1, int var2) throws SQLException {
      this.checkForClosedStatement();

      try {
         this.inputs_[var1 - 1] = null;
         this.stmtHandle_.getInSqlda().sqlvar[var1 - 1].sqlind = -1;
      } catch (ArrayIndexOutOfBoundsException var4) {
         throw new interbase.interclient.ParameterIndexOutOfBoundsException(interbase.interclient.ErrorKey.parameterIndexOutOfBounds__0__, var1);
      }
   }

   private int getParameterIBType(int var1) throws SQLException {
      try {
         return this.inputTypes_[var1 - 1];
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new ParameterIndexOutOfBoundsException(interbase.interclient.ErrorKey.parameterIndexOutOfBounds__0__, var1);
      }
   }

   public synchronized void setBoolean(int var1, boolean var2) throws SQLException {
      int var3 = this.getParameterIBType(var1);
      switch(var3) {
      case 8:
      case 9:
         this.inputs_[var1 - 1] = String.valueOf(var2);
         if (((String)this.inputs_[var1 - 1]).length() > this.inputCharLengths_[var1 - 1]) {
            throw new DataTruncation(var1, true, false, ((String)this.inputs_[var1 - 1]).length(), this.inputCharLengths_[var1 - 1]);
         }
         break;
      default:
         if (var2) {
            this.setInt(var1, 1);
         } else {
            this.setInt(var1, 0);
         }
      }

      this.stmtHandle_.getInSqlda().sqlvar[var1 - 1].sqlind = 0;
   }

   public synchronized void setByte(int var1, byte var2) throws SQLException {
      this.setInt(var1, var2);
      this.stmtHandle_.getInSqlda().sqlvar[var1 - 1].sqlind = 0;
   }

   public synchronized void setShort(int var1, short var2) throws SQLException {
      this.setInt(var1, var2);
   }

   public synchronized void setInt(int var1, int var2) throws SQLException {
      this.checkForClosedStatement();
      int var3 = this.getParameterIBType(var1);
      BigDecimal var4 = null;
      switch(var3) {
      case 6:
      case 7:
      case 17:
      case 18:
      case 19:
         Integer var5 = new Integer(var2);
         var4 = new BigDecimal(var5.toString());
         var4 = var4.movePointRight(this.inputScales_[var1 - 1]);
         var4 = var4.setScale(0, 5);
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 16:
      }

      switch(var3) {
      case 2:
         this.inputs_[var1 - 1] = new Integer(var2);
         break;
      case 3:
         this.inputs_[var1 - 1] = new Float((float)var2);
         break;
      case 4:
      case 5:
         this.inputs_[var1 - 1] = new Double((double)var2);
         break;
      case 6:
      case 19:
         if (var4.compareTo(bdMaxIntValue) == 1 || var4.compareTo(bdMinIntValue) == -1) {
            throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
         }

         this.inputs_[var1 - 1] = new Integer(var4.intValue());
         break;
      case 7:
         if (var4.compareTo(bdMaxShortValue) == 1 || var4.compareTo(bdMinShortValue) == -1) {
            throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
         }

         this.inputs_[var1 - 1] = new Short(var4.shortValue());
         break;
      case 8:
      case 9:
         this.inputs_[var1 - 1] = String.valueOf(var2);
         if (((String)this.inputs_[var1 - 1]).length() > this.inputCharLengths_[var1 - 1]) {
            throw new DataTruncation(var1, true, false, ((String)this.inputs_[var1 - 1]).length(), this.inputCharLengths_[var1 - 1]);
         }
         break;
      case 10:
         this.inputs_[var1 - 1] = new Long(this.setBlobString(String.valueOf(var2)));
         break;
      case 11:
      case 14:
      case 15:
      case 16:
         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__type_conversion__);
      case 12:
         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__type_conversion__set_number_on_binary_blob__);
      case 13:
      case 20:
      default:
         break;
      case 17:
      case 18:
         if (var4.compareTo(bdMaxLongValue) == 1 || var4.compareTo(bdMinLongValue) == -1) {
            throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
         }

         this.inputs_[var1 - 1] = new Long(var4.longValue());
         break;
      case 21:
         if (var2 > 1) {
            throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
         }
      case 1:
         if (var2 > 32767 || var2 < -32768) {
            throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
         }

         this.inputs_[var1 - 1] = new Short((short)var2);
      }

      this.stmtHandle_.getInSqlda().sqlvar[var1 - 1].sqlind = 0;
   }

   public synchronized void setLong(int var1, long var2) throws SQLException {
      this.checkForClosedStatement();
      int var4 = this.getParameterIBType(var1);
      BigDecimal var5 = null;
      switch(var4) {
      case 6:
      case 7:
      case 17:
      case 18:
      case 19:
         Long var6 = new Long(var2);
         var5 = new BigDecimal(var6.toString());
         long var7 = var5.longValue();
         var5 = var5.movePointRight(this.inputScales_[var1 - 1]);
         var5 = var5.setScale(0, 5);
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 16:
      }

      switch(var4) {
      case 2:
         if (var2 <= 2147483647L && var2 >= -2147483648L) {
            this.inputs_[var1 - 1] = new Integer((int)var2);
            break;
         }

         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
      case 3:
         this.inputs_[var1 - 1] = new Float((float)var2);
         break;
      case 4:
      case 5:
         this.inputs_[var1 - 1] = new Double((double)var2);
         break;
      case 6:
      case 19:
         if (var5.compareTo(bdMaxIntValue) == 1 || var5.compareTo(bdMinIntValue) == -1) {
            throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
         }

         this.inputs_[var1 - 1] = new Integer(var5.intValue());
         break;
      case 7:
         if (var5.compareTo(bdMaxShortValue) != 1 && var5.compareTo(bdMinShortValue) != -1) {
            this.inputs_[var1 - 1] = new Short(var5.shortValue());
            break;
         }

         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
      case 8:
      case 9:
         this.inputs_[var1 - 1] = String.valueOf(var2);
         if (((String)this.inputs_[var1 - 1]).length() > this.inputCharLengths_[var1 - 1]) {
            throw new DataTruncation(var1, true, false, ((String)this.inputs_[var1 - 1]).length(), this.inputCharLengths_[var1 - 1]);
         }
         break;
      case 10:
         this.inputs_[var1 - 1] = new Long(this.setBlobString(String.valueOf(var2)));
         break;
      case 11:
      case 14:
      case 15:
      case 16:
         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__type_conversion__);
      case 12:
         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__type_conversion__set_number_on_binary_blob__);
      case 13:
      case 20:
      default:
         break;
      case 17:
      case 18:
         if (var5.compareTo(bdMaxLongValue) != 1 && var5.compareTo(bdMinLongValue) != -1) {
            this.inputs_[var1 - 1] = new Long(var5.longValue());
            break;
         }

         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
      case 21:
         if (var2 > 1L) {
            throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
         }
      case 1:
         if (var2 > 32767L || var2 < -32768L) {
            throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
         }

         this.inputs_[var1 - 1] = new Short((short)((int)var2));
      }

      this.stmtHandle_.getInSqlda().sqlvar[var1 - 1].sqlind = 0;
   }

   public synchronized void setFloat(int var1, float var2) throws SQLException {
      this.checkForClosedStatement();
      int var3 = this.getParameterIBType(var1);
      BigDecimal var4 = null;
      switch(var3) {
      case 6:
      case 7:
      case 17:
      case 18:
      case 19:
         Float var5 = new Float(var2);
         var4 = new BigDecimal(var5.toString());
         var4 = var4.movePointRight(this.inputScales_[var1 - 1]);
         var4 = var4.setScale(0, 5);
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 16:
      }

      switch(var3) {
      case 1:
         if (var2 > 32767.0F || var2 < -32768.0F) {
            throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
         }

         this.inputs_[var1 - 1] = new Short((short)((int)var2));
         break;
      case 2:
         if (var2 > 2.14748365E9F || var2 < -2.14748365E9F) {
            throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
         }

         this.inputs_[var1 - 1] = new Integer((int)var2);
         break;
      case 3:
         this.inputs_[var1 - 1] = new Float(var2);
         break;
      case 4:
      case 5:
         this.inputs_[var1 - 1] = new Double((double)var2);
         break;
      case 6:
      case 19:
         if (var4.compareTo(bdMaxIntValue) == 1 || var4.compareTo(bdMinIntValue) == -1) {
            throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
         }

         this.inputs_[var1 - 1] = new Integer(var4.intValue());
         break;
      case 7:
         if (var4.compareTo(bdMaxShortValue) == 1 || var4.compareTo(bdMinShortValue) == -1) {
            throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
         }

         this.inputs_[var1 - 1] = new Short(var4.shortValue());
         break;
      case 8:
      case 9:
         this.inputs_[var1 - 1] = String.valueOf(var2);
         if (((String)this.inputs_[var1 - 1]).length() > this.inputCharLengths_[var1 - 1]) {
            throw new DataTruncation(var1, true, false, ((String)this.inputs_[var1 - 1]).length(), this.inputCharLengths_[var1 - 1]);
         }
         break;
      case 10:
         this.inputs_[var1 - 1] = new Long(this.setBlobString(String.valueOf(var2)));
         break;
      case 11:
      case 14:
      case 15:
      case 16:
      case 21:
         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__type_conversion__);
      case 12:
         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__type_conversion__set_number_on_binary_blob__);
      case 13:
      case 20:
      default:
         break;
      case 17:
      case 18:
         if (var4.compareTo(bdMaxLongValue) == 1 || var4.compareTo(bdMinLongValue) == -1) {
            throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
         }

         this.inputs_[var1 - 1] = new Long(var4.longValue());
      }

      this.stmtHandle_.getInSqlda().sqlvar[var1 - 1].sqlind = 0;
   }

   public synchronized void setDouble(int var1, double var2) throws SQLException {
      this.checkForClosedStatement();
      int var4 = this.getParameterIBType(var1);
      BigDecimal var5 = null;
      switch(var4) {
      case 6:
      case 7:
      case 17:
      case 18:
      case 19:
         var5 = new BigDecimal(var2);
         if (this.inputScales_[var1 - 1] != 0) {
            var5 = var5.movePointRight(this.inputScales_[var1 - 1]);
            var5 = var5.setScale(0, 5);
         }
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 16:
      }

      switch(var4) {
      case 1:
         if (var2 <= 32767.0D && var2 >= -32768.0D) {
            this.inputs_[var1 - 1] = new Short((short)((int)var2));
            break;
         }

         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
      case 2:
         if (var2 > 2.147483647E9D || var2 < -2.147483648E9D) {
            throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
         }

         this.inputs_[var1 - 1] = new Integer((int)var2);
         break;
      case 3:
         if (var2 <= 3.4028234663852886E38D && var2 >= -3.4028234663852886E38D) {
            this.inputs_[var1 - 1] = new Float((float)var2);
            break;
         }

         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
      case 4:
      case 5:
         this.inputs_[var1 - 1] = new Double(var2);
         break;
      case 6:
      case 19:
         if (var5.compareTo(bdMaxIntValue) != 1 && var5.compareTo(bdMinIntValue) != -1) {
            this.inputs_[var1 - 1] = new Integer(var5.intValue());
            break;
         }

         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
      case 7:
         if (var5.compareTo(bdMaxShortValue) != 1 && var5.compareTo(bdMinShortValue) != -1) {
            this.inputs_[var1 - 1] = new Short(var5.shortValue());
            break;
         }

         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
      case 8:
      case 9:
         this.inputs_[var1 - 1] = String.valueOf(var2);
         if (((String)this.inputs_[var1 - 1]).length() > this.inputCharLengths_[var1 - 1]) {
            throw new DataTruncation(var1, true, false, ((String)this.inputs_[var1 - 1]).length(), this.inputCharLengths_[var1 - 1]);
         }
         break;
      case 10:
         this.inputs_[var1 - 1] = new Long(this.setBlobString(String.valueOf(var2)));
         break;
      case 11:
      case 14:
      case 15:
      case 16:
      case 21:
         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__type_conversion__);
      case 12:
         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__type_conversion__set_number_on_binary_blob__);
      case 13:
      case 20:
      default:
         break;
      case 17:
      case 18:
         if (var5.compareTo(bdMaxLongValue) == 1 || var5.compareTo(bdMinLongValue) == -1) {
            throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
         }

         this.inputs_[var1 - 1] = new Long(var5.longValue());
      }

      this.stmtHandle_.getInSqlda().sqlvar[var1 - 1].sqlind = 0;
   }

   public synchronized void setBigDecimal(int var1, BigDecimal var2) throws SQLException {
      this.checkForClosedStatement();
      int var3 = this.getParameterIBType(var1);
      BigDecimal var4 = null;
      switch(var3) {
      case 6:
      case 7:
      case 17:
      case 18:
      case 19:
         var4 = var2.movePointRight(this.inputScales_[var1 - 1]);
         var4 = var4.setScale(0, 5);
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 16:
      }

      switch(var3) {
      case 1:
         if (var2.compareTo(bdMaxShortValue) != 1 && var2.compareTo(bdMinShortValue) != -1) {
            this.inputs_[var1 - 1] = new Short(var2.shortValue());
            break;
         }

         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
      case 2:
         if (var2.compareTo(bdMaxIntValue) == 1 || var2.compareTo(bdMinIntValue) == -1) {
            throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
         }

         this.inputs_[var1 - 1] = new Integer(var2.intValue());
         break;
      case 3:
         if (var2.compareTo(bdMaxFloatValue) != 1 && var2.compareTo(bdMinFloatValue) != -1) {
            this.inputs_[var1 - 1] = new Float(var2.floatValue());
            break;
         }

         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
      case 4:
      case 5:
         if (var2.compareTo(bdMaxDoubleValue) != 1 && var2.compareTo(bdMinDoubleValue) != -1) {
            this.inputs_[var1 - 1] = new Double(var2.doubleValue());
            break;
         }

         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
      case 6:
      case 19:
         if (var4.compareTo(bdMaxIntValue) != 1 && var4.compareTo(bdMinIntValue) != -1) {
            this.inputs_[var1 - 1] = new Integer(var4.intValue());
            break;
         }

         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
      case 7:
         if (var4.compareTo(bdMaxShortValue) != 1 && var4.compareTo(bdMinShortValue) != -1) {
            this.inputs_[var1 - 1] = new Short(var4.shortValue());
            break;
         }

         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
      case 8:
      case 9:
         this.inputs_[var1 - 1] = var2.toString();
         if (((String)this.inputs_[var1 - 1]).length() > this.inputCharLengths_[var1 - 1]) {
            throw new DataTruncation(var1, true, false, ((String)this.inputs_[var1 - 1]).length(), this.inputCharLengths_[var1 - 1]);
         }
         break;
      case 10:
         this.inputs_[var1 - 1] = new Long(this.setBlobString(var2.toString()));
         break;
      case 11:
      case 14:
      case 15:
      case 16:
      case 21:
         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__type_conversion__);
      case 12:
         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__type_conversion__set_number_on_binary_blob__);
      case 13:
      case 20:
      default:
         break;
      case 17:
      case 18:
         if (var4.compareTo(bdMaxLongValue) == 1 || var4.compareTo(bdMinLongValue) == -1) {
            throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, String.valueOf(var2));
         }

         this.inputs_[var1 - 1] = new Long(var4.longValue());
      }

      this.stmtHandle_.getInSqlda().sqlvar[var1 - 1].sqlind = 0;
   }

   public synchronized void setString(int var1, String var2) throws SQLException {
      this.checkForClosedStatement();
      int var3 = this.getParameterIBType(var1);

      try {
         switch(var3) {
         case 1:
         case 2:
         case 21:
            this.setInt(var1, Integer.parseInt(var2));
            break;
         case 3:
            this.inputs_[var1 - 1] = new Float(var2);
            break;
         case 4:
         case 5:
            this.inputs_[var1 - 1] = new Double(var2);
            break;
         case 6:
         case 7:
            this.setBigDecimal(var1, new BigDecimal(var2));
            break;
         case 8:
         case 9:
            this.inputs_[var1 - 1] = var2;
            if (var2.getBytes().length > this.inputCharLengths_[var1 - 1]) {
               throw new DataTruncation(var1, true, false, var2.length(), this.inputCharLengths_[var1 - 1]);
            }
            break;
         case 10:
         case 12:
            this.inputs_[var1 - 1] = new Long(this.setBlobString(var2));
            break;
         case 11:
            try {
               this.setTimestamp(var1, Timestamp.valueOf(var2));
            } catch (IllegalArgumentException var7) {
               throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, var2);
            }
         case 13:
         case 20:
         default:
            break;
         case 14:
            throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__type_conversion__);
         case 15:
            try {
               this.setDate(var1, Date.valueOf(var2));
               break;
            } catch (IllegalArgumentException var6) {
               throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, var2);
            }
         case 16:
            try {
               this.setTime(var1, Time.valueOf(var2));
               break;
            } catch (IllegalArgumentException var5) {
               throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, var2);
            }
         case 17:
         case 18:
         case 19:
            this.setBigDecimal(var1, new BigDecimal(var2));
         }

         this.stmtHandle_.getInSqlda().sqlvar[var1 - 1].sqlind = 0;
      } catch (NumberFormatException var8) {
         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__instance_conversion_0__, var2);
      }
   }

   public synchronized void setBytes(int var1, byte[] var2) throws SQLException {
      this.checkForClosedStatement();
      int var3 = this.getParameterIBType(var1);
      switch(var3) {
      case 8:
      case 9:
         if (var2.length > this.inputCharLengths_[var1 - 1]) {
            throw new DataTruncation(var1, true, false, var2.length, this.inputCharLengths_[var1 - 1]);
         }

         this.inputs_[var1 - 1] = var2;
         break;
      case 10:
      case 12:
         this.inputs_[var1 - 1] = new Long((new interbase.interclient.BlobInput(this, new ByteArrayInputStream(var2), var2.length)).blobId);
         break;
      case 11:
      default:
         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__type_conversion__);
      }

      this.stmtHandle_.getInSqlda().sqlvar[var1 - 1].sqlind = 0;
   }

   public synchronized void setDate(int var1, Date var2) throws SQLException {
      this.checkForClosedStatement();
      int var3 = this.getParameterIBType(var1);
      switch(var3) {
      case 8:
      case 9:
         this.inputs_[var1 - 1] = var2.toString();
         if (((String)this.inputs_[var1 - 1]).length() > this.inputCharLengths_[var1 - 1]) {
            throw new DataTruncation(var1, true, false, ((String)this.inputs_[var1 - 1]).length(), this.inputCharLengths_[var1 - 1]);
         }
         break;
      case 10:
         this.inputs_[var1 - 1] = new Long(this.setBlobString(var2.toString()));
         break;
      case 11:
      case 15:
         this.inputs_[var1 - 1] = new IBTimestamp(var2);
         break;
      case 12:
         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__type_conversion__set_date_on_binary_blob__);
      case 13:
      case 14:
      default:
         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__type_conversion__);
      }

      this.stmtHandle_.getInSqlda().sqlvar[var1 - 1].sqlind = 0;
   }

   public synchronized void setTime(int var1, Time var2) throws SQLException {
      this.checkForClosedStatement();
      int var3 = this.getParameterIBType(var1);
      switch(var3) {
      case 8:
      case 9:
         this.inputs_[var1 - 1] = var2.toString();
         if (((String)this.inputs_[var1 - 1]).length() > this.inputCharLengths_[var1 - 1]) {
            throw new DataTruncation(var1, true, false, ((String)this.inputs_[var1 - 1]).length(), this.inputCharLengths_[var1 - 1]);
         }
         break;
      case 10:
         this.inputs_[var1 - 1] = new Long(this.setBlobString(var2.toString()));
         break;
      case 11:
      case 16:
         this.inputs_[var1 - 1] = new IBTimestamp(var2);
         break;
      case 12:
         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__type_conversion__set_date_on_binary_blob__);
      case 13:
      case 14:
      case 15:
      default:
         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__type_conversion__);
      }

      this.stmtHandle_.getInSqlda().sqlvar[var1 - 1].sqlind = 0;
   }

   public synchronized void setTimestamp(int var1, Timestamp var2) throws SQLException {
      this.checkForClosedStatement();
      int var3 = this.getParameterIBType(var1);
      switch(var3) {
      case 8:
      case 9:
         this.inputs_[var1 - 1] = var2.toString();
         if (((String)this.inputs_[var1 - 1]).length() > this.inputCharLengths_[var1 - 1]) {
            throw new DataTruncation(var1, true, false, ((String)this.inputs_[var1 - 1]).length(), this.inputCharLengths_[var1 - 1]);
         }
         break;
      case 10:
         this.inputs_[var1 - 1] = new Long(this.setBlobString(var2.toString()));
         break;
      case 11:
         this.inputs_[var1 - 1] = new IBTimestamp(var2);
         break;
      case 12:
         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__type_conversion__set_date_on_binary_blob__);
      case 13:
      case 14:
      default:
         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__type_conversion__);
      case 15:
         this.inputs_[var1 - 1] = new IBTimestamp(var2.getYear(), var2.getMonth(), var2.getDate(), 0, 0, 0, 0);
         break;
      case 16:
         this.inputs_[var1 - 1] = new IBTimestamp(0, 0, 1, var2.getHours(), var2.getMinutes(), var2.getSeconds(), var2.getNanos());
      }

      this.stmtHandle_.getInSqlda().sqlvar[var1 - 1].sqlind = 0;
   }

   byte[] getBytesFromInputStream(InputStream var1, int var2) throws SQLException {
      try {
         byte[] var3 = new byte[var2];

         for(int var4 = 0; var4 < var2; var4 += var1.read(var3, var4, var2 - var4)) {
            ;
         }

         return var3;
      } catch (IOException var5) {
         throw new CommunicationException(interbase.interclient.ErrorKey.communication__user_stream__io_exception_on_read_0__, Utils.getMessage(var5));
      }
   }

   String getStringFromAsciiInputStream(InputStream var1, int var2) throws SQLException {
      byte[] var3 = this.getBytesFromInputStream(var1, var2);
      return new String(var3);
   }

   String getStringFromUnicodeInputStream(InputStream var1, int var2) throws SQLException {
      byte[] var3 = this.getBytesFromInputStream(var1, var2);
      char[] var4 = new char[var2 / 2];
      int var5 = 0;

      for(int var6 = 0; var6 < var2 / 2; ++var6) {
         int var7 = (var3[var5++] & 255) << 8;
         int var8 = (var3[var5++] & 255) << 0;
         var4[var6] = (char)(var7 + var8);
      }

      return new String(var4);
   }

   public synchronized void setAsciiStream(int var1, InputStream var2, int var3) throws SQLException {
      this.checkForClosedStatement();
      int var4 = this.getParameterIBType(var1);
      switch(var4) {
      case 8:
      case 9:
         if (var3 > this.inputCharLengths_[var1 - 1]) {
            throw new DataTruncation(var1, true, false, var3, this.inputCharLengths_[var1 - 1]);
         }

         this.inputs_[var1 - 1] = this.getStringFromAsciiInputStream(var2, var3);
         break;
      case 10:
      case 12:
         this.inputs_[var1 - 1] = new Long((new interbase.interclient.BlobInput(this, var2, var3)).blobId);
         break;
      case 11:
      default:
         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__type_conversion__);
      }

      this.stmtHandle_.getInSqlda().sqlvar[var1 - 1].sqlind = 0;
   }

   /** @deprecated */
   public synchronized void setUnicodeStream(int var1, InputStream var2, int var3) throws SQLException {
      this.checkForClosedStatement();
      int var4 = this.getParameterIBType(var1);
      if (var3 % 2 != 0) {
         throw new InvalidArgumentException(interbase.interclient.ErrorKey.invalidArgument__setUnicodeStream_odd_bytes__);
      } else {
         switch(var4) {
         case 8:
         case 9:
            if (var3 > this.inputCharLengths_[var1 - 1]) {
               throw new DataTruncation(var1, true, false, var3, this.inputCharLengths_[var1 - 1]);
            }

            this.inputs_[var1 - 1] = this.getStringFromUnicodeInputStream(var2, var3);
            break;
         case 10:
         case 12:
            this.inputs_[var1 - 1] = new Integer((int)(new interbase.interclient.BlobInput(this, var2, var3)).blobId);
            break;
         case 11:
         default:
            throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__type_conversion__);
         }

         this.stmtHandle_.getInSqlda().sqlvar[var1 - 1].sqlind = 0;
      }
   }

   public synchronized void setBinaryStream(int var1, InputStream var2, int var3) throws SQLException {
      this.checkForClosedStatement();
      int var4 = this.getParameterIBType(var1);
      switch(var4) {
      case 8:
      case 9:
         if (var3 > this.inputCharLengths_[var1 - 1]) {
            throw new DataTruncation(var1, true, false, var3, this.inputCharLengths_[var1 - 1]);
         }

         this.inputs_[var1 - 1] = this.getBytesFromInputStream(var2, var3);
         break;
      case 10:
      case 12:
         this.inputs_[var1 - 1] = new Long((new interbase.interclient.BlobInput(this, var2, var3)).blobId);
         break;
      case 11:
      default:
         throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__type_conversion__);
      }

      this.stmtHandle_.getInSqlda().sqlvar[var1 - 1].sqlind = 0;
   }

   private long setBlobString(String var1) throws SQLException {
      try {
         byte[] var2 = var1.getBytes(this.connection_.ctb_.name());
         return (new interbase.interclient.BlobInput(this, new ByteArrayInputStream(var2), var2.length)).blobId;
      } catch (UnsupportedEncodingException var3) {
         throw new BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 128);
      }
   }

   public synchronized void clearParameters() throws SQLException {
      this.println("clearParameters:", this);
      this.checkForClosedStatement();

      for(int var1 = 0; var1 < this.inputCols_; ++var1) {
         this.inputs_[var1] = null;
      }

   }

   public synchronized void setObject(int var1, Object var2, int var3, int var4) throws SQLException {
      this.setObject(var1, var2);
   }

   @Override
   public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {

   }

   @Override
   public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {

   }

   @Override
   public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {

   }

   @Override
   public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {

   }

   @Override
   public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {

   }

   @Override
   public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {

   }

   @Override
   public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {

   }

   @Override
   public void setClob(int parameterIndex, Reader reader) throws SQLException {

   }

   @Override
   public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {

   }

   @Override
   public void setNClob(int parameterIndex, Reader reader) throws SQLException {

   }

   public synchronized void setObject(int var1, Object var2, int var3) throws SQLException {
      this.setObject(var1, var2);
   }

   public synchronized void setObject(int var1, Object var2) throws SQLException {
      if (var2 instanceof Boolean) {
         this.setBoolean(var1, ((Boolean)var2).booleanValue());
      } else if (var2 instanceof Byte) {
         this.setByte(var1, ((Byte)var2).byteValue());
      } else if (var2 instanceof Short) {
         this.setShort(var1, ((Short)var2).shortValue());
      } else if (var2 instanceof Integer) {
         this.setInt(var1, ((Integer)var2).intValue());
      } else if (var2 instanceof Long) {
         this.setLong(var1, ((Long)var2).longValue());
      } else if (var2 instanceof Double) {
         this.setDouble(var1, ((Double)var2).doubleValue());
      } else if (var2 instanceof Float) {
         this.setFloat(var1, ((Float)var2).floatValue());
      } else if (var2 instanceof BigDecimal) {
         this.setBigDecimal(var1, (BigDecimal)var2);
      } else if (var2 instanceof String) {
         this.setString(var1, (String)var2);
      } else if (var2 instanceof byte[] && this.getParameterIBType(var1) != 14) {
         this.setBytes(var1, (byte[])var2);
      } else if (var2 instanceof Date) {
         this.setDate(var1, (Date)var2);
      } else if (var2 instanceof Time) {
         this.setTime(var1, (Time)var2);
      } else if (var2 instanceof Timestamp) {
         this.setTimestamp(var1, (Timestamp)var2);
      } else if (var2 instanceof java.sql.Array) {
         this.setArray(var1, (java.sql.Array)var2);
      } else {
         if (!var2.getClass().isArray()) {
            if (var2 instanceof InputStream) {
               throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__set_object_on_stream__);
            }

            throw new interbase.interclient.ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__type_conversion__);
         }

         this.setJavaArray(var1, var2);
      }

      this.stmtHandle_.getInSqlda().sqlvar[var1 - 1].sqlind = 0;
   }

   public synchronized boolean execute() throws SQLException {
      this.println("execute:", this);
      this.checkForClosedStatement();
      this.clearWarnings();
      if (this.resultSet_ != null) {
         this.resultSet_.close();
         this.resultSet_ = null;
         this.resultSetStack_ = null;
      }

      this.updateCountStack_ = null;
      this.remote_EXECUTE_PREPARED_STATEMENT();
      this.resultSetStack_ = this.resultSet_;
      return this.resultSet_ != null;
   }

   private void remote_EXECUTE_PREPARED_STATEMENT() throws SQLException {
      this.allocateStatement();
      this.setInputParameters(this.inputs_);
      this.remote_EXECUTE_STATEMENT((String)null, true);
   }

   public synchronized void addBatch() throws SQLException {
      this.println("addBatch:", this);
      if (this.batchInputs_ == null) {
         this.batchInputs_ = new ArrayList();
      }

      Object[] var1 = new Object[this.inputs_.length];

      for(int var2 = 0; var2 < this.inputs_.length; ++var2) {
         var1[var2] = this.inputs_[var2];
      }

      this.batchInputs_.add(var1);
   }

   public synchronized void setCharacterStream(int var1, Reader var2, int var3) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setRef(int var1, Ref var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setBlob(int var1, java.sql.Blob var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setClob(int var1, java.sql.Clob var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setArray(int var1, java.sql.Array var2) throws SQLException {
      this.setJavaArray(var1, var2.getArray());
   }

   private synchronized void setJavaArray(int var1, Object var2) throws SQLException {
      this.checkForClosedStatement();
      int var3 = this.getParameterIBType(var1);
      if (var3 == 14) {
         throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
      } else {
         throw new ParameterConversionException(interbase.interclient.ErrorKey.parameterConversion__type_conversion__);
      }
   }

   public synchronized java.sql.ResultSetMetaData getMetaData() throws SQLException {
      this.checkForClosedStatement();
      return this.resultSet_ == null ? null : this.resultSet_.getMetaData();
   }

   public synchronized void setDate(int var1, Date var2, Calendar var3) throws SQLException {
      if (var3 == null) {
         this.setDate(var1, var2);
      } else {
         var3.setTime(var2);
         this.setDate(var1, new Date(var3.getTime().getTime()));
      }

   }

   public synchronized void setTime(int var1, Time var2, Calendar var3) throws SQLException {
      if (var3 == null) {
         this.setTime(var1, var2);
      } else {
         var3.setTime(var2);
         this.setTime(var1, new Time(var3.getTime().getTime()));
      }

   }

   public synchronized void setTimestamp(int var1, Timestamp var2, Calendar var3) throws SQLException {
      if (var3 == null) {
         this.setTimestamp(var1, var2);
      } else if (var2 == null) {
         this.setTimestamp(var1, new Timestamp(var3.getTime().getTime()));
      } else {
         long var4 = var2.getTime() - (long)var3.getTimeZone().getRawOffset();
         this.setTimestamp(var1, new Timestamp(var4));
      }

   }

   public synchronized void setNull(int var1, int var2, String var3) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized java.sql.ParameterMetaData getParameterMetaData() throws SQLException {
      this.checkForClosedStatement();
      if (this.parameterMetaData_ == null) {
         this.parameterMetaData_ = IBParameterMetaData.setParameterMetaData(this);
      }

      return this.parameterMetaData_;
   }

   @Override
   public void setRowId(int parameterIndex, RowId x) throws SQLException {

   }

   @Override
   public void setNString(int parameterIndex, String value) throws SQLException {

   }

   @Override
   public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {

   }

   @Override
   public void setNClob(int parameterIndex, NClob value) throws SQLException {

   }

   @Override
   public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {

   }

   @Override
   public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {

   }

   @Override
   public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {

   }

   @Override
   public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {

   }

   /** @deprecated */
   public interbase.interclient.ParameterMetaData getIBParameterMetaData() throws SQLException {
      if (this.ibParameterMetaData == null) {
         this.ibParameterMetaData = new ParameterMetaData(this);
      }

      return this.ibParameterMetaData;
   }

   /** @deprecated */
   public synchronized java.sql.ResultSetMetaData getResultSetMetaData() throws SQLException {
      return this.getMetaData();
   }

   public synchronized void prepareArray(int var1, String var2, String var3) throws SQLException {
      if (this.getParameterIBType(var1) != 14) {
         throw new InvalidArgumentException(interbase.interclient.ErrorKey.invalidArgument__not_array_parameter__);
      }
   }

   public synchronized void setURL(int var1, URL var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }
}
