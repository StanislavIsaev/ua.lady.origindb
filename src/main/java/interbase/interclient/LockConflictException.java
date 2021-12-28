package interbase.interclient;

class LockConflictException extends interbase.interclient.SQLException {
   private static final String className__ = "LockConflictException";

   LockConflictException(int var1, int var2, int var3, String var4) {
      super("LockConflictException", var1, var2, var3, var4);
   }

   LockConflictException(String var1, int var2, int var3, int var4, String var5) {
      super(var1, var2, var3, var4, var5);
   }
}
