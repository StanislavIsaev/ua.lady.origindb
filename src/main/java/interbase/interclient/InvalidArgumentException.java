package interbase.interclient;

public class InvalidArgumentException extends InvalidOperationException {
   private static final String className__ = "InvalidArgumentException";

   InvalidArgumentException(interbase.interclient.ErrorKey var1) {
      super("InvalidArgumentException", var1);
   }

   InvalidArgumentException(interbase.interclient.ErrorKey var1, Object var2) {
      super("InvalidArgumentException", var1, var2);
   }

   InvalidArgumentException(int var1) {
      super("InvalidArgumentException", var1);
   }

   InvalidArgumentException(String var1, int var2, Object var3) {
      super(var1, var2, var3);
   }

   InvalidArgumentException(String var1, interbase.interclient.ErrorKey var2, Object var3) {
      super(var1, var2, var3);
   }
}
