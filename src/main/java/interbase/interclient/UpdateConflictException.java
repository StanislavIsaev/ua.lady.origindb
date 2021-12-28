package interbase.interclient;

final class UpdateConflictException extends LockConflictException {
   private static final String className__ = "UpdateConflictException";

   UpdateConflictException(int var1, int var2, int var3, String var4) {
      super("UpdateConflictException", var1, var2, var3, var4);
   }
}
