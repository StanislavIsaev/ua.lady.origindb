package interbase.interclient;

import java.util.Vector;

final class BufferCache {
   private static final int minBufferLength__ = 1024;
   private static final int minCharBufferLength__ = 256;
   private static int maxMemoryCached__ = 500000;
   private static int maxMemoryCachedPerConnection__ = 500000;
   private int numConnections_ = 1;
   private int memoryCached_ = 0;
   private Vector cachedBuffers_ = new Vector(12, 12);
   private Vector cachedOutputBuffers_ = new Vector(12, 12);
   private Vector cachedCharBuffers_ = new Vector(12, 12);

   synchronized void incrementConnectionCount() {
      ++this.numConnections_;
      maxMemoryCached__ += maxMemoryCachedPerConnection__;
   }

   synchronized void decrementConnectionCount() {
      --this.numConnections_;
      maxMemoryCached__ -= maxMemoryCachedPerConnection__;
   }

   synchronized byte[] takeBuffer(int var1) {
      for(int var3 = 0; var3 < this.cachedBuffers_.size(); ++var3) {
         byte[] var2 = (byte[])this.cachedBuffers_.elementAt(var3);
         if (var2.length >= var1) {
            this.cachedBuffers_.removeElementAt(var3);
            this.memoryCached_ -= var2.length;
            return var2;
         }
      }

      return new byte[Math.max(var1, 1024)];
   }

   synchronized void returnBuffer(byte[] var1) {
      if (var1 != null) {
         boolean var2 = false;
         this.memoryCached_ += var1.length;

         for(int var3 = 0; var3 < this.cachedBuffers_.size(); ++var3) {
            if (((byte[])this.cachedBuffers_.elementAt(var3)).length > var1.length) {
               this.cachedBuffers_.insertElementAt(var1, var3);
               var2 = true;
               break;
            }
         }

         if (!var2) {
            this.cachedBuffers_.addElement(var1);
         }

      }
   }

   synchronized char[] takeCharBuffer(int var1) {
      for(int var3 = 0; var3 < this.cachedCharBuffers_.size(); ++var3) {
         char[] var2 = (char[])this.cachedCharBuffers_.elementAt(var3);
         if (var2.length >= var1) {
            this.cachedCharBuffers_.removeElementAt(var3);
            return var2;
         }
      }

      return new char[Math.max(var1, 256)];
   }

   synchronized void returnCharBuffer(char[] var1) {
      if (var1 != null) {
         boolean var2 = false;

         for(int var3 = 0; var3 < this.cachedCharBuffers_.size(); ++var3) {
            if (((char[])this.cachedCharBuffers_.elementAt(var3)).length > var1.length) {
               this.cachedCharBuffers_.insertElementAt(var1, var3);
               var2 = true;
               break;
            }
         }

         if (!var2) {
            this.cachedCharBuffers_.addElement(var1);
         }

      }
   }
}
