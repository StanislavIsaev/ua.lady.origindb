package interbase.interclient;

public final class BugCheckException extends SQLException {
   private static final int lastBugCodeUsed__ = 140;
   private static final String className__ = "BugCheckException";

   BugCheckException(ErrorKey var1, int var2) {
      super("BugCheckException", var1, (Object)String.valueOf(var2));
   }

   BugCheckException(int var1, int var2) {
      super("BugCheckException", var1, (Object)String.valueOf(var2));
   }

   BugCheckException(int var1, int var2, int var3, String var4) {
      super("BugCheckException", var1, var2, var3, var4);
   }
}
