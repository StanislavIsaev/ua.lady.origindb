package interbase.interclient;

import java.io.PrintWriter;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class XADataSource implements javax.sql.XADataSource {
   public synchronized javax.sql.XAConnection getXAConnection() throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized javax.sql.XAConnection getXAConnection(String var1, String var2) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized PrintWriter getLogWriter() throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setLogWriter(PrintWriter var1) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setLoginTimeout(int var1) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized int getLoginTimeout() throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   @Override
   public Logger getParentLogger() throws SQLFeatureNotSupportedException {
      return null;
   }
}
