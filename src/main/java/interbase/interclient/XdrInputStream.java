package interbase.interclient;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

class XdrInputStream extends DataInputStream {
   private byte[] padFourBytes = new byte[4];

   XdrInputStream(InputStream var1) {
      super(var1);
   }

   final byte[] readOpaque(int var1) throws IOException {
      byte[] var2 = new byte[var1];
      this.readFully(var2);
      this.readFully(this.padFourBytes, 0, 4 - var1 & 3);
      return var2;
   }

   final byte[] readBuffer() throws IOException {
      int var1 = this.readInt();
      byte[] var2 = new byte[var1];
      this.readFully(var2);
      this.readFully(this.padFourBytes, 0, 4 - var1 & 3);
      return var2;
   }

   final String readString() throws IOException {
      int var1 = this.readInt();
      byte[] var2 = new byte[var1];
      this.readFully(var2);
      this.readFully(this.padFourBytes, 0, 4 - var1 & 3);
      return new String(var2);
   }
}
