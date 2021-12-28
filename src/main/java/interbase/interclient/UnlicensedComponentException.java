package interbase.interclient;

public class UnlicensedComponentException extends interbase.interclient.SQLException {
   private static final String className__ = "UnlicensedComponentException";

   UnlicensedComponentException(interbase.interclient.ErrorKey var1) {
      super("UnlicensedComponentException", var1);
   }

   UnlicensedComponentException(interbase.interclient.ErrorKey var1, Object var2) {
      super("UnlicensedComponentException", var1, var2);
   }

   UnlicensedComponentException(int var1) {
      super("UnlicensedComponentException", var1);
   }

   UnlicensedComponentException(int var1, int var2, int var3, String var4) {
      super("UnlicensedComponentException", var1, var2, var3, var4);
   }

   UnlicensedComponentException(String var1, interbase.interclient.ErrorKey var2, Object[] var3) {
      super(var1, var2, var3);
   }
}
