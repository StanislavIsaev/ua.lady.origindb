package interbase.interclient;

class IBConnectionRequestInfo {
   private IBStruct struct;

   IBConnectionRequestInfo() {
   }

   IBConnectionRequestInfo(IBConnectionRequestInfo var1) {
      this.struct = Ibase.cloneStructlet(var1.struct);
   }

   IBStruct getDpb() {
      return this.struct;
   }

   void setProperty(int var1, String var2) {
      this.append(Ibase.newStructlet(var1, var2));
   }

   void setProperty(int var1) {
      this.append(Ibase.newStructlet(var1));
   }

   void setProperty(int var1, int var2) {
      this.append(Ibase.newStructlet(var1, var2));
   }

   void setProperty(int var1, byte[] var2) {
      this.append(Ibase.newStructlet(var1, var2));
   }

   byte[] getProperty(int var1) {
      return this.struct == null ? null : this.struct.find(var1);
   }

   String getStringProperty(int var1) {
      byte[] var2 = this.getProperty(var1);
      return var2 != null ? new String(this.struct.find(var1)) : null;
   }

   private void append(IBStruct var1) {
      if (this.struct == null) {
         this.struct = var1;
      } else {
         this.struct.append(var1);
      }

   }

   void setUser(String var1) {
      this.setProperty(28, (String)var1);
   }

   String getUser() {
      return this.getStringProperty(28);
   }

   void setPassword(String var1) {
      this.setProperty(29, (String)var1);
   }

   String getPassword() {
      return this.getStringProperty(29);
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof IBConnectionRequestInfo) {
         IBStruct var2 = ((IBConnectionRequestInfo)var1).struct;
         if (this.struct == null) {
            return var2 == null;
         } else {
            return this.struct.equals(var2);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.struct == null ? 0 : this.struct.hashCode();
   }
}
