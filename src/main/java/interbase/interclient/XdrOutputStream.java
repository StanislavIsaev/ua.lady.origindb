package interbase.interclient;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Set;

class XdrOutputStream extends DataOutputStream {
   private static final String defaultEncoding = "8859_1";
   private final byte[] pad = new byte[]{0, 0, 0, 0};

   public XdrOutputStream(OutputStream var1) {
      super(var1);
   }

   public final void writeOpaque(byte[] var1, int var2) throws IOException {
      if (var1 != null && var2 > 0) {
         this.write(var1, 0, var2);
         this.write(this.pad, 0, 4 - var2 & 3);
      }

   }

   public final void writeBuffer(byte[] var1, int var2) throws IOException {
      this.writeInt(var2);
      if (var1 != null && var2 > 0) {
         this.write(var1, 0, var2);
         this.write(this.pad, 0, 4 - var2 & 3);
      }

   }

   public final void writeBlobBuffer(byte[] var1) throws IOException {
      int var2 = var1.length;
      if (var2 > 32767) {
         throw new IOException("");
      } else {
         this.writeInt(var2 + 2);
         this.writeInt(var2 + 2);
         this.write(var2 >> 0 & 255);
         this.write(var2 >> 8 & 255);
         this.write(var1, 0, var2);
         this.write(this.pad, 0, 4 - var2 + 2 & 3);
      }
   }

   public final void writeString(String var1, String var2) throws IOException {
      if (var2 == null) {
         this.writeString(var1, "8859_1");
      } else {
         byte[] var3 = var1.getBytes(var2);
         int var4 = var3.length;
         this.writeInt(var4);
         if (var4 > 0) {
            this.write(var3, 0, var4);
            this.write(this.pad, 0, 4 - var4 & 3);
         }
      }

   }

   public final void writeString(String var1) throws IOException {
      byte[] var2 = var1.getBytes();
      int var3 = var2.length;
      this.writeInt(var3);
      if (var3 > 0) {
         this.write(var2, 0, var3);
         this.write(this.pad, 0, 4 - var3 & 3);
      }

   }

   final void writeSet(int var1, Set var2) throws IOException {
      if (var2 == null) {
         this.writeInt(1);
         this.write(var1);
      } else {
         this.writeInt(var2.size() + 1);
         this.write(var1);
         Iterator var3 = var2.iterator();

         while(var3.hasNext()) {
            int var4 = ((Integer)var3.next()).intValue();
            this.write(var4);
         }

         this.write(this.pad, 0, 4 - (var2.size() + 1) & 3);
      }

   }

   final void writeTyped(int var1, XdrEnabled var2) throws IOException {
      int var3;
      if (var2 == null) {
         this.writeInt(1);
         this.write(var1);
         var3 = 1;
      } else {
         var3 = var2.getSize() + 1;
         this.writeInt(var3);
         this.write(var1);
         var2.write(this);
      }

      this.write(this.pad, 0, 4 - var3 & 3);
   }
}
