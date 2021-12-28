package interbase.interclient;

class XSQLDA {
   static final int SQLDA_VERSION1 = 1;
   static final int SQLDA_VERSION2 = 2;
   int version = 2;
   int sqln;
   int sqld;
   XSQLVAR[] sqlvar;

   public XSQLDA() {
   }

   public XSQLDA(int var1) {
      this.sqln = var1;
      this.sqld = var1;
      this.sqlvar = new XSQLVAR[var1];
   }

   public XSQLDA(int var1, XSQLVAR[] var2) {
      this.sqln = var1;
      this.sqld = var1;
      this.sqlvar = var2;
   }

   protected void finalize() throws Throwable {
      this.sqlvar = null;
      super.finalize();
   }
}
