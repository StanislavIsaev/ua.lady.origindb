package interbase.interclient;

public final class OutOfMemoryException extends interbase.interclient.SQLException {
   private static final String className__ = "OutOfMemoryException";

   OutOfMemoryException(int var1) {
      super("OutOfMemoryException", var1);
   }

   OutOfMemoryException(int var1, int var2, int var3, String var4) {
      super("OutOfMemoryException", var1, var2, var3, var4);
   }
}
