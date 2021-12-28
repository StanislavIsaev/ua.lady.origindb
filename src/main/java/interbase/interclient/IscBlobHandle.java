package interbase.interclient;

class IscBlobHandle {
   IscDbHandle db;
   IscTrHandle tr;
   int rbl_id;
   long blob_id;
   IscBlobHandle next;
   int rbl_flags;

   public long getBlobId() {
      return this.blob_id;
   }

   public void setBlobId(long var1) {
      this.blob_id = var1;
   }

   public boolean isEof() {
      return (this.rbl_flags & 4) != 0;
   }
}
