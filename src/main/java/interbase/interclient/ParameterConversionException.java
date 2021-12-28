package interbase.interclient;

public final class ParameterConversionException extends DataConversionException {
   private static final String className__ = "ParameterConversionException";

   ParameterConversionException(ErrorKey var1) {
      super("ParameterConversionException", var1);
   }

   ParameterConversionException(ErrorKey var1, Object var2) {
      super("ParameterConversionException", var1, var2);
   }

   ParameterConversionException(ErrorKey var1, Object[] var2) {
      super("ParameterConversionException", var1, var2);
   }
}
