package interbase.interclient;

public class DriverNotCapableException extends interbase.interclient.SQLException {
   private static final String className__ = "DriverNotCapableException";

   DriverNotCapableException(interbase.interclient.ErrorKey var1) {
      super("DriverNotCapableException", var1);
   }

   DriverNotCapableException(int var1) {
      super("DriverNotCapableException", var1);
   }

   DriverNotCapableException(String var1, interbase.interclient.ErrorKey var2, Object var3) {
      super(var1, var2, var3);
   }

   DriverNotCapableException(String var1, int var2, Object var3) {
      super(var1, var2, var3);
   }
}
