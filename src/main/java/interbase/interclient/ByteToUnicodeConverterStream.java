package interbase.interclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

final class ByteToUnicodeConverterStream extends InputStream {
   InputStream in_;
   Reader reader_;
   boolean midCharacter_ = false;
   int loByte_;

   ByteToUnicodeConverterStream(InputStream var1, InputStreamReader var2) {
      this.in_ = var1;
      this.reader_ = var2;
   }

   ByteToUnicodeConverterStream(String var1) {
      this.reader_ = new StringReader(var1);
   }

   public synchronized void close() throws IOException {
      this.reader_.close();
   }

   public synchronized int read() throws IOException {
      if (!this.midCharacter_) {
         int var1 = this.reader_.read();
         int var2 = var1 >> 8 & 255;
         this.loByte_ = var1 >> 0 & 255;
         this.midCharacter_ = true;
         return var2;
      } else {
         this.midCharacter_ = false;
         return this.loByte_;
      }
   }

   public synchronized int read(byte[] var1) throws IOException {
      return this.read(var1, 0, var1.length);
   }

   public synchronized int read(byte[] var1, int var2, int var3) throws IOException {
      if (var3 == 0) {
         return 0;
      } else {
         int var4 = Math.max(1, var3 / 2);
         char[] var5 = Globals.cache__.takeCharBuffer(var4);

         try {
            int var6 = this.reader_.read(var5, 0, var4);
            int var7 = var2;

            for(int var8 = 0; var8 < var6; ++var8) {
               var1[var7++] = (byte)(var5[var8] >> 8 & 255);
               var1[var7++] = (byte)(var5[var8] >> 0 & 255);
            }

            int var9 = 2 * var6;
            return var9;
         } finally {
            Globals.cache__.returnCharBuffer(var5);
         }
      }
   }

   public long skip(long var1) throws IOException {
      long var3 = this.reader_.skip(var1 / 2L);
      return 2L * var3;
   }

   public int available() throws IOException {
      return this.reader_ instanceof StringReader ? 0 : this.in_.available();
   }

   public void mark(int var1) {
   }

   public void reset() throws IOException {
      throw new BlobIOException("69");
   }

   public boolean markSupported() {
      return false;
   }
}
