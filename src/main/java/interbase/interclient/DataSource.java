package interbase.interclient;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class DataSource extends DataSourceProperties implements javax.sql.DataSource, Referenceable, Serializable {
   public DataSource() {
      this.setDriver("interbase.interclient.Driver");
   }

   public java.sql.Connection getConnection() throws SQLException {
      return this.getConnection(this.getUser(), this.getPassword());
   }

   public java.sql.Connection getConnection(String var1, String var2) throws SQLException {
      this.println("getConnection(" + var1 + ", ****)", this);
      if (this.getNetworkProtocol() != null && !this.getNetworkProtocol().equals("jdbc:interbase:")) {
         throw new SQLException("invalid network protocol");
      } else {
         Properties var3 = new Properties();
         this.setUser(var1);
         this.setPassword(var2);
         this.setPropertiesForDataSource(var3);
         this.loadDriver();
         return new Connection(this.loginTimeout * 1000, this.getServerName(), this.getPortNumber(), this.getDatabaseName(), var3);
      }
   }

   public Reference getReference() throws NamingException {
      return (new DataSourceObject()).getReference(this);
   }

   @Override
   public <T> T unwrap(Class<T> iface) throws SQLException {
      return null;
   }

   @Override
   public boolean isWrapperFor(Class<?> iface) throws SQLException {
      return false;
   }

   @Override
   public Logger getParentLogger() throws SQLFeatureNotSupportedException {
      return null;
   }
}
