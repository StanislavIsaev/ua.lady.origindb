package interbase.interclient;

public final class ParameterIndexOutOfBoundsException extends InvalidArgumentException {
   private static final String className__ = "ParameterIndexOutOfBoundsException";

   ParameterIndexOutOfBoundsException(interbase.interclient.ErrorKey var1, int var2) {
      super("ParameterIndexOutOfBoundsException", var1, String.valueOf(var2));
   }
}
