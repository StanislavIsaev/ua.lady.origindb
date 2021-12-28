package interbase.interclient;

class SQLException extends java.sql.SQLException {
   SQLException(String var1, interbase.interclient.ErrorKey var2, Object[] var3) {
      super(Globals.getResource("89") + Globals.getResource(var2.getResourceKey(), var3) + Globals.getResource("0") + var1, var2.getSQLState(), var2.getErrorCode());
   }

   SQLException(String var1, interbase.interclient.ErrorKey var2, Object var3) {
      super(Globals.getResource("89") + Globals.getResource(var2.getResourceKey(), new Object[]{var3}) + Globals.getResource("0") + var1, var2.getSQLState(), var2.getErrorCode());
   }

   SQLException(String var1, interbase.interclient.ErrorKey var2) {
      super(Globals.getResource("89") + Globals.getResource(var2.getResourceKey()) + Globals.getResource("0") + var1, var2.getSQLState(), var2.getErrorCode());
   }

   SQLException(String var1, int var2, Object[] var3) {
      this(var1, interbase.interclient.ErrorKey.interserverErrorKeys__[var2], var3);
   }

   SQLException(String var1, int var2, Object var3) {
      this(var1, interbase.interclient.ErrorKey.interserverErrorKeys__[var2], var3);
   }

   SQLException(String var1, int var2) {
      this(var1, interbase.interclient.ErrorKey.interserverErrorKeys__[var2]);
   }

   SQLException(String var1, int var2, int var3, int var4, String var5) {
      super(Globals.getResource("106") + Globals.getResource(interbase.interclient.ErrorKey.interserverErrorKeys__[var2].getResourceKey(), new Object[]{var5}) + Globals.getResource("0") + var1, interbase.interclient.ErrorKey.interserverErrorKeys__[var2].getSQLState(var3, var4), var3);
   }

   SQLException(int var1, int var2, int var3, String var4) {
      super(Globals.getResource("106") + Globals.getResource(interbase.interclient.ErrorKey.interserverErrorKeys__[var1].getResourceKey(), new Object[]{var4}), interbase.interclient.ErrorKey.interserverErrorKeys__[var1].getSQLState(var2, var3), var2);
   }
}
