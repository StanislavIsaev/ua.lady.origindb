package interbase.interclient;

class SQLWarning extends java.sql.SQLWarning {
   SQLWarning(String var1, interbase.interclient.ErrorKey var2, Object[] var3) {
      super(Globals.getResource("89") + Globals.getResource(var2.getResourceKey(), var3) + Globals.getResource("0") + var1, var2.getSQLState(), var2.getErrorCode());
   }

   SQLWarning(String var1, interbase.interclient.ErrorKey var2, Object var3) {
      super(Globals.getResource("89") + Globals.getResource(var2.getResourceKey(), new Object[]{var3}) + Globals.getResource("0") + var1, var2.getSQLState(), var2.getErrorCode());
   }

   SQLWarning(String var1, interbase.interclient.ErrorKey var2) {
      super(Globals.getResource("89") + Globals.getResource(var2.getResourceKey()) + Globals.getResource("0") + var1, var2.getSQLState(), var2.getErrorCode());
   }

   SQLWarning(String var1, int var2, Object[] var3) {
      this(var1, interbase.interclient.ErrorKey.interserverErrorKeys__[var2], var3);
   }

   SQLWarning(String var1, int var2, Object var3) {
      this(var1, interbase.interclient.ErrorKey.interserverErrorKeys__[var2], var3);
   }

   SQLWarning(String var1, int var2) {
      this(var1, interbase.interclient.ErrorKey.interserverErrorKeys__[var2]);
   }
}
