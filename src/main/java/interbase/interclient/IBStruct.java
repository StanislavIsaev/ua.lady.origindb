package interbase.interclient;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

class IBStruct implements XdrEnabled, Serializable {
   int structType;
   byte[] structContains;
   IBStruct nextStruct;

   IBStruct(int var1, byte[] var2) {
      this.structType = var1;
      this.structContains = var2;
   }

   IBStruct(IBStruct var1) {
      this.structType = var1.structType;
      this.structContains = var1.structContains;
      if (var1.nextStruct != null) {
         this.nextStruct = new IBStruct(var1.nextStruct);
      }

   }

   void append(IBStruct var1) {
      if (this.structType == var1.structType) {
         this.structContains = var1.structContains;
      } else if (this.nextStruct == null) {
         this.nextStruct = var1;
      } else {
         this.nextStruct.append(var1);
      }

   }

   byte[] find(int var1) {
      if (var1 == this.structType) {
         return this.structContains;
      } else {
         return this.nextStruct == null ? null : this.nextStruct.find(var1);
      }
   }

   public int getSize() {
      return this.nextStruct == null ? this.structContains.length + 2 : this.structContains.length + 2 + this.nextStruct.getSize();
   }

   public void write(interbase.interclient.XdrOutputStream var1) throws IOException {
      var1.write(this.structType);
      var1.write(this.structContains.length);
      var1.write(this.structContains);
      if (this.nextStruct != null) {
         this.nextStruct.write(var1);
      }

   }

   public void read(interbase.interclient.XdrInputStream var1, int var2) {
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof IBStruct) {
         IBStruct var2 = (IBStruct)var1;
         if (this.structType == var2.structType && Arrays.equals(this.structContains, var2.structContains)) {
            if (this.nextStruct != null) {
               return this.nextStruct.equals(var2.nextStruct);
            } else {
               return var2.nextStruct == null;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int var1 = this.structType;

      for(int var2 = 0; var2 < this.structContains.length; ++var2) {
         var1 ^= this.structContains[var2] << 8 * (var2 % 4);
      }

      if (this.nextStruct != null) {
         var1 ^= this.nextStruct.hashCode();
      }

      return var1;
   }
}
