package interbase.interclient;

import java.sql.SQLException;

public final class ResultSetMetaData implements java.sql.ResultSetMetaData {
   ResultSet resultSet_;
   boolean metaDataRetrieved_ = false;
   boolean[] writables_ = null;
   public static final int columnNoNulls = 0;
   public static final int columnNullable = 1;
   public static final int columnNullableUnknown = 2;
   static final String isWriteableQuery = "select RF.RDB$FIELD_NAME from RDB$RELATIONS REL, RDB$RELATION_FIELDS RF, RDB$FIELDS FLD, RDB$USER_PRIVILEGES PRV  where RF.RDB$FIELD_NAME = ? and REL.RDB$RELATION_NAME = ? and REL.RDB$RELATION_NAME = RF.RDB$RELATION_NAME and RF.RDB$FIELD_SOURCE = FLD.RDB$FIELD_NAME and REL.RDB$VIEW_BLR IS NULL and FLD.RDB$COMPUTED_BLR IS NULL and PRV.RDB$RELATION_NAME = REL.RDB$RELATION_NAME and (PRV.RDB$FIELD_NAME = RF.RDB$FIELD_NAME or  PRV.RDB$FIELD_NAME IS NULL) and PRV.RDB$PRIVILEGE in ('A', 'I', 'U', 'D') and (PRV.RDB$USER = 'PUBLIC' or  PRV.RDB$USER = ?)";

   ResultSetMetaData(ResultSet var1) {
      this.resultSet_ = var1;
   }

   public int getColumnCount() throws SQLException {
      this.resultSet_.checkForClosedCursor();
      return this.resultSet_.resultCols_;
   }

   public boolean isAutoIncrement(int var1) throws SQLException {
      this.resultSet_.checkForClosedCursor();
      return false;
   }

   public boolean isCaseSensitive(int var1) throws SQLException {
      this.resultSet_.checkForClosedCursor();
      return true;
   }

   public boolean isSearchable(int var1) throws SQLException {
      this.resultSet_.checkForClosedCursor();
      return true;
   }

   public boolean isCurrency(int var1) throws SQLException {
      this.resultSet_.checkForClosedCursor();
      return false;
   }

   public int isNullable(int var1) throws SQLException {
      this.resultSet_.checkForClosedCursor();

      try {
         return (this.resultSet_.xsqlda_.sqlvar[var1 - 1].sqltype & 1) == 1 ? 1 : 0;
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new ColumnIndexOutOfBoundsException(ErrorKey.columnIndexOutOfBounds__0__, var1);
      }
   }

   public boolean isSigned(int var1) throws SQLException {
      this.resultSet_.checkForClosedCursor();

      try {
         switch(this.resultSet_.resultTypes_[var1 - 1]) {
         case 0:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 20:
         default:
            return false;
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 17:
         case 18:
         case 19:
            return true;
         }
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new ColumnIndexOutOfBoundsException(ErrorKey.columnIndexOutOfBounds__0__, var1);
      }
   }

   public int getColumnDisplaySize(int var1) throws SQLException {
      try {
         this.resultSet_.checkForClosedCursor();
         return IBTypes.getColumnSize(this.resultSet_.resultTypes_[var1 - 1], IBTypes.getCharacterLength(this.resultSet_.xsqlda_.sqlvar[var1 - 1]));
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new ColumnIndexOutOfBoundsException(ErrorKey.columnIndexOutOfBounds__0__, var1);
      }
   }

   public String getColumnLabel(int var1) throws SQLException {
      this.resultSet_.checkForClosedCursor();

      try {
         return this.resultSet_.xsqlda_.sqlvar[var1 - 1].aliasname;
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new ColumnIndexOutOfBoundsException(ErrorKey.columnIndexOutOfBounds__0__, var1);
      }
   }

   public String getColumnName(int var1) throws SQLException {
      this.resultSet_.checkForClosedCursor();

      try {
         return this.resultSet_.xsqlda_.sqlvar[var1 - 1].sqlname;
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new ColumnIndexOutOfBoundsException(ErrorKey.columnIndexOutOfBounds__0__, var1);
      }
   }

   public String getSchemaName(int var1) throws SQLException {
      this.resultSet_.checkForClosedCursor();
      return "";
   }

   public int getPrecision(int var1) throws SQLException {
      this.resultSet_.checkForClosedCursor();

      try {
         switch(this.resultSet_.resultTypes_[var1 - 1]) {
         case 14:
            return this.resultSet_.arrayDescriptors_[var1 - 1].elementPrecision_;
         default:
            return this.resultSet_.statement_.connection_.databaseMetaData_.ibMinorVersion_ < 0 && this.resultSet_.statement_.connection_.databaseMetaData_.databaseSQLDialect_ <= 2 ? IBTypes.getPrecision(this.resultSet_.resultTypes_[var1 - 1], IBTypes.getCharacterLength(this.resultSet_.xsqlda_.sqlvar[var1 - 1])) : IBTypes.getPrecision(this.resultSet_.xsqlda_.sqlvar[var1 - 1], this.resultSet_.resultTypes_[var1 - 1]);
         }
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new ColumnIndexOutOfBoundsException(ErrorKey.columnIndexOutOfBounds__0__, var1);
      }
   }

   public int getScale(int var1) throws SQLException {
      this.resultSet_.checkForClosedCursor();

      try {
         switch(this.resultSet_.resultTypes_[var1 - 1]) {
         case 8:
         case 9:
         case 20:
            return 0;
         case 14:
            return this.resultSet_.arrayDescriptors_[var1 - 1].elementScale_;
         default:
            return this.resultSet_.resultScales_[var1 - 1];
         }
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new ColumnIndexOutOfBoundsException(ErrorKey.columnIndexOutOfBounds__0__, var1);
      }
   }

   public synchronized String getTableName(int var1) throws SQLException {
      this.resultSet_.checkForClosedCursor();

      try {
         return this.resultSet_.xsqlda_.sqlvar[var1 - 1].relname != null ? this.resultSet_.xsqlda_.sqlvar[var1 - 1].relname : new String("");
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new ColumnIndexOutOfBoundsException(ErrorKey.columnIndexOutOfBounds__0__, var1);
      }
   }

   public String getCatalogName(int var1) throws SQLException {
      this.resultSet_.checkForClosedCursor();
      return "";
   }

   public int getColumnType(int var1) throws SQLException {
      this.resultSet_.checkForClosedCursor();

      try {
         return IBTypes.getSQLType(this.resultSet_.resultTypes_[var1 - 1]);
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new ColumnIndexOutOfBoundsException(ErrorKey.columnIndexOutOfBounds__0__, var1);
      }
   }

   public String getColumnTypeName(int var1) throws SQLException {
      this.resultSet_.checkForClosedCursor();

      try {
         return IBTypes.getIBTypeName(this.resultSet_.resultTypes_[var1 - 1]);
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new ColumnIndexOutOfBoundsException(ErrorKey.columnIndexOutOfBounds__0__, var1);
      }
   }

   public boolean isReadOnly(int var1) throws SQLException {
      return !this.isWritable(var1);
   }

   public synchronized boolean isWritable(int var1) throws SQLException {
      this.resultSet_.checkForClosedCursor();
      return this.remote_GET_RESULT_COLUMN_META_DATA(var1);
   }

   public boolean isDefinitelyWritable(int var1) throws SQLException {
      this.resultSet_.checkForClosedCursor();
      return false;
   }

   private boolean remote_GET_RESULT_COLUMN_META_DATA(int var1) throws SQLException {
      try {
         PreparedStatement var2 = (PreparedStatement)this.resultSet_.statement_.connection_.prepareStatement(3, "select RF.RDB$FIELD_NAME from RDB$RELATIONS REL, RDB$RELATION_FIELDS RF, RDB$FIELDS FLD, RDB$USER_PRIVILEGES PRV  where RF.RDB$FIELD_NAME = ? and REL.RDB$RELATION_NAME = ? and REL.RDB$RELATION_NAME = RF.RDB$RELATION_NAME and RF.RDB$FIELD_SOURCE = FLD.RDB$FIELD_NAME and REL.RDB$VIEW_BLR IS NULL and FLD.RDB$COMPUTED_BLR IS NULL and PRV.RDB$RELATION_NAME = REL.RDB$RELATION_NAME and (PRV.RDB$FIELD_NAME = RF.RDB$FIELD_NAME or  PRV.RDB$FIELD_NAME IS NULL) and PRV.RDB$PRIVILEGE in ('A', 'I', 'U', 'D') and (PRV.RDB$USER = 'PUBLIC' or  PRV.RDB$USER = ?)");
         var2.setString(1, this.resultSet_.xsqlda_.sqlvar[var1 - 1].sqlname);
         var2.setString(2, this.resultSet_.xsqlda_.sqlvar[var1 - 1].relname);
         var2.setString(3, this.resultSet_.statement_.connection_.databaseMetaData_.userName_);
         ResultSet var3 = (ResultSet)var2.executeQuery();
         return var3.next();
      } catch (ArrayIndexOutOfBoundsException var4) {
         throw new ColumnIndexOutOfBoundsException(ErrorKey.columnIndexOutOfBounds__0__, var1);
      }
   }

   public synchronized String getColumnClassName(int var1) throws SQLException {
      this.resultSet_.checkForClosedCursor();

      try {
         return IBTypes.getClassName(this.resultSet_.resultTypes_[var1 - 1]);
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new ColumnIndexOutOfBoundsException(ErrorKey.columnIndexOutOfBounds__0__, var1);
      }
   }

   public int getArrayBaseType(int var1) throws SQLException {
      this.resultSet_.checkForClosedCursor();

      try {
         if (this.resultSet_.resultTypes_[var1 - 1] != 14) {
            throw new InvalidArgumentException(ErrorKey.invalidArgument__not_array_column__);
         } else if (this.resultSet_.arrayDescriptors_[var1 - 1] != null) {
            return IBTypes.getSQLType(this.resultSet_.arrayDescriptors_[var1 - 1].elementDataType_);
         } else {
            throw new DriverNotCapableException(ErrorKey.driverNotCapable__input_array_metadata__);
         }
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new ColumnIndexOutOfBoundsException(ErrorKey.columnIndexOutOfBounds__0__, var1);
      }
   }

   public int[][] getArrayDimensions(int var1) throws SQLException {
      this.resultSet_.checkForClosedCursor();

      try {
         if (this.resultSet_.resultTypes_[var1 - 1] != 14) {
            throw new InvalidArgumentException(ErrorKey.invalidArgument__not_array_column__);
         } else if (this.resultSet_.arrayDescriptors_[var1 - 1] != null) {
            return this.resultSet_.arrayDescriptors_[var1 - 1].getDimensions();
         } else {
            throw new DriverNotCapableException(ErrorKey.driverNotCapable__input_array_metadata__);
         }
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new ColumnIndexOutOfBoundsException(ErrorKey.columnIndexOutOfBounds__0__, var1);
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
}
