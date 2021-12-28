package interbase.interclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.CharacterCodingException;

class BlobOutput {
   InputStreamReader reader_ = null;
   private long blobId_;
   private Statement statement_;
   int actualSegmentSize_;
   boolean lastSegment_ = false;
   int blobRef_;
   JavaNIOByteToCharConverters btc_;
   String encoding_;
   interbase.interclient.IscBlobHandle blobHandle_;
   IBBlobInputStream ibBlobInputStream_ = null;
   int bufferlength = 16384;
   private interbase.interclient.IBException sqlWarnings_;

   BlobOutput(Statement var1, long var2, JavaNIOByteToCharConverters var4) throws java.sql.SQLException {
      this.statement_ = var1;
      this.blobId_ = var2;
      this.btc_ = var4;
      this.encoding_ = this.btc_.getCharacterEncoding();
      this.remote_OPEN_BLOB();

      try {
         this.reader_ = new InputStreamReader(this.ibBlobInputStream_, this.encoding_);
      } catch (UnsupportedEncodingException var6) {
         throw new interbase.interclient.BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 129);
      }
   }

   protected void finalize() throws Throwable {
      this.close();
      super.finalize();
   }

   void close() throws java.sql.SQLException {
      try {
         this.ibBlobInputStream_.close();
      } catch (IOException var2) {
         throw new interbase.interclient.CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_blob_close_01__, "server", Utils.getMessage(var2));
      }
   }

   void remote_OPEN_BLOB() throws java.sql.SQLException {
      try {
         this.blobHandle_ = this.statement_.connection_.ibase_.getNewIscBlobHandle();
         this.blobHandle_.setBlobId(this.blobId_);
         this.statement_.connection_.ibase_.iscOpenBlob2(this.statement_.connection_.db_, this.statement_.connection_.tra_, this.blobHandle_, (IBStruct)null, this.sqlWarnings_);
         this.ibBlobInputStream_ = new IBBlobInputStream();
      } catch (interbase.interclient.IBException var6) {
         throw new interbase.interclient.CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_blob_read_01__, "server", Utils.getMessage(var6));
      } finally {
         ;
      }
   }

   InputStream getInputStream() {
      return this.ibBlobInputStream_;
   }

   InputStream getUnicodeInputStream() {
      return new interbase.interclient.ByteToUnicodeConverterStream(this.ibBlobInputStream_, this.reader_);
   }

   byte[] getBytes() throws java.sql.SQLException, IOException {
      int var1 = this.ibBlobInputStream_.available();
      byte[] var2 = new byte[var1];
      this.getBytes(var2, 0, var1);
      return var2;
   }

   private void getBytes(byte[] var1, int var2, int var3) throws java.sql.SQLException {
      try {
         for(int var4 = 0; var4 < var3; var4 += this.ibBlobInputStream_.read(var1, var2 + var4, var3 - var4)) {
            ;
         }

      } catch (IOException var6) {
         throw new CommunicationException(interbase.interclient.ErrorKey.communication__io_exception_on_blob_read_01__, "server", Utils.getMessage(var6));
      } catch (ArrayIndexOutOfBoundsException var7) {
         throw new BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 130);
      }
   }

   String getString() throws java.sql.SQLException, IOException {
      int var1 = this.ibBlobInputStream_.available();
      if (var1 > 0) {
         char[] var2 = Globals.cache__.takeCharBuffer(var1);
         byte[] var3 = Globals.cache__.takeBuffer(var1);

         String var6;
         try {
            this.getBytes(var3, 0, var1);

            try {
               int var4 = this.statement_.connection_.btc_.convert(var3, 0, var1, var2, 0, var1);
               int var5 = this.statement_.maxFieldSize_ == 0 ? var4 : Math.min(var4, this.statement_.maxFieldSize_);
               var6 = new String(var2, 0, var5);
            } catch (CharacterCodingException var11) {
               throw new CharacterEncodingException(interbase.interclient.ErrorKey.characterEncoding__read_0__, Utils.getMessage(var11));
            }
         } finally {
            Globals.cache__.returnCharBuffer(var2);
            Globals.cache__.returnBuffer(var3);
         }

         return var6;
      } else {
         return null;
      }
   }

   class NamelessClass_1 {
   }

   public class IBBlobInputStream extends InputStream {
      private byte[] buffer;
      private int pos;

      private IBBlobInputStream() {
         this.buffer = null;
         this.pos = 0;
      }

      public int available() throws IOException {
         if (this.buffer == null) {
            if (BlobOutput.this.blobHandle_.isEof()) {
               return -1;
            }

            try {
               this.buffer = BlobOutput.this.statement_.connection_.ibase_.iscGetSegment(BlobOutput.this.blobHandle_, BlobOutput.this.bufferlength, BlobOutput.this.sqlWarnings_);
            } catch (java.sql.SQLException var2) {
               throw new interbase.interclient.BlobIOException("62", Utils.getMessage(var2));
            }

            this.pos = 0;
            if (this.buffer.length == 0) {
               return -1;
            }
         }

         return this.buffer.length - this.pos;
      }

      public int read() throws IOException {
         if (this.available() <= 0) {
            return -1;
         } else {
            int var1 = this.buffer[this.pos++] & 255;
            if (this.pos == this.buffer.length) {
               this.buffer = null;
            }

            return var1;
         }
      }

      public int read(byte[] var1) throws IOException {
         return this.read(var1, 0, var1.length);
      }

      public int read(byte[] var1, int var2, int var3) throws IOException {
         int var4 = this.available();
         if (var4 <= 0) {
            return -1;
         } else if (var4 > var3) {
            System.arraycopy(this.buffer, this.pos, var1, var2, var3);
            this.pos += var3;
            return var3;
         } else {
            System.arraycopy(this.buffer, this.pos, var1, var2, var4);
            this.buffer = null;
            this.pos = 0;
            return var4;
         }
      }

      public void close() throws IOException {
         if (BlobOutput.this.blobHandle_ != null) {
            try {
               BlobOutput.this.statement_.connection_.ibase_.iscCloseBlob(BlobOutput.this.blobHandle_, BlobOutput.this.sqlWarnings_);
            } catch (java.sql.SQLException var2) {
               throw new BlobIOException("63", Utils.getMessage(var2));
            }

            BlobOutput.this.blobHandle_ = null;
         }

      }

      // $FF: synthetic method
      IBBlobInputStream(NamelessClass_1 var2) {
         this();
      }
   }
}
