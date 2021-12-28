package interbase.interclient;

public final class UnsupportedCharacterSetException extends DriverNotCapableException {
   private static final String className__ = "UnsupportedCharacterSetException";

   UnsupportedCharacterSetException(interbase.interclient.ErrorKey var1, String var2) {
      super("UnsupportedCharacterSetException", var1, var2);
   }

   UnsupportedCharacterSetException(int var1, int var2) {
      super("UnsupportedCharacterSetException", var1, String.valueOf(var2));
   }
}
