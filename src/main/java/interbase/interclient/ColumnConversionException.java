package interbase.interclient;

public final class ColumnConversionException extends DataConversionException {
   private static final String className__ = "ColumnConversionException";

   ColumnConversionException(interbase.interclient.ErrorKey var1) {
      super("ColumnConversionException", var1);
   }

   ColumnConversionException(interbase.interclient.ErrorKey var1, Object var2) {
      super("ColumnConversionException", var1, var2);
   }
}
