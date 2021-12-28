package interbase.interclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

class BlobInput {
   long blobId = 0L;
   static final int binary = 0;
   private static final int BUF_SIZE = 4096;
   private interbase.interclient.IBException sqlWarnings_;

   BlobInput(Statement var1, InputStream var2, int var3) throws java.sql.SQLException {
      interbase.interclient.IscBlobHandle var4 = var1.connection_.ibase_.getNewIscBlobHandle();

      try {
         var1.connection_.setTransaction();
         var1.connection_.ibase_.iscCreateBlob2(var1.connection_.db_, var1.connection_.tra_, var4, (IBStruct)null, this.sqlWarnings_);
         byte[] var5 = new byte[4096];

         try {
            int var6;
            for(; var3 > 0; var3 -= var6) {
               var6 = var2.read(var5, 0, var3 < 4096 ? var3 : 4096);
               if (var6 < 4096) {
                  byte[] var7 = new byte[var6];
                  System.arraycopy(var5, 0, var7, 0, var6);
                  var1.connection_.ibase_.iscPutSegment(var4, var7, this.sqlWarnings_);
               } else {
                  var1.connection_.ibase_.iscPutSegment(var4, var5, this.sqlWarnings_);
               }
            }

            var1.connection_.ibase_.iscCloseBlob(var4, this.sqlWarnings_);
            this.blobId = var4.getBlobId();
         } catch (IOException var8) {
            throw new java.sql.SQLException(var8.toString());
         }
      } catch (interbase.interclient.IBException var9) {
         throw new java.sql.SQLException(var9.getMessage());
      }
   }

   private ByteArrayOutputStream setBlobBinaryStream(InputStream var1, int var2) throws java.sql.SQLException {
      ByteArrayOutputStream var3 = new ByteArrayOutputStream(var2);
      byte[] var4 = new byte[4096];

      try {
         while(var2 > 0) {
            int var5 = var1.read(var4, 0, var2 < 4096 ? var2 : 4096);
            var3.write(var4, 0, var5);
            var2 -= var5;
         }

         var3.close();
         return var3;
      } catch (IOException var7) {
         throw new java.sql.SQLException(var7.toString());
      }
   }
}
