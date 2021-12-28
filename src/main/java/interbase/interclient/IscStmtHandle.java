package interbase.interclient;

import java.util.ArrayList;
import java.util.List;

class IscStmtHandle {
   public List rows = new ArrayList();
   IscDbHandle iscDbHandle;
   interbase.interclient.XSQLDA in_sqlda = null;
   interbase.interclient.XSQLDA out_sqlda = null;
   int rsr_id;
   boolean allRowsFetched = false;
   boolean singletonResult = false;
   private int stmtType;
   private int majorDatabaseVersion = 0;
   private int minorDatabaseVersion = 0;

   public IscStmtHandle(int var1, int var2) {
      this.majorDatabaseVersion = var1;
      this.minorDatabaseVersion = var2;
   }

   public IscStmtHandle(ArrayList var1, int var2, int var3) {
      this.rows = var1;
      this.allRowsFetched = true;
      this.majorDatabaseVersion = var2;
      this.minorDatabaseVersion = var3;
   }

   public interbase.interclient.XSQLDA getInSqlda() {
      return this.in_sqlda;
   }

   public interbase.interclient.XSQLDA getOutSqlda() {
      return this.out_sqlda;
   }

   public void clearRows() {
      this.rows.clear();
      this.allRowsFetched = false;
   }

   public void clearSqlda() {
      this.clearOutSqlda();
      this.clearInSqlda();
   }

   public void clearOutSqlda() {
      this.out_sqlda = null;
   }

   public void clearInSqlda() {
      this.in_sqlda = null;
   }

   public void setOutSqlda(interbase.interclient.XSQLDA var1) {
      this.out_sqlda = var1;
   }

   public void setInSqlda(interbase.interclient.XSQLDA var1) {
      this.in_sqlda = var1;
   }

   public boolean isAllRowsFetched() {
      return this.allRowsFetched;
   }

   public boolean isSingletonResult() {
      return this.singletonResult;
   }

   public void setSingletonResult(boolean var1) {
      this.singletonResult = var1;
   }

   public int getStmtType() {
      return this.stmtType;
   }

   public void setStmtType(int var1) {
      this.stmtType = var1;
   }

   int getMajorDatabaseVersion() {
      return this.majorDatabaseVersion;
   }

   int getMinorDatabaseVersion() {
      return this.minorDatabaseVersion;
   }

   protected void finalize() throws Throwable {
      this.iscDbHandle = null;
      this.clearSqlda();
      this.clearRows();
      super.finalize();
   }
}
