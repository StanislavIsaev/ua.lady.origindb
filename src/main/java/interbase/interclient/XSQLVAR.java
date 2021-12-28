package interbase.interclient;

class XSQLVAR {
   public int sqltype;
   public int sqlscale;
   public int sqlsubtype;
   public int sqllen;
   public Object sqldata;
   public int sqlind;
   public String sqlname;
   public String relname;
   public String ownname;
   public String aliasname;
   public int sqlStmtType;
   public int sqlPrecision = 0;

   public XSQLVAR() {
   }

   public XSQLVAR(Object var1) {
      this.sqldata = var1;
   }

   protected void finalize() throws Throwable {
      this.sqldata = null;
      super.finalize();
   }
}
