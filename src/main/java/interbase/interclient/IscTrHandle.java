package interbase.interclient;

class IscTrHandle {
   public static final int NOTRANSACTION = 0;
   public static final int TRANSACTIONSTARTING = 1;
   public static final int TRANSACTIONSTARTED = 2;
   public static final int TRANSACTIONPREPARING = 3;
   public static final int TRANSACTIONPREPARED = 4;
   public static final int TRANSACTIONCOMMITTING = 5;
   public static final int TRANSACTIONROLLINGBACK = 6;
   int rtr_id;
   IscDbHandle rtr_rdb;
   private int state = 0;

   protected void finalize() throws Throwable {
      this.rtr_rdb = null;
      super.finalize();
   }

   public IscDbHandle getDbHandle() {
      return this.rtr_rdb;
   }

   void setState(int var1) {
      this.state = var1;
   }

   public int getState() {
      return this.state;
   }
}
