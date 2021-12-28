package interbase.interclient;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Timestamp;
import java.util.Map;

public final class Array implements java.sql.Array
{
   Object data_;
   long id_;
   interbase.interclient.ArrayDescriptor descriptor_;
   interbase.interclient.Connection connection_;
   private static final BigDecimal bdMaxShortValue;
   private static final BigDecimal bdMinShortValue;
   private static final BigDecimal bdMaxIntValue;
   private static final BigDecimal bdMinIntValue;
   private static final BigDecimal bdMaxFloatValue;
   private static final BigDecimal bdMinFloatValue;
   private static final BigDecimal bdMaxDoubleValue;
   private static final BigDecimal bdMinDoubleValue;

   Array(final Object data_) throws SQLException {
      this.data_ = null;
      this.id_ = 0L;
      this.descriptor_ = null;
      this.connection_ = null;
      if (!data_.getClass().isArray()) {
         throw new interbase.interclient.BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 131);
      }
      this.data_ = data_;
   }

   Array(final Object data_, final interbase.interclient.ArrayDescriptor descriptor) throws SQLException {
      this.data_ = null;
      this.id_ = 0L;
      this.descriptor_ = null;
      this.connection_ = null;
      if (!data_.getClass().isArray()) {
         throw new interbase.interclient.BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 132);
      }
      this.setDescriptor(descriptor);
      this.data_ = data_;
   }

   Array(final long id_, final interbase.interclient.ArrayDescriptor descriptor, final Connection connection_) throws SQLException {
      this.data_ = null;
      this.id_ = 0L;
      this.descriptor_ = null;
      this.connection_ = null;
      this.setDescriptor(descriptor);
      this.id_ = id_;
      this.connection_ = connection_;
   }

   void setDescriptor(final interbase.interclient.ArrayDescriptor descriptor_) throws SQLException {
      if (descriptor_ == null) {
         throw new interbase.interclient.BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 133);
      }
      if (this.data_ != null) {
         this.checkDimensions(this.data_, descriptor_, 0);
      }
      this.descriptor_ = descriptor_;
   }

   private void checkDimensions(final Object o, final interbase.interclient.ArrayDescriptor arrayDescriptor, int n) throws SQLException {
      if (o == null) {
         throw new interbase.interclient.InvalidArgumentException(interbase.interclient.ErrorKey.invalidArgument__invalid_array_dimensions__);
      }
      final int length = java.lang.reflect.Array.getLength(o);
      if (arrayDescriptor.dimensionBounds_[n][1] - arrayDescriptor.dimensionBounds_[n][0] + 1 != length) {
         throw new interbase.interclient.InvalidArgumentException(interbase.interclient.ErrorKey.invalidArgument__invalid_array_dimensions__);
      }
      if (o.getClass().getComponentType().isArray()) {
         if (++n >= arrayDescriptor.dimensions_) {
            throw new interbase.interclient.InvalidArgumentException(interbase.interclient.ErrorKey.invalidArgument__invalid_array_dimensions__);
         }
         for (int i = 0; i < length; ++i) {
            this.checkDimensions(java.lang.reflect.Array.get(o, i), arrayDescriptor, n);
         }
      }
      else if (n != arrayDescriptor.dimensions_ - 1) {
         throw new InvalidArgumentException(interbase.interclient.ErrorKey.invalidArgument__invalid_array_dimensions__);
      }
   }

   void putArraySlice() throws SQLException {
      long n = this.descriptor_.dimensionBounds_[0][1] - this.descriptor_.dimensionBounds_[0][0] + 1;
      for (int i = 1; i < this.descriptor_.dimensions_; ++i) {
         n *= this.descriptor_.dimensionBounds_[i][1] - this.descriptor_.dimensionBounds_[i][0] + 1;
      }
      final long n2 = n * this.descriptor_.elementLength_;
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   private Object remote_GET_ARRAY_SLICE(final int[][] array) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   private Object createJavaArray(final int[][] array) throws SQLException {
      final int[] dimensions = new int[this.descriptor_.dimensions_];
      for (int i = 0; i < this.descriptor_.dimensions_; ++i) {
         dimensions[i] = array[i][1] - array[i][0] + 1;
      }
      Serializable componentType = null;
      switch (this.descriptor_.elementDataType_) {
         case 1: {
            componentType = Short.TYPE;
            break;
         }
         case 2: {
            componentType = Integer.TYPE;
            break;
         }
         case 3: {
            componentType = Float.TYPE;
            break;
         }
         case 4: {
            componentType = Double.TYPE;
            break;
         }
         case 5:
         case 6:
         case 7: {
            componentType = BigDecimal.class;
            break;
         }
         case 8:
         case 9: {
            componentType = String.class;
            break;
         }
         case 11: {
            componentType = Timestamp.class;
            break;
         }
         default: {
            throw new BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 135);
         }
      }
      return java.lang.reflect.Array.newInstance((Class<?>)componentType, dimensions);
   }

   public synchronized String getBaseTypeName() throws SQLException {
      return IBTypes.getIBTypeName(this.descriptor_.elementDataType_);
   }

   public synchronized int getBaseType() throws SQLException {
      return IBTypes.getSQLType(this.descriptor_.elementDataType_);
   }

   public synchronized Object getArray() throws SQLException {
      if (this.data_ != null) {
         return this.data_;
      }
      this.data_ = this.remote_GET_ARRAY_SLICE(this.descriptor_.dimensionBounds_);
      this.connection_.transactionStartedOnServer_ = true;
      return this.data_;
   }

   public synchronized Object getArray(final Map map) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Object getArray(final long n, final int n2) throws SQLException {
      final int[][] dimensions = this.descriptor_.getDimensions();
      dimensions[0][0] = (int)n;
      dimensions[0][1] = (int)(n + n2 - 1L);
      final boolean checkSliceBounds = this.descriptor_.checkSliceBounds(dimensions);
      final Object remote_GET_ARRAY_SLICE = this.remote_GET_ARRAY_SLICE(dimensions);
      if (checkSliceBounds) {
         this.data_ = remote_GET_ARRAY_SLICE;
      }
      return remote_GET_ARRAY_SLICE;
   }

   public synchronized Object getArray(final long n, final int n2, final Map map) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized ResultSet getResultSet() throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized ResultSet getResultSet(final Map map) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized ResultSet getResultSet(final long n, final int n2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   @Override
   public synchronized void free() throws SQLException {
      throw new SQLFeatureNotSupportedException("decompiled");
   }

   public synchronized ResultSet getResultSet(final long n, final int n2, final Map map) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Object getArray(final int[][] array) throws SQLException {
      final boolean checkSliceBounds = this.descriptor_.checkSliceBounds(array);
      final Object remote_GET_ARRAY_SLICE = this.remote_GET_ARRAY_SLICE(array);
      if (checkSliceBounds) {
         this.data_ = remote_GET_ARRAY_SLICE;
      }
      return remote_GET_ARRAY_SLICE;
   }

   public synchronized ResultSet getResultSet(final int[][] array) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   static {
      bdMaxShortValue = new BigDecimal(32767.0);
      bdMinShortValue = new BigDecimal(-32768.0);
      bdMaxIntValue = new BigDecimal(2.147483647E9);
      bdMinIntValue = new BigDecimal(-2.147483648E9);
      bdMaxFloatValue = new BigDecimal(3.4028234663852886E38);
      bdMinFloatValue = new BigDecimal(1.401298464324817E-45);
      bdMaxDoubleValue = new BigDecimal(Double.MAX_VALUE);
      bdMinDoubleValue = new BigDecimal(Double.MIN_VALUE);
   }
}