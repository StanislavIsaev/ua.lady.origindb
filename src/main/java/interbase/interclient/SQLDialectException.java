package interbase.interclient;

public final class SQLDialectException extends InvalidArgumentException {
   private static final String className__ = "SQLDialectException";

   SQLDialectException(interbase.interclient.ErrorKey var1, String var2) {
      super("SQLDialectException", var1, var2);
   }

   SQLDialectException(int var1, int var2) {
      super("SQLDialectException", var1, new Integer(var2));
   }
}
