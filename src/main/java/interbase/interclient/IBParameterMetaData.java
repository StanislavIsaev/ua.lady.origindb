package interbase.interclient;

public final class IBParameterMetaData implements java.sql.ParameterMetaData {
   interbase.interclient.PreparedStatement preparedStatement_;

   IBParameterMetaData(interbase.interclient.PreparedStatement var1) {
      this.preparedStatement_ = var1;
   }

   public int getParameterCount() throws java.sql.SQLException {
      return this.preparedStatement_.inputCols_;
   }

   public int getParameterType(int var1) throws java.sql.SQLException {
      try {
         return IBTypes.getSQLType(this.preparedStatement_.inputTypes_[var1 - 1]);
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new interbase.interclient.ParameterIndexOutOfBoundsException(interbase.interclient.ErrorKey.parameterIndexOutOfBounds__0__, var1);
      }
   }

   public String getParameterTypeName(int var1) throws java.sql.SQLException {
      try {
         return IBTypes.getIBTypeName(this.preparedStatement_.inputTypes_[var1 - 1]);
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new interbase.interclient.ParameterIndexOutOfBoundsException(interbase.interclient.ErrorKey.parameterIndexOutOfBounds__0__, var1);
      }
   }

   public int getPrecision(int var1) throws java.sql.SQLException {
      try {
         switch(this.preparedStatement_.inputTypes_[var1 - 1]) {
         case 8:
         case 9:
         case 20:
            return this.preparedStatement_.inputCharLengths_[var1 - 1];
         case 14:
            if (this.preparedStatement_.arrayDescriptors_[var1 - 1] != null) {
               return this.preparedStatement_.arrayDescriptors_[var1 - 1].elementPrecision_;
            }

            throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__input_array_metadata__);
         default:
            return this.preparedStatement_.inputPrecisions_[var1 - 1];
         }
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new interbase.interclient.ParameterIndexOutOfBoundsException(interbase.interclient.ErrorKey.parameterIndexOutOfBounds__0__, var1);
      }
   }

   public int getScale(int var1) throws java.sql.SQLException {
      try {
         switch(this.preparedStatement_.inputTypes_[var1 - 1]) {
         case 8:
         case 9:
         case 20:
            return 0;
         case 14:
            if (this.preparedStatement_.arrayDescriptors_[var1 - 1] != null) {
               return this.preparedStatement_.arrayDescriptors_[var1 - 1].elementScale_;
            }

            throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__input_array_metadata__);
         default:
            return this.preparedStatement_.inputScales_[var1 - 1];
         }
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new interbase.interclient.ParameterIndexOutOfBoundsException(interbase.interclient.ErrorKey.parameterIndexOutOfBounds__0__, var1);
      }
   }

   public int isNullable(int var1) throws java.sql.SQLException {
      try {
         return this.preparedStatement_.inputNullables_[var1 - 1] ? 1 : 0;
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new interbase.interclient.ParameterIndexOutOfBoundsException(interbase.interclient.ErrorKey.parameterIndexOutOfBounds__0__, var1);
      }
   }

   public int getArrayBaseType(int var1) throws java.sql.SQLException {
      try {
         if (this.preparedStatement_.inputTypes_[var1 - 1] != 14) {
            throw new interbase.interclient.InvalidArgumentException(interbase.interclient.ErrorKey.invalidArgument__not_array_parameter__);
         } else if (this.preparedStatement_.arrayDescriptors_[var1 - 1] != null) {
            return IBTypes.getSQLType(this.preparedStatement_.arrayDescriptors_[var1 - 1].elementDataType_);
         } else {
            throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__input_array_metadata__);
         }
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new interbase.interclient.ParameterIndexOutOfBoundsException(interbase.interclient.ErrorKey.parameterIndexOutOfBounds__0__, var1);
      }
   }

   public int[][] getArrayDimensions(int var1) throws java.sql.SQLException {
      try {
         if (this.preparedStatement_.inputTypes_[var1 - 1] != 14) {
            throw new InvalidArgumentException(interbase.interclient.ErrorKey.invalidArgument__not_array_parameter__);
         } else if (this.preparedStatement_.arrayDescriptors_[var1 - 1] != null) {
            return this.preparedStatement_.arrayDescriptors_[var1 - 1].getDimensions();
         } else {
            throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__input_array_metadata__);
         }
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new interbase.interclient.ParameterIndexOutOfBoundsException(interbase.interclient.ErrorKey.parameterIndexOutOfBounds__0__, var1);
      }
   }

   public int getParameterMode(int var1) throws interbase.interclient.SQLException {
      return 0;
   }

   public boolean isSigned(int var1) throws interbase.interclient.SQLException {
      try {
         return IBTypes.isNumeric(this.preparedStatement_.inputTypes_[var1]);
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new interbase.interclient.ParameterIndexOutOfBoundsException(interbase.interclient.ErrorKey.parameterIndexOutOfBounds__0__, var1);
      }
   }

   public String getParameterClassName(int var1) throws java.sql.SQLException {
      try {
         return IBTypes.getClassName(this.preparedStatement_.inputTypes_[var1]);
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new ParameterIndexOutOfBoundsException(interbase.interclient.ErrorKey.parameterIndexOutOfBounds__0__, var1);
      }
   }

   static java.sql.ParameterMetaData setParameterMetaData(PreparedStatement var0) {
      return new IBParameterMetaData(var0);
   }

   @Override
   public <T> T unwrap(Class<T> iface) throws java.sql.SQLException {
      return null;
   }

   @Override
   public boolean isWrapperFor(Class<?> iface) throws java.sql.SQLException {
      return false;
   }
}
