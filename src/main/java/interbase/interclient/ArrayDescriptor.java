package interbase.interclient;

final class ArrayDescriptor {
   String columnName_ = null;
   String tableName_ = null;
   int elementDataType_ = 0;
   int elementIBDataType_ = 0;
   int elementScale_ = 0;
   int elementPrecision_ = 0;
   int elementLength_ = 0;
   int flags_ = 0;
   int dimensions_ = 0;
   int elementIBSubType_ = 0;
   int[][] dimensionBounds_ = null;
   static final int MAX_DIMENSIONS = 16;
   static final int array_bound_upper = 1;
   static final int array_bound_lower = 0;

   ArrayDescriptor(Connection var1, String var2, String var3) throws java.sql.SQLException {
      this.lookupBounds(var1, var2, var3);
   }

   ArrayDescriptor() throws SQLException {
      this.dimensionBounds_ = new int[16][2];
   }

   private void lookupBounds(Connection var1, String var2, String var3) throws java.sql.SQLException {
      Statement var4 = (Statement)var1.createStatement();
      ResultSet var5 = (ResultSet)var4.executeQuery("select y.rdb$field_type, y.rdb$field_scale, y.rdb$field_length, y.rdb$field_name , y.rdb$dimensions, y.rdb$field_sub_type from rdb$relation_fields x, rdb$fields y  where x.rdb$field_source = y.rdb$field_name and x.rdb$relation_name = '" + var2.toUpperCase() + "' " + "and x.rdb$field_name = '" + var3.toUpperCase() + "'");
      if (var5.next()) {
         this.elementIBDataType_ = var5.getInt(1);
         this.elementIBSubType_ = var5.getInt(6);
         this.elementScale_ = Math.abs(var5.getInt(2));
         this.elementDataType_ = IBTypes.getIBArrayType(this.elementIBDataType_, this.elementScale_, this.elementIBSubType_);
         this.elementLength_ = var5.getInt(3);
         this.elementPrecision_ = IBTypes.getIBArrayPrecision(var5.getInt(1), this.elementLength_);
         this.dimensions_ = var5.getInt(5);
         this.columnName_ = var5.getString(4).trim();
      }

      var5.close();
      var5 = (ResultSet)var4.executeQuery("select x.rdb$lower_bound, x.rdb$upper_bound from rdb$field_dimensions x where rdb$field_name = '" + this.columnName_ + "' order by rdb$dimension");
      this.dimensionBounds_ = new int[this.dimensions_][2];

      for(int var6 = 0; var5.next(); ++var6) {
         this.dimensionBounds_[var6][0] = var5.getInt(1);
         this.dimensionBounds_[var6][1] = var5.getInt(2);
      }

      var5.close();
      var4.close();
      this.columnName_ = new String(var3);
      this.tableName_ = new String(var2);
   }

   int[][] getDimensions() throws java.sql.SQLException {
      int[][] var1 = new int[this.dimensions_][2];

      for(int var2 = 0; var2 < this.dimensions_; ++var2) {
         var1[var2][0] = this.dimensionBounds_[var2][0];
         var1[var2][1] = this.dimensionBounds_[var2][1];
      }

      return var1;
   }

   boolean checkSliceBounds(int[][] var1) throws java.sql.SQLException {
      if (var1.length != this.dimensions_) {
         throw new InvalidArgumentException(ErrorKey.invalidArgument__invalid_array_slice__);
      } else {
         boolean var2 = true;

         for(int var3 = 0; var3 < var1.length; ++var3) {
            if (var1[var3][0] < this.dimensionBounds_[var3][0] || var1[var3][0] > this.dimensionBounds_[var3][1] || var1[var3][1] > this.dimensionBounds_[var3][1] || var1[var3][1] < this.dimensionBounds_[var3][0] || var1[var3][0] > var1[var3][1]) {
               throw new InvalidArgumentException(ErrorKey.invalidArgument__invalid_array_slice__);
            }

            if (var1[var3][0] != this.dimensionBounds_[var3][0] || var1[var3][1] != this.dimensionBounds_[var3][1]) {
               var2 = false;
            }
         }

         return var2;
      }
   }
}
