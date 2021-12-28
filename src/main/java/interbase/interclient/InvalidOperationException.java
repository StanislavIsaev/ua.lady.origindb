package interbase.interclient;

public class InvalidOperationException extends interbase.interclient.SQLException {
   private static final String className__ = "InvalidOperationException";

   InvalidOperationException(interbase.interclient.ErrorKey var1) {
      super("InvalidOperationException", var1);
   }

   InvalidOperationException(int var1) {
      super("InvalidOperationException", var1);
   }

   InvalidOperationException(String var1, interbase.interclient.ErrorKey var2) {
      super(var1, var2);
   }

   InvalidOperationException(String var1, int var2) {
      super(var1, var2);
   }

   InvalidOperationException(String var1, int var2, Object var3) {
      super(var1, var2, var3);
   }

   InvalidOperationException(String var1, interbase.interclient.ErrorKey var2, Object var3) {
      super(var1, var2, var3);
   }
}
