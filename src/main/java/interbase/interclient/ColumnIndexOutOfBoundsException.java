package interbase.interclient;

public final class ColumnIndexOutOfBoundsException extends InvalidArgumentException {
   private static final String className__ = "ColumnIndexOutOfBoundsException";

   ColumnIndexOutOfBoundsException(interbase.interclient.ErrorKey var1, int var2) {
      super("ColumnIndexOutOfBoundsException", var1, String.valueOf(var2));
   }
}
