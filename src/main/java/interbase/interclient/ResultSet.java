package interbase.interclient;

import borland.jdbc.SQLAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;

public final class ResultSet implements java.sql.ResultSet, Adaptor, SQLAdapter, com.inprise.sql.SQLAdapter {
   int numRows_;
   private interbase.interclient.ResultSetMetaData resultSetMetaData_;
   interbase.interclient.Statement statement_;
   Hashtable columnNameToIndexCache_ = null;
   private interbase.interclient.IBException sqlWarnings_;
   private static final int WAS_NULL__ = 1;
   private static final int WAS_NOT_NULL__ = 2;
   private static final int WAS_NULL_UNSET__ = 0;
   private int wasNull_ = 0;
   int resultCols_;
   int[] resultTypes_;
   int[] resultScales_;
   interbase.interclient.ArrayDescriptor[] arrayDescriptors_;
   char[] cbuf_;
   private boolean openOnClient_ = true;
   boolean openOnServer_;
   boolean validCursorPosition_ = false;
   private boolean adaptToRightTrimString_ = false;
   private boolean adaptToSingleInstanceTime_ = false;
   private interbase.interclient.IBTimestamp adaptableIBTimestamp_ = null;
   private Date adaptableDate_ = null;
   private Time adaptableTime_ = null;
   private Timestamp adaptableTimestamp_ = null;
   interbase.interclient.XSQLDA xsqlda_ = null;
   private long numberOfRowsFetched_ = 0L;
   private long currentPosition_ = 0L;
   private boolean fetched_ = false;
   private boolean catalogQuery_ = false;
   private int resultSetType_ = 1003;
   public static final int FETCH_FORWARD = 1000;
   public static final int FETCH_REVERSE = 1001;
   public static final int FETCH_UNKNOWN = 1002;
   public static final int TYPE_FORWARD_ONLY = 1003;
   public static final int TYPE_SCROLL_INSENSITIVE = 1004;
   public static final int TYPE_SCROLL_SENSITIVE = 1005;
   public static final int CONCUR_READ_ONLY = 1007;
   public static final int CONCUR_UPDATABLE = 1008;
   static final int HOLD_CURSORS_OVER_COMMIT = 1;
   static final int CLOSE_CURSORS_AT_COMMIT = 2;

   ResultSet(interbase.interclient.Statement var1, int var2, boolean var3) {
      this.resultCols_ = var2;
      this.resultTypes_ = new int[this.resultCols_];
      this.resultScales_ = new int[this.resultCols_];
      this.arrayDescriptors_ = new interbase.interclient.ArrayDescriptor[this.resultCols_];
      this.statement_ = var1;
      this.openOnServer_ = var3;
      this.resultSetType_ = 1003;
   }

   ResultSet(Statement var1, boolean var2, int var3, interbase.interclient.XSQLDA var4, int var5) throws SQLException {
      this.resultCols_ = var3;
      this.resultTypes_ = new int[this.resultCols_];
      this.resultScales_ = new int[this.resultCols_];
      this.arrayDescriptors_ = new interbase.interclient.ArrayDescriptor[this.resultCols_];
      this.statement_ = var1;
      this.openOnServer_ = var2;
      this.xsqlda_ = var4;
      this.numRows_ = var4.sqld;
      this.setResultSetMetaData(var4, false);
      this.resultSetType_ = var5;
   }

   protected void finalize() throws Throwable {
      if (this.openOnServer_) {
         this.close();
      }

      super.finalize();
   }

   public synchronized boolean next() throws SQLException {
      this.checkForClosedCursor();
      this.clearWarnings();
      this.validCursorPosition_ = this.getNextCursorPosition();
      return this.validCursorPosition_;
   }

   private boolean getNextCursorPosition() throws SQLException {
      if (this.fetched_) {
         if (this.numberOfRowsFetched_ == this.currentPosition_) {
            if (this.statement_.stmtHandle_.isAllRowsFetched()) {
               if (this.catalogQuery_) {
                  this.cleanupCatalog();
               }

               this.statement_.cancelable = false;
               return false;
            } else {
               this.remote_FETCH_ROWS();
               this.numberOfRowsFetched_ = (long)this.statement_.stmtHandle_.rows.size();
               this.fetched_ = true;
               if (this.numberOfRowsFetched_ > 0L) {
                  ++this.currentPosition_;
                  return true;
               } else {
                  if (this.catalogQuery_) {
                     this.cleanupCatalog();
                  }

                  this.statement_.cancelable = false;
                  return false;
               }
            }
         } else {
            ++this.currentPosition_;
            return true;
         }
      } else {
         boolean var1 = false;

         do {
            this.remote_FETCH_ROWS();
            if (this.resultSetType_ == 1003 || this.statement_.stmtHandle_.isAllRowsFetched()) {
               var1 = true;
            }
         } while(!var1);

         this.numberOfRowsFetched_ = (long)this.statement_.stmtHandle_.rows.size();
         this.fetched_ = true;
         if (this.numberOfRowsFetched_ > 0L) {
            ++this.currentPosition_;
            return true;
         } else {
            if (this.catalogQuery_) {
               this.cleanupCatalog();
            }

            this.statement_.cancelable = false;
            return false;
         }
      }
   }

   private void cleanupCatalog() {
   }

   void remote_FETCH_ROWS() throws SQLException {
      if (this.openOnServer_) {
         try {
            if (!this.statement_.stmtHandle_.isSingletonResult() && this.resultSetType_ == 1003) {
               this.statement_.stmtHandle_.clearRows();
               this.currentPosition_ = 0L;
            }

            boolean var1 = this.statement_.connection_.ibase_.iscDsqlFetch(this.statement_.stmtHandle_, this.statement_.stmtHandle_.getOutSqlda().version, this.statement_.stmtHandle_.getOutSqlda(), this.sqlWarnings_, this.statement_.getFetchSize());
         } catch (interbase.interclient.IBException var2) {
            this.fetched_ = false;
            this.validCursorPosition_ = false;
            throw new SQLException(var2.getMessage());
         }
      }
   }

   void checkForClosedCursor() throws SQLException {
      if (!this.openOnClient_) {
         throw new interbase.interclient.InvalidOperationException(interbase.interclient.ErrorKey.invalidOperation__result_set_closed__);
      }
   }

   short getRowData_short(int var1) throws SQLException {
      Object[] var2 = (Object[])this.statement_.stmtHandle_.rows.get((int)(this.currentPosition_ - 1L));
      return ((Short)var2[var1]).shortValue();
   }

   int getRowData_int(int var1) throws SQLException {
      Object[] var2 = (Object[])this.statement_.stmtHandle_.rows.get((int)(this.currentPosition_ - 1L));
      return ((Integer)var2[var1]).intValue();
   }

   long getRowData_long(int var1) throws SQLException {
      Object[] var2 = (Object[])this.statement_.stmtHandle_.rows.get((int)(this.currentPosition_ - 1L));
      return ((Long)var2[var1]).longValue();
   }

   float getRowData_float(int var1) throws SQLException {
      Object[] var2 = (Object[])this.statement_.stmtHandle_.rows.get((int)(this.currentPosition_ - 1L));
      return ((Float)var2[var1]).floatValue();
   }

   double getRowData_double(int var1) throws SQLException {
      Object[] var2 = (Object[])this.statement_.stmtHandle_.rows.get((int)(this.currentPosition_ - 1L));
      return ((Double)var2[var1]).doubleValue();
   }

   Timestamp getRowData_timestampId(int var1) throws SQLException {
      Object[] var2 = (Object[])this.statement_.stmtHandle_.rows.get((int)(this.currentPosition_ - 1L));
      return (Timestamp)var2[var1];
   }

   Date getRowData_sqldate(int var1) throws SQLException {
      Object[] var2 = (Object[])this.statement_.stmtHandle_.rows.get((int)(this.currentPosition_ - 1L));
      return (Date)var2[var1];
   }

   Time getRowData_sqltime(int var1) throws SQLException {
      Object[] var2 = (Object[])this.statement_.stmtHandle_.rows.get((int)(this.currentPosition_ - 1L));
      return (Time)var2[var1];
   }

   long getRowData_blobId(int var1) throws SQLException {
      Object[] var2 = (Object[])this.statement_.stmtHandle_.rows.get((int)(this.currentPosition_ - 1L));
      return ((Long)var2[var1]).longValue();
   }

   long getRowData_arrayId(int var1) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   String getRowData_char(int var1) throws SQLException {
      return this.getRowData_varchar(var1);
   }

   String getRowData_varchar(int var1) throws SQLException {
      Object[] var2 = (Object[])this.statement_.stmtHandle_.rows.get((int)(this.currentPosition_ - 1L));
      String var3 = CharacterEncodings.getCharacterSetName(this.xsqlda_.sqlvar[var1].sqlsubtype % 128);
      String var4;
      if (!this.statement_.connection_.charsetWasNone || this.statement_.connection_.charsetWasNone && var3 == null) {
         try {
            var4 = new String((byte[])var2[var1], this.statement_.connection_.ianaCharacterEncoding_);
         } catch (UnsupportedEncodingException var7) {
            throw new interbase.interclient.UnsupportedCharacterEncodingException(interbase.interclient.ErrorKey.unsupportedCharacterEncoding__0__, this.statement_.connection_.ianaCharacterEncoding_);
         }
      } else {
         try {
            var4 = new String((byte[])var2[var1], var3);
         } catch (UnsupportedEncodingException var6) {
            throw new interbase.interclient.UnsupportedCharacterEncodingException(interbase.interclient.ErrorKey.unsupportedCharacterEncoding__0__, var3);
         }
      }

      return var4;
   }

   String getRowData_varchar_string(int var1) throws SQLException {
      Object[] var2 = (Object[])this.statement_.stmtHandle_.rows.get((int)(this.currentPosition_ - 1L));
      return new String(var2[var1].toString());
   }

   byte[] getRowData_bytes(int var1) throws SQLException {
      Object[] var2 = (Object[])this.statement_.stmtHandle_.rows.get((int)(this.currentPosition_ - 1L));
      return (byte[])var2[var1];
   }

   void local_Close() throws SQLException {
      this.openOnClient_ = false;
      Globals.cache__.returnCharBuffer(this.cbuf_);
      this.cbuf_ = null;
      this.numberOfRowsFetched_ = 0L;
      this.currentPosition_ = 0L;
      if (this.columnNameToIndexCache_ != null) {
         this.columnNameToIndexCache_.clear();
         this.columnNameToIndexCache_ = null;
      }

      this.xsqlda_ = null;
      this.markMetaDataNull();
      if (this.resultSetMetaData_ != null) {
         this.resultSetMetaData_ = null;
      }

      this.statement_ = null;
      this.sqlWarnings_ = null;
   }

   private void markMetaDataNull() {
      this.resultTypes_ = null;
      this.resultScales_ = null;
      this.arrayDescriptors_ = null;
   }

   public synchronized void close() throws SQLException {
      if (this.openOnServer_) {
         this.remote_CLOSE_CURSOR();
      }

      this.openOnServer_ = false;
      this.local_Close();
      if (this.statement_ != null) {
         this.statement_.resultSet_ = null;
      }

   }

   synchronized void remote_CLOSE_CURSOR() throws SQLException {
      if (!this.statement_.stmtHandle_.isSingletonResult()) {
         try {
            interbase.interclient.Ibase var10002 = this.statement_.connection_.ibase_;
            this.statement_.connection_.ibase_.iscDsqlFreeStatement(this.statement_.stmtHandle_, 1, this.sqlWarnings_);
            this.statement_.cancelable = false;
         } catch (interbase.interclient.IBException var2) {
            throw new SQLException(var2.toString());
         }
      }

   }

   void checkForValidCursorPosition() throws SQLException {
      if (!this.validCursorPosition_) {
         throw new interbase.interclient.InvalidOperationException(interbase.interclient.ErrorKey.invalidOperation__read_at_invalid_cursor_position__);
      }
   }

   public synchronized boolean wasNull() throws SQLException {
      this.checkForClosedCursor();
      if (this.wasNull_ == 0) {
         throw new InvalidOperationException(interbase.interclient.ErrorKey.invalidOperation__was_null_with_no_data_retrieved__);
      } else {
         return this.wasNull_ == 1;
      }
   }

   public synchronized String getString(int var1) throws SQLException {
      if (this.isNullPreamble(var1)) {
         return null;
      } else {
         try {
            switch(this.resultTypes_[var1 - 1]) {
            case 1:
            case 21:
               return String.valueOf(this.getRowData_short(var1 - 1));
            case 2:
               return String.valueOf(this.getRowData_int(var1 - 1));
            case 3:
               return String.valueOf(this.getRowData_float(var1 - 1));
            case 4:
               return String.valueOf(this.getRowData_double(var1 - 1));
            case 5:
            case 6:
            case 7:
               return this.getBigDecimal(var1, this.resultScales_[var1 - 1]).toString();
            case 8:
               return this.getRowData_char(var1 - 1);
            case 9:
               return this.getRowData_varchar(var1 - 1);
            case 10:
            case 12:
               long var2 = this.getRowData_blobId(var1 - 1);
               interbase.interclient.BlobOutput var4 = new interbase.interclient.BlobOutput(this.statement_, var2, this.statement_.connection_.btc_);
               return var4.getString();
            case 11:
               return this.getTimestamp(var1).toString();
            case 13:
            default:
               throw new interbase.interclient.BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 114);
            case 14:
               return "";
            case 15:
               return this.getDate(var1).toString();
            case 16:
               return this.getTime(var1).toString();
            case 17:
            case 18:
            case 19:
               return this.getBigDecimal(var1, this.resultScales_[var1 - 1]).toString();
            case 20:
               return this.getRowData_varchar_string(var1 - 1);
            }
         } catch (IOException var5) {
            throw new SQLException(var5.toString());
         }
      }
   }

   public synchronized boolean getBoolean(int var1) throws SQLException {
      return this.getInt(var1) != 0;
   }

   public synchronized byte getByte(int var1) throws SQLException {
      return (byte)this.getInt(var1);
   }

   public synchronized short getShort(int var1) throws SQLException {
      return (short)this.getInt(var1);
   }

   public synchronized int getInt(int var1) throws SQLException {
      if (this.isNullPreamble(var1)) {
         return 0;
      } else {
         switch(this.resultTypes_[var1 - 1]) {
         case 1:
         case 21:
            return this.getRowData_short(var1 - 1);
         case 2:
            return this.getRowData_int(var1 - 1);
         case 3:
            return (int)this.getRowData_float(var1 - 1);
         case 4:
         case 5:
            return (int)this.getRowData_double(var1 - 1);
         case 6:
            return BigDecimal.valueOf((long)this.getRowData_int(var1 - 1), this.resultScales_[var1 - 1]).intValue();
         case 7:
            return BigDecimal.valueOf((long)this.getRowData_short(var1 - 1), this.resultScales_[var1 - 1]).intValue();
         case 8:
         case 9:
         case 10:
         case 20:
            String var2 = this.getString(var1);

            try {
               return Integer.parseInt(var2.trim());
            } catch (NumberFormatException var4) {
               throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__instance_conversion_0__, var2);
            }
         case 11:
         case 13:
         case 14:
         case 15:
         case 16:
         default:
            throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__type_conversion__);
         case 12:
            throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__type_conversion__get_number_on_binary_blob__);
         case 17:
         case 18:
            return BigDecimal.valueOf(this.getRowData_long(var1 - 1), this.resultScales_[var1 - 1]).intValue();
         case 19:
            return BigDecimal.valueOf((long)this.getRowData_int(var1 - 1), this.resultScales_[var1 - 1]).intValue();
         }
      }
   }

   public synchronized long getLong(int var1) throws SQLException {
      if (this.isNullPreamble(var1)) {
         return 0L;
      } else {
         switch(this.resultTypes_[var1 - 1]) {
         case 1:
         case 21:
            return (long)this.getRowData_short(var1 - 1);
         case 2:
            return (long)this.getRowData_int(var1 - 1);
         case 3:
            return (long)this.getRowData_float(var1 - 1);
         case 4:
         case 5:
            return (long)this.getRowData_double(var1 - 1);
         case 6:
            return BigDecimal.valueOf((long)this.getRowData_int(var1 - 1), this.resultScales_[var1 - 1]).longValue();
         case 7:
            return BigDecimal.valueOf((long)this.getRowData_short(var1 - 1), this.resultScales_[var1 - 1]).longValue();
         case 8:
         case 9:
         case 10:
         case 20:
            String var2 = this.getString(var1);

            try {
               return Long.parseLong(var2);
            } catch (NumberFormatException var4) {
               throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__instance_conversion_0__, var2);
            }
         case 11:
         case 13:
         case 14:
         case 15:
         case 16:
         default:
            throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__type_conversion__);
         case 12:
            throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__type_conversion__get_number_on_binary_blob__);
         case 17:
         case 18:
            return BigDecimal.valueOf(this.getRowData_long(var1 - 1), this.resultScales_[var1 - 1]).longValue();
         case 19:
            return BigDecimal.valueOf((long)this.getRowData_int(var1 - 1), this.resultScales_[var1 - 1]).longValue();
         }
      }
   }

   public synchronized float getFloat(int var1) throws SQLException {
      if (this.isNullPreamble(var1)) {
         return 0.0F;
      } else {
         switch(this.resultTypes_[var1 - 1]) {
         case 1:
            return (float)this.getRowData_short(var1 - 1);
         case 2:
            return (float)this.getRowData_int(var1 - 1);
         case 3:
            return this.getRowData_float(var1 - 1);
         case 4:
         case 5:
            return (float)this.getRowData_double(var1 - 1);
         case 6:
            return BigDecimal.valueOf((long)this.getRowData_int(var1 - 1), this.resultScales_[var1 - 1]).floatValue();
         case 7:
            return BigDecimal.valueOf((long)this.getRowData_short(var1 - 1), this.resultScales_[var1 - 1]).floatValue();
         case 8:
         case 9:
         case 10:
         case 20:
            String var2 = this.getString(var1);

            try {
               return Float.valueOf(var2).floatValue();
            } catch (NumberFormatException var4) {
               throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__instance_conversion_0__, var2);
            }
         case 11:
         case 13:
         case 14:
         case 15:
         case 16:
         default:
            throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__type_conversion__);
         case 12:
            throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__type_conversion__get_number_on_binary_blob__);
         case 17:
         case 18:
            return BigDecimal.valueOf(this.getRowData_long(var1 - 1), this.resultScales_[var1 - 1]).floatValue();
         case 19:
            return BigDecimal.valueOf((long)this.getRowData_int(var1 - 1), this.resultScales_[var1 - 1]).floatValue();
         }
      }
   }

   public synchronized double getDouble(int var1) throws SQLException {
      if (this.isNullPreamble(var1)) {
         return 0.0D;
      } else {
         switch(this.resultTypes_[var1 - 1]) {
         case 1:
            return (double)this.getRowData_short(var1 - 1);
         case 2:
            return (double)this.getRowData_int(var1 - 1);
         case 3:
            return (double)this.getRowData_float(var1 - 1);
         case 4:
         case 5:
            return this.getRowData_double(var1 - 1);
         case 6:
            return BigDecimal.valueOf((long)this.getRowData_int(var1 - 1), this.resultScales_[var1 - 1]).doubleValue();
         case 7:
            return BigDecimal.valueOf((long)this.getRowData_short(var1 - 1), this.resultScales_[var1 - 1]).doubleValue();
         case 8:
         case 9:
         case 10:
         case 20:
            String var2 = this.getString(var1);

            try {
               return Double.valueOf(var2).doubleValue();
            } catch (NumberFormatException var4) {
               throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__instance_conversion_0__, var2);
            }
         case 11:
         case 13:
         case 14:
         case 15:
         case 16:
         default:
            throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__type_conversion__);
         case 12:
            throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__type_conversion__get_number_on_binary_blob__);
         case 17:
         case 18:
            return BigDecimal.valueOf(this.getRowData_long(var1 - 1), this.resultScales_[var1 - 1]).doubleValue();
         case 19:
            return BigDecimal.valueOf((long)this.getRowData_int(var1 - 1), this.resultScales_[var1 - 1]).doubleValue();
         }
      }
   }

   /** @deprecated */
   public synchronized BigDecimal getBigDecimal(int var1, int var2) throws SQLException {
      if (this.isNullPreamble(var1)) {
         return null;
      } else {
         Object var3 = null;
         switch(this.resultTypes_[var1 - 1]) {
         case 1:
            return BigDecimal.valueOf((long)this.getRowData_short(var1 - 1), var2);
         case 2:
            return BigDecimal.valueOf((long)this.getRowData_int(var1 - 1), var2);
         case 3:
            return (new BigDecimal((double)this.getRowData_float(var1 - 1))).setScale(var2, 6);
         case 4:
         case 5:
            return (new BigDecimal(this.getRowData_double(var1 - 1))).setScale(var2, 6);
         case 6:
            return BigDecimal.valueOf((long)this.getRowData_int(var1 - 1), this.resultScales_[var1 - 1]).setScale(var2, 6);
         case 7:
            return BigDecimal.valueOf((long)this.getRowData_short(var1 - 1), this.resultScales_[var1 - 1]).setScale(var2, 6);
         case 8:
         case 9:
         case 10:
         case 20:
            String var4 = this.getString(var1);

            try {
               return (new BigDecimal(var4)).setScale(var2, 6);
            } catch (NumberFormatException var6) {
               throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__instance_conversion_0__, var4);
            }
         case 11:
         case 13:
         case 14:
         case 15:
         case 16:
         default:
            throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__type_conversion__);
         case 12:
            throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__type_conversion__get_number_on_binary_blob__);
         case 17:
         case 18:
            return BigDecimal.valueOf(this.getRowData_long(var1 - 1), this.resultScales_[var1 - 1]).setScale(var2, 6);
         case 19:
            return BigDecimal.valueOf((long)this.getRowData_int(var1 - 1), this.resultScales_[var1 - 1]).setScale(var2, 6);
         }
      }
   }

   public synchronized byte[] getBytes(int var1) throws SQLException {
      if (this.isNullPreamble(var1)) {
         return null;
      } else {
         try {
            switch(this.resultTypes_[var1 - 1]) {
            case 8:
            case 9:
               return this.getRowData_bytes(var1 - 1);
            case 10:
            case 12:
               long var2 = this.getRowData_blobId(var1 - 1);
               interbase.interclient.BlobOutput var4 = new interbase.interclient.BlobOutput(this.statement_, var2, this.statement_.connection_.btc_);
               return var4.getBytes();
            case 11:
            default:
               throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__type_conversion__);
            }
         } catch (IOException var5) {
            return null;
         }
      }
   }

   public synchronized Date getDate(int var1) throws SQLException {
      if (this.isNullPreamble(var1)) {
         return null;
      } else {
         switch(this.resultTypes_[var1 - 1]) {
         case 8:
         case 9:
         case 10:
         case 20:
            String var5 = this.getString(var1);

            try {
               return Date.valueOf(var5);
            } catch (IllegalArgumentException var4) {
               throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__instance_conversion_0__, var5);
            }
         case 11:
            if (this.adaptToSingleInstanceTime_) {
               this.adaptableDate_.setTime(this.getRowData_timestampId(var1 - 1).getTime());
               return this.adaptableDate_;
            }

            Date var2 = new Date(this.getRowData_timestampId(var1 - 1).getTime());
            return var2;
         case 12:
            throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__type_conversion__get_date_on_binary_blob__);
         case 13:
         case 14:
         case 16:
         case 17:
         case 18:
         case 19:
         default:
            throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__type_conversion__);
         case 15:
            return this.getRowData_sqldate(var1 - 1);
         }
      }
   }

   public synchronized Time getTime(int var1) throws SQLException {
      if (this.isNullPreamble(var1)) {
         return null;
      } else {
         Time var2;
         switch(this.resultTypes_[var1 - 1]) {
         case 8:
         case 9:
         case 10:
         case 20:
            String var5 = this.getString(var1);

            try {
               return Time.valueOf(var5.trim());
            } catch (IllegalArgumentException var4) {
               throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__instance_conversion_0__, var5);
            }
         case 11:
            if (this.adaptToSingleInstanceTime_) {
               this.adaptableTime_.setTime(this.getRowData_timestampId(var1 - 1).getTime());
               return this.adaptableTime_;
            }

            var2 = new Time(this.getRowData_timestampId(var1 - 1).getTime());
            return var2;
         case 12:
            throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__type_conversion__get_date_on_binary_blob__);
         case 13:
         case 14:
         case 15:
         case 17:
         case 18:
         case 19:
         default:
            throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__type_conversion__);
         case 16:
            if (this.adaptToSingleInstanceTime_) {
               this.adaptableTime_.setTime(this.getRowData_sqltime(var1 - 1).getTime());
               return this.adaptableTime_;
            } else {
               var2 = new Time(this.getRowData_sqltime(var1 - 1).getTime());
               return var2;
            }
         }
      }
   }

   public synchronized Timestamp getTimestamp(int var1) throws SQLException {
      if (this.isNullPreamble(var1)) {
         return null;
      } else {
         switch(this.resultTypes_[var1 - 1]) {
         case 8:
         case 9:
         case 10:
         case 20:
            String var8 = this.getString(var1);

            try {
               return Timestamp.valueOf(var8.trim());
            } catch (IllegalArgumentException var5) {
               throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__instance_conversion_0__, var8);
            }
         case 11:
            if (this.adaptToSingleInstanceTime_) {
               this.adaptableTimestamp_ = (Timestamp)this.getRowData_timestampId(var1 - 1).clone();
               return this.adaptableTimestamp_;
            }

            Timestamp var6 = (Timestamp)this.getRowData_timestampId(var1 - 1).clone();
            return var6;
         case 12:
            throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__type_conversion__get_date_on_binary_blob__);
         case 13:
         case 14:
         case 17:
         case 18:
         case 19:
         default:
            throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__type_conversion__);
         case 15:
            int var2 = this.getRowData_int(var1 - 1);
            if (this.adaptToSingleInstanceTime_) {
               this.adaptableIBTimestamp_.setDateTime(0, var2);
               this.adaptableTimestamp_.setTime(this.adaptableIBTimestamp_.getTimeInMillis());
               this.adaptableTimestamp_.setNanos(this.adaptableIBTimestamp_.getNanos());
               return this.adaptableTimestamp_;
            }

            interbase.interclient.IBTimestamp var7 = new interbase.interclient.IBTimestamp(0, var2);
            Timestamp var4 = new Timestamp(var7.getTimeInMillis());
            var4.setNanos(var7.getNanos());
            return var4;
         case 16:
            if (this.adaptToSingleInstanceTime_) {
               this.adaptableTimestamp_.setTime(this.getRowData_sqltime(var1 - 1).getTime());
               return this.adaptableTimestamp_;
            } else {
               Timestamp var3 = new Timestamp(this.getRowData_sqltime(var1 - 1).getTime());
               return var3;
            }
         }
      }
   }

   public synchronized InputStream getAsciiStream(int var1) throws SQLException {
      if (this.isNullPreamble(var1)) {
         return null;
      } else {
         switch(this.resultTypes_[var1 - 1]) {
         case 8:
         case 9:
            return new ByteArrayInputStream(this.getRowData_bytes(var1 - 1));
         case 10:
         case 12:
            long var2 = this.getRowData_blobId(var1 - 1);
            interbase.interclient.BlobOutput var4 = new interbase.interclient.BlobOutput(this.statement_, var2, this.statement_.connection_.btc_);
            return var4.getInputStream();
         case 11:
         default:
            throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__type_conversion__);
         }
      }
   }

   /** @deprecated */
   public synchronized InputStream getUnicodeStream(int var1) throws SQLException {
      if (this.isNullPreamble(var1)) {
         return null;
      } else {
         switch(this.resultTypes_[var1 - 1]) {
         case 8:
            return new interbase.interclient.ByteToUnicodeConverterStream(this.getRowData_char(var1 - 1));
         case 9:
            return new interbase.interclient.ByteToUnicodeConverterStream(this.getRowData_varchar(var1 - 1));
         case 10:
         case 12:
            long var2 = this.getRowData_blobId(var1 - 1);
            interbase.interclient.BlobOutput var4 = new interbase.interclient.BlobOutput(this.statement_, var2, this.statement_.connection_.btc_);
            return var4.getUnicodeInputStream();
         case 11:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         default:
            throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__type_conversion__);
         case 20:
            return new interbase.interclient.ByteToUnicodeConverterStream(this.getRowData_varchar_string(var1 - 1));
         }
      }
   }

   public synchronized InputStream getBinaryStream(int var1) throws SQLException {
      if (this.isNullPreamble(var1)) {
         return null;
      } else {
         switch(this.resultTypes_[var1 - 1]) {
         case 8:
         case 9:
            return new ByteArrayInputStream(this.getRowData_bytes(var1 - 1));
         case 10:
         case 12:
            long var2 = this.getRowData_blobId(var1 - 1);
            interbase.interclient.BlobOutput var4 = new interbase.interclient.BlobOutput(this.statement_, var2, this.statement_.connection_.btc_);
            return var4.getInputStream();
         case 11:
         default:
            throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__type_conversion__);
         }
      }
   }

   public synchronized String getString(String var1) throws SQLException {
      return this.getString(this.findColumn(var1));
   }

   public synchronized boolean getBoolean(String var1) throws SQLException {
      return this.getBoolean(this.findColumn(var1));
   }

   public synchronized byte getByte(String var1) throws SQLException {
      return this.getByte(this.findColumn(var1));
   }

   public synchronized short getShort(String var1) throws SQLException {
      return this.getShort(this.findColumn(var1));
   }

   public synchronized int getInt(String var1) throws SQLException {
      return this.getInt(this.findColumn(var1));
   }

   public synchronized long getLong(String var1) throws SQLException {
      return this.getLong(this.findColumn(var1));
   }

   public synchronized float getFloat(String var1) throws SQLException {
      return this.getFloat(this.findColumn(var1));
   }

   public synchronized double getDouble(String var1) throws SQLException {
      return this.getDouble(this.findColumn(var1));
   }

   /** @deprecated */
   public synchronized BigDecimal getBigDecimal(String var1, int var2) throws SQLException {
      return this.getBigDecimal(this.findColumn(var1), var2);
   }

   public synchronized byte[] getBytes(String var1) throws SQLException {
      return this.getBytes(this.findColumn(var1));
   }

   public synchronized Date getDate(String var1) throws SQLException {
      return this.getDate(this.findColumn(var1));
   }

   public synchronized Time getTime(String var1) throws SQLException {
      return this.getTime(this.findColumn(var1));
   }

   public synchronized Timestamp getTimestamp(String var1) throws SQLException {
      return this.getTimestamp(this.findColumn(var1));
   }

   public synchronized InputStream getAsciiStream(String var1) throws SQLException {
      return this.getAsciiStream(this.findColumn(var1));
   }

   /** @deprecated */
   public synchronized InputStream getUnicodeStream(String var1) throws SQLException {
      return this.getUnicodeStream(this.findColumn(var1));
   }

   public synchronized InputStream getBinaryStream(String var1) throws SQLException {
      return this.getBinaryStream(this.findColumn(var1));
   }

   public synchronized java.sql.SQLWarning getWarnings() throws SQLException {
      return new java.sql.SQLWarning(this.sqlWarnings_.getMessage());
   }

   public synchronized void clearWarnings() throws SQLException {
      this.sqlWarnings_ = null;
   }

   public synchronized String getCursorName() throws SQLException {
      return this.statement_.cursorName_;
   }

   public synchronized java.sql.ResultSetMetaData getMetaData() throws SQLException {
      this.checkForClosedCursor();
      if (this.resultSetMetaData_ == null) {
         this.resultSetMetaData_ = new ResultSetMetaData(this);
      }

      return this.resultSetMetaData_;
   }

   public synchronized Object getObject(int var1) throws SQLException {
      if (this.isNullPreamble(var1)) {
         return null;
      } else {
         switch(this.resultTypes_[var1 - 1]) {
         case 1:
            return new Integer(this.getShort(var1));
         case 2:
            return new Integer(this.getInt(var1));
         case 3:
            return new Float(this.getFloat(var1));
         case 4:
            return new Double(this.getDouble(var1));
         case 5:
         case 6:
         case 7:
            return this.getBigDecimal(var1, this.resultScales_[var1 - 1]);
         case 8:
         case 9:
         case 10:
         case 20:
            return this.getString(var1);
         case 11:
            return this.getTimestamp(var1);
         case 12:
            return this.getBytes(var1);
         case 13:
         default:
            throw new BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 115);
         case 14:
            return this.getArray(var1);
         case 15:
            return this.getDate(var1);
         case 16:
            return this.getTime(var1);
         case 17:
         case 18:
         case 19:
            return this.getBigDecimal(var1, this.resultScales_[var1 - 1]);
         case 21:
            return this.getShort(var1) == 0 ? new Boolean(false) : new Boolean(true);
         }
      }
   }

   public synchronized Object getObject(String var1) throws SQLException {
      return this.getObject(this.findColumn(var1));
   }

   public synchronized int findColumn(String var1) throws SQLException {
      this.checkForClosedCursor();
      if (this.columnNameToIndexCache_ == null) {
         this.columnNameToIndexCache_ = new Hashtable();
      } else {
         Integer var2 = (Integer)this.columnNameToIndexCache_.get(var1);
         if (var2 != null) {
            return var2.intValue();
         }
      }

      for(int var5 = 0; var5 < this.resultCols_; ++var5) {
         boolean var3 = false;
         int var4 = var1.length() - 1;
         if ('"' == var1.charAt(0) && '"' == var1.charAt(var4)) {
            if (this.xsqlda_.sqlvar[var5].sqlname.equals(var1.substring(1, var4)) || this.xsqlda_.sqlvar[var5].aliasname != null && this.xsqlda_.sqlvar[var5].aliasname.equals(var1.substring(1, var4))) {
               var3 = true;
            }
         } else if (this.xsqlda_.sqlvar[var5].sqlname.equalsIgnoreCase(var1) || this.xsqlda_.sqlvar[var5].aliasname != null && this.xsqlda_.sqlvar[var5].aliasname.equalsIgnoreCase(var1)) {
            var3 = true;
         }

         if (var3) {
            this.columnNameToIndexCache_.put(var1, new Integer(var5 + 1));
            return var5 + 1;
         }
      }

      throw new InvalidArgumentException(interbase.interclient.ErrorKey.invalidArgument__column_name_0__, var1);
   }

   void setResultSetMetaData(interbase.interclient.XSQLDA var1, boolean var2) throws SQLException {
      for(int var3 = 0; var3 < this.resultCols_; ++var3) {
         if (var2) {
            var1.sqlvar[var3].aliasname = var1.sqlvar[var3].sqlname;
         }

         this.resultTypes_[var3] = interbase.interclient.IBTypes.getResultTypes(var1.sqlvar[var3].sqltype, var1.sqlvar[var3]);
         this.resultScales_[var3] = Math.abs(var1.sqlvar[var3].sqlscale);
         if (this.resultTypes_[var3] == 14) {
            this.arrayDescriptors_[var3] = new interbase.interclient.ArrayDescriptor(this.statement_.connection_, var1.sqlvar[var3].relname, var1.sqlvar[var3].sqlname);
         }
      }

   }

   public synchronized Reader getCharacterStream(int var1) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Reader getCharacterStream(String var1) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized BigDecimal getBigDecimal(int var1) throws SQLException {
      if (this.isNullPreamble(var1)) {
         return null;
      } else {
         Object var2 = null;
         switch(this.resultTypes_[var1 - 1]) {
         case 1:
         case 21:
            return BigDecimal.valueOf((long)this.getRowData_short(var1 - 1), this.resultScales_[var1 - 1]);
         case 2:
            return BigDecimal.valueOf((long)this.getRowData_int(var1 - 1), this.resultScales_[var1 - 1]);
         case 3:
            return (new BigDecimal((double)this.getRowData_float(var1 - 1))).setScale(this.resultScales_[var1 - 1], 6);
         case 4:
         case 5:
            return (new BigDecimal(this.getRowData_double(var1 - 1))).setScale(this.resultScales_[var1 - 1], 6);
         case 6:
            return BigDecimal.valueOf((long)this.getRowData_int(var1 - 1), this.resultScales_[var1 - 1]).setScale(this.resultScales_[var1 - 1], 6);
         case 7:
            return BigDecimal.valueOf((long)this.getRowData_short(var1 - 1), this.resultScales_[var1 - 1]).setScale(this.resultScales_[var1 - 1], 6);
         case 8:
         case 9:
         case 10:
         case 20:
            String var3 = this.getString(var1);

            try {
               return (new BigDecimal(var3)).setScale(this.resultScales_[var1 - 1], 6);
            } catch (NumberFormatException var5) {
               throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__instance_conversion_0__, var3);
            }
         case 11:
         case 13:
         case 14:
         case 15:
         case 16:
         default:
            throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__type_conversion__);
         case 12:
            throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__type_conversion__get_number_on_binary_blob__);
         case 17:
         case 18:
            return BigDecimal.valueOf(this.getRowData_long(var1 - 1), this.resultScales_[var1 - 1]).setScale(this.resultScales_[var1 - 1], 6);
         case 19:
            return BigDecimal.valueOf((long)this.getRowData_int(var1 - 1), this.resultScales_[var1 - 1]).setScale(this.resultScales_[var1 - 1], 6);
         }
      }
   }

   public synchronized BigDecimal getBigDecimal(String var1) throws SQLException {
      return this.getBigDecimal(this.findColumn(var1));
   }

   public synchronized boolean isBeforeFirst() throws SQLException {
      if (this.getType() == 1003) {
         throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
      } else if (this.fetched_ && this.statement_.stmtHandle_.rows != null) {
         return this.currentPosition_ == 0L;
      } else {
         throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.invalidOperation__result_set_closed__);
      }
   }

   public synchronized boolean isAfterLast() throws SQLException {
      if (this.getType() == 1003) {
         throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
      } else if (this.fetched_ && this.statement_.stmtHandle_.rows != null) {
         return this.currentPosition_ == (long)(this.statement_.stmtHandle_.rows.size() + 1);
      } else {
         throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.invalidOperation__result_set_closed__);
      }
   }

   public synchronized boolean isFirst() throws SQLException {
      if (this.getType() == 1003) {
         throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
      } else if (this.fetched_ && this.statement_.stmtHandle_.rows != null) {
         if (this.currentPosition_ == 1L) {
            this.validCursorPosition_ = true;
            return true;
         } else {
            return false;
         }
      } else {
         throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.invalidOperation__result_set_closed__);
      }
   }

   public synchronized boolean isLast() throws SQLException {
      if (this.getType() == 1003) {
         throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
      } else if (this.fetched_ && this.statement_.stmtHandle_.rows != null) {
         if (this.currentPosition_ == (long)this.statement_.stmtHandle_.rows.size()) {
            this.validCursorPosition_ = true;
            return true;
         } else {
            return false;
         }
      } else {
         throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.invalidOperation__result_set_closed__);
      }
   }

   public synchronized void beforeFirst() throws SQLException {
      if (this.getType() == 1003) {
         throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
      } else {
         if (this.fetched_ && this.statement_.stmtHandle_.rows != null) {
            this.currentPosition_ = 0L;
            this.validCursorPosition_ = false;
         }

      }
   }

   public synchronized void afterLast() throws SQLException {
      if (this.getType() == 1003) {
         throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
      } else {
         if (this.fetched_ && this.statement_.stmtHandle_.rows != null) {
            this.currentPosition_ = (long)(this.statement_.stmtHandle_.rows.size() + 1);
            this.validCursorPosition_ = false;
         }

      }
   }

   public synchronized boolean first() throws SQLException {
      if (this.getType() == 1003) {
         throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
      } else if (this.fetched_ && this.statement_.stmtHandle_.rows != null) {
         this.currentPosition_ = 1L;
         this.validCursorPosition_ = true;
         return true;
      } else {
         this.validCursorPosition_ = false;
         return false;
      }
   }

   public synchronized boolean last() throws SQLException {
      if (this.getType() == 1003) {
         throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
      } else if (this.fetched_ && this.statement_.stmtHandle_.rows != null) {
         this.currentPosition_ = (long)this.statement_.stmtHandle_.rows.size();
         this.validCursorPosition_ = true;
         return true;
      } else {
         this.validCursorPosition_ = false;
         return false;
      }
   }

   public synchronized int getRow() throws SQLException {
      return this.fetched_ ? (int)this.currentPosition_ : 0;
   }

   public synchronized boolean absolute(int var1) throws SQLException {
      if (this.getType() == 1003) {
         throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
      } else if (this.fetched_) {
         if (var1 > 0) {
            if (var1 <= this.statement_.stmtHandle_.rows.size()) {
               this.currentPosition_ = (long)var1;
            } else {
               this.currentPosition_ = (long)(var1 + 1);
            }
         } else {
            if (var1 >= 0) {
               this.currentPosition_ = 0L;
               this.validCursorPosition_ = false;
               return false;
            }

            this.currentPosition_ = (long)(this.statement_.stmtHandle_.rows.size() - var1);
            if (this.currentPosition_ < 0L) {
               this.currentPosition_ = 0L;
               this.validCursorPosition_ = false;
               return false;
            }
         }

         this.validCursorPosition_ = true;
         return true;
      } else {
         return false;
      }
   }

   public synchronized boolean relative(int var1) throws SQLException {
      if (this.getType() != 1003 && this.fetched_) {
         long var2 = this.currentPosition_ + (long)var1;
         if (var2 >= (long)this.statement_.stmtHandle_.rows.size()) {
            this.afterLast();
            return false;
         } else if (this.currentPosition_ <= 1L) {
            this.beforeFirst();
            return false;
         } else {
            this.currentPosition_ = var2;
            this.validCursorPosition_ = true;
            return true;
         }
      } else {
         throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
      }
   }

   public synchronized boolean previous() throws SQLException {
      if (this.getType() != 1003 && this.fetched_) {
         if (this.fetched_ && this.currentPosition_ > 1L) {
            --this.currentPosition_;
            this.validCursorPosition_ = true;
            return true;
         } else {
            this.validCursorPosition_ = false;
            return false;
         }
      } else {
         throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
      }
   }

   public synchronized void setFetchDirection(int var1) throws SQLException {
      if (var1 != 1000) {
         throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__scrolling_not_supported__);
      }
   }

   public synchronized int getFetchDirection() throws SQLException {
      return 1000;
   }

   public synchronized void setFetchSize(int var1) throws SQLException {
      this.statement_.setFetchSize(var1);
   }

   public synchronized int getFetchSize() throws SQLException {
      return this.statement_.getFetchSize();
   }

   public synchronized int getType() throws SQLException {
      return this.resultSetType_;
   }

   public synchronized int getConcurrency() throws SQLException {
      return 1007;
   }

   public synchronized boolean rowUpdated() throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__scrolling_not_supported__);
   }

   public synchronized boolean rowInserted() throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__scrolling_not_supported__);
   }

   public synchronized boolean rowDeleted() throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__scrolling_not_supported__);
   }

   public synchronized void updateNull(int var1) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateBoolean(int var1, boolean var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateByte(int var1, byte var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateShort(int var1, short var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateInt(int var1, int var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateLong(int var1, long var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateFloat(int var1, float var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateDouble(int var1, double var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateBigDecimal(int var1, BigDecimal var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateString(int var1, String var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateBytes(int var1, byte[] var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateDate(int var1, Date var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateTime(int var1, Time var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateTimestamp(int var1, Timestamp var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateAsciiStream(int var1, InputStream var2, int var3) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateBinaryStream(int var1, InputStream var2, int var3) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateCharacterStream(int var1, Reader var2, int var3) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateObject(int var1, Object var2, int var3) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateObject(int var1, Object var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateNull(String var1) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateBoolean(String var1, boolean var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateByte(String var1, byte var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateShort(String var1, short var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateInt(String var1, int var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateLong(String var1, long var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateFloat(String var1, float var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateDouble(String var1, double var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateBigDecimal(String var1, BigDecimal var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateString(String var1, String var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateBytes(String var1, byte[] var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateDate(String var1, Date var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateTime(String var1, Time var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateTimestamp(String var1, Timestamp var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateAsciiStream(String var1, InputStream var2, int var3) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateBinaryStream(String var1, InputStream var2, int var3) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateCharacterStream(String var1, Reader var2, int var3) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateObject(String var1, Object var2, int var3) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateObject(String var1, Object var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void insertRow() throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateRow() throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void deleteRow() throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void refreshRow() throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void cancelRowUpdates() throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void moveToInsertRow() throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void moveToCurrentRow() throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized java.sql.Statement getStatement() throws SQLException {
      return this.statement_;
   }

   public synchronized Object getObject(int var1, Map var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Ref getRef(int var1) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized java.sql.Blob getBlob(int var1) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized java.sql.Clob getClob(int var1) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized java.sql.Array getArray(int var1) throws SQLException {
      if (this.isNullPreamble(var1)) {
         return null;
      } else {
         switch(this.resultTypes_[var1 - 1]) {
         case 14:
            return new Array(this.getRowData_arrayId(var1 - 1), this.arrayDescriptors_[var1 - 1], this.statement_.connection_);
         default:
            throw new ColumnConversionException(interbase.interclient.ErrorKey.columnConversion__type_conversion__);
         }
      }
   }

   public synchronized Object getObject(String var1, Map var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Ref getRef(String var1) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized java.sql.Blob getBlob(String var1) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized java.sql.Clob getClob(String var1) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized java.sql.Array getArray(String var1) throws SQLException {
      return this.getArray(this.findColumn(var1));
   }

   public synchronized Date getDate(int var1, Calendar var2) throws SQLException {
      Date var3 = this.getDate(var1);
      if (var2 == null) {
         return var3;
      } else {
         var2.setTime(var3);
         return new Date(var2.getTime().getTime());
      }
   }

   public synchronized Date getDate(String var1, Calendar var2) throws SQLException {
      return this.getDate(this.findColumn(var1), var2);
   }

   public synchronized Time getTime(int var1, Calendar var2) throws SQLException {
      Time var3 = this.getTime(var1);
      if (var2 == null) {
         return var3;
      } else {
         var2.setTime(var3);
         return new Time(var2.getTime().getTime());
      }
   }

   public synchronized Time getTime(String var1, Calendar var2) throws SQLException {
      return this.getTime(this.findColumn(var1), var2);
   }

   public synchronized Timestamp getTimestamp(int var1, Calendar var2) throws SQLException {
      Timestamp var3 = this.getTimestamp(var1);
      if (var2 == null) {
         return var3;
      } else {
         long var4 = var3.getTime() + (long)var2.getTimeZone().getRawOffset();
         return new Timestamp(var4);
      }
   }

   public synchronized Timestamp getTimestamp(String var1, Calendar var2) throws SQLException {
      return this.getTimestamp(this.findColumn(var1), var2);
   }

   public synchronized boolean isNull(int var1) throws SQLException {
      this.checkForClosedCursor();
      this.checkForValidCursorPosition();

      try {
         return this.isNullType(var1 - 1);
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new interbase.interclient.ColumnIndexOutOfBoundsException(interbase.interclient.ErrorKey.columnIndexOutOfBounds__0__, var1);
      }
   }

   public synchronized boolean adapt(int var1, Object var2) throws SQLException {
      switch(var1) {
      case 1:
         this.adaptToRightTrimString_ = true;
         return true;
      case 2:
         this.adaptToSingleInstanceTime_ = true;
         this.adaptableIBTimestamp_ = new IBTimestamp(0, 0, 1);
         this.adaptableDate_ = new Date(0L);
         this.adaptableTime_ = new Time(0L);
         this.adaptableTimestamp_ = new Timestamp(0L);
         return true;
      default:
         return false;
      }
   }

   public synchronized void revert(int var1) throws SQLException {
      switch(var1) {
      case 1:
         this.adaptToRightTrimString_ = false;
         break;
      case 2:
         this.adaptToSingleInstanceTime_ = false;
      }

   }

   private synchronized boolean isNullPreamble(int var1) throws SQLException {
      this.checkForClosedCursor();
      this.checkForValidCursorPosition();

      try {
         if (this.isNullType(var1 - 1)) {
            this.wasNull_ = 1;
            return true;
         } else {
            this.wasNull_ = 2;
            return false;
         }
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new ColumnIndexOutOfBoundsException(interbase.interclient.ErrorKey.columnIndexOutOfBounds__0__, var1);
      }
   }

   private boolean isNullType(int var1) {
      Object[] var2 = (Object[])this.statement_.stmtHandle_.rows.get((int)(this.currentPosition_ - 1L));
      return var2[var1] == null;
   }

   public synchronized URL getURL(int var1) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized URL getURL(String var1) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateRef(int var1, Ref var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateRef(String var1, Ref var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateBlob(int var1, java.sql.Blob var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateBlob(String var1, java.sql.Blob var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateClob(int var1, java.sql.Clob var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateClob(String var1, java.sql.Clob var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateArray(int var1, java.sql.Array var2) throws SQLException {
      throw new interbase.interclient.DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void updateArray(String var1, java.sql.Array var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   @Override
   public RowId getRowId(int columnIndex) throws SQLException {
      return null;
   }

   @Override
   public RowId getRowId(String columnLabel) throws SQLException {
      return null;
   }

   @Override
   public void updateRowId(int columnIndex, RowId x) throws SQLException {

   }

   @Override
   public void updateRowId(String columnLabel, RowId x) throws SQLException {

   }

   @Override
   public int getHoldability() throws SQLException {
      return 0;
   }

   @Override
   public boolean isClosed() throws SQLException {
      return false;
   }

   @Override
   public void updateNString(int columnIndex, String nString) throws SQLException {

   }

   @Override
   public void updateNString(String columnLabel, String nString) throws SQLException {

   }

   @Override
   public void updateNClob(int columnIndex, NClob nClob) throws SQLException {

   }

   @Override
   public void updateNClob(String columnLabel, NClob nClob) throws SQLException {

   }

   @Override
   public NClob getNClob(int columnIndex) throws SQLException {
      return null;
   }

   @Override
   public NClob getNClob(String columnLabel) throws SQLException {
      return null;
   }

   @Override
   public SQLXML getSQLXML(int columnIndex) throws SQLException {
      return null;
   }

   @Override
   public SQLXML getSQLXML(String columnLabel) throws SQLException {
      return null;
   }

   @Override
   public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {

   }

   @Override
   public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {

   }

   @Override
   public String getNString(int columnIndex) throws SQLException {
      return null;
   }

   @Override
   public String getNString(String columnLabel) throws SQLException {
      return null;
   }

   @Override
   public Reader getNCharacterStream(int columnIndex) throws SQLException {
      return null;
   }

   @Override
   public Reader getNCharacterStream(String columnLabel) throws SQLException {
      return null;
   }

   @Override
   public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {

   }

   @Override
   public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {

   }

   @Override
   public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {

   }

   @Override
   public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {

   }

   @Override
   public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {

   }

   @Override
   public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {

   }

   @Override
   public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {

   }

   @Override
   public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {

   }

   @Override
   public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {

   }

   @Override
   public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {

   }

   @Override
   public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {

   }

   @Override
   public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {

   }

   @Override
   public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {

   }

   @Override
   public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {

   }

   @Override
   public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {

   }

   @Override
   public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {

   }

   @Override
   public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {

   }

   @Override
   public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {

   }

   @Override
   public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {

   }

   @Override
   public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {

   }

   @Override
   public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {

   }

   @Override
   public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {

   }

   @Override
   public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {

   }

   @Override
   public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {

   }

   @Override
   public void updateClob(int columnIndex, Reader reader) throws SQLException {

   }

   @Override
   public void updateClob(String columnLabel, Reader reader) throws SQLException {

   }

   @Override
   public void updateNClob(int columnIndex, Reader reader) throws SQLException {

   }

   @Override
   public void updateNClob(String columnLabel, Reader reader) throws SQLException {

   }

   @Override
   public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
      return null;
   }

   @Override
   public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
      return null;
   }

   @Override
   public <T> T unwrap(Class<T> iface) throws SQLException {
      return null;
   }

   @Override
   public boolean isWrapperFor(Class<?> iface) throws SQLException {
      return false;
   }
}
