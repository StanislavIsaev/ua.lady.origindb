package interbase.interclient;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.sql.ConnectionPoolDataSource;
import java.sql.DriverManager;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class JdbcConnectionFactory extends DataSourceProperties implements ConnectionPoolDataSource, javax.sql.XADataSource, Referenceable {
   public synchronized javax.sql.PooledConnection getPooledConnection() throws java.sql.SQLException {
      return this.getXAConnection();
   }

   public synchronized javax.sql.PooledConnection getPooledConnection(String var1, String var2) throws java.sql.SQLException {
      return this.getXAConnection(var1, var2);
   }

   public javax.sql.XAConnection getXAConnection() throws java.sql.SQLException {
      return this.getXAConnection(this.getUser(), this.getPassword());
   }

   public javax.sql.XAConnection getXAConnection(String var1, String var2) throws java.sql.SQLException {
      this.loadDriver();
      this.setProperties();
      this.println("JdbcConnectionFactory.getXAConnection(" + var1 + ",***)", this);
      return new PooledConnection(DriverManager.getConnection(this.makeUrl(), this.props), this);
   }

   public Reference getReference() throws NamingException {
      return (new CPDSObject()).getReference(this);
   }

   @Override
   public Logger getParentLogger() throws SQLFeatureNotSupportedException {
      return null;
   }
}
