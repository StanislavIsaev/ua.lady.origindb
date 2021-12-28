package interbase.interclient;

public final class UnauthorizedUserException extends interbase.interclient.SQLException {
   private static final String className__ = "UnauthorizedUserException";

   UnauthorizedUserException(int var1, int var2, int var3, String var4) {
      super("UnauthorizedUserException", var1, var2, var3, var4);
   }
}
