package interbase.interclient;

import java.net.Socket;

class IscDbHandle {
   private int rdb_id;
   Socket socket;
   interbase.interclient.XdrOutputStream out;
   interbase.interclient.XdrInputStream in;
   int op = -1;
   String charSetToUse_ = null;

   public IscDbHandle() {
   }

   public IscDbHandle(String var1) {
      this.charSetToUse_ = var1;
   }

   protected void finalize() throws Throwable {
      if (this.out != null) {
         this.out = null;
      }

      if (this.in != null) {
         this.in = null;
      }

      super.finalize();
   }

   void setRdb_id(int var1) {
      this.rdb_id = var1;
   }

   public int getRdb_id() {
      return this.rdb_id;
   }
}
