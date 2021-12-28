package interbase.interclient;

import java.io.InputStream;
import java.io.OutputStream;

public final class Blob implements java.sql.Blob {
   public synchronized long length() throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized byte[] getBytes(long var1, int var3) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized InputStream getBinaryStream() throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized long position(byte[] var1, long var2) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized long position(java.sql.Blob var1, long var2) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized int setBytes(long var1, byte[] var3) throws interbase.interclient.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized int setBytes(long var1, byte[] var3, int var4, int var5) throws interbase.interclient.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized OutputStream setBinaryStream(long var1) throws interbase.interclient.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void truncate(long var1) throws interbase.interclient.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   @Override
   public void free() throws java.sql.SQLException {

   }

   @Override
   public InputStream getBinaryStream(long pos, long length) throws java.sql.SQLException {
      return null;
   }
}
