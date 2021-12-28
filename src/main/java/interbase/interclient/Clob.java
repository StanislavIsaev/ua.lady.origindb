package interbase.interclient;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public final class Clob implements java.sql.Clob {
   public synchronized long length() throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized String getSubString(long var1, int var3) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Reader getCharacterStream() throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized InputStream getAsciiStream() throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized long position(String var1, long var2) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized long position(java.sql.Clob var1, long var2) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized int setString(long var1, String var3) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized int setString(long var1, String var3, int var4, int var5) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized OutputStream setAsciiStream(long var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Writer setCharacterStream(long var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void truncate(long var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   @Override
   public void free() throws java.sql.SQLException {

   }

   @Override
   public Reader getCharacterStream(long pos, long length) throws java.sql.SQLException {
      return null;
   }
}
