package interbase.interclient;

public class XAConnection extends PooledConnection implements javax.sql.XAConnection {
   public synchronized javax.transaction.xa.XAResource getXAResource() throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }
}
