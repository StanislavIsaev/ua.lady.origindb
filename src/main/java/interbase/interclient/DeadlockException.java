package interbase.interclient;

public final class DeadlockException extends SQLException {
   private static final String className__ = "DeadlockException";

   DeadlockException(int var1, int var2, int var3, String var4) {
      super("DeadlockException", var1, var2, var3, var4);
   }
}
