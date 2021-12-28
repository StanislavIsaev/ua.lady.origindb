package interbase.interclient;

import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.naming.spi.ObjectFactory;

public abstract class DataSourcePropertiesObject implements ObjectFactory {
   Reference ref_ = null;

   void getDataSourcePropertiesFromRef(DataSourceProperties var1) {
      var1.setDatabaseName(this.getString("databaseName"));
      var1.setDataSourceName(this.getString("dataSourceName"));
      var1.setDescription(this.getString("description"));
      var1.setNetworkProtocol(this.getString("networkProtocol"));
      var1.setPassword(this.getString("password"));

      try {
         var1.setPortNumber(Integer.parseInt(this.getString("portNumber")));
      } catch (NumberFormatException var5) {
         var1.setPortNumber(3050);
      }

      var1.setRoleName(this.getString("roleName"));
      var1.setServerName(this.getString("serverName"));
      var1.setUser(this.getString("user"));
      var1.setCharSet(this.getString("charSet"));

      try {
         var1.setSuggestedCachePages(Integer.parseInt(this.getString("suggestedCachePages")));
      } catch (NumberFormatException var4) {
         var1.setSuggestedCachePages(0);
      }

      var1.setSweepOnConnect((new Boolean(this.getString("sweepOnConnect"))).booleanValue());
      var1.setServerManagerHost(this.getString("serverManagerHost"));

      try {
         var1.setSqlDialect(Integer.parseInt(this.getString("sqlDialect")));
      } catch (NumberFormatException var3) {
         var1.setSqlDialect(0);
      }

      var1.setCreateDatabase((new Boolean(this.getString("createDatabase"))).booleanValue());
   }

   final void setString(String var1, String var2) {
      if (var2 != null) {
         this.ref_.add(new StringRefAddr(var1, var2));
      }

   }

   final String getString(String var1) {
      RefAddr var2 = this.ref_.get(var1);
      return var2 != null ? (String)var2.getContent() : null;
   }

   void setDataSourcePropertiesForRef(DataSourceProperties var1) throws NamingException {
      this.setString("databaseName", var1.getDatabaseName());
      this.setString("dataSourceName", var1.getDataSourceName());
      this.setString("description", var1.getDescription());
      this.setString("networkProtocol", var1.getNetworkProtocol());
      this.setString("serverName", var1.getServerName());
      this.setString("user", var1.getUser());
      this.setString("password", var1.getPassword());
      this.setString("roleName", var1.getRoleName());
      Integer var2 = new Integer(var1.getPortNumber());
      this.setString("portNumber", var2.toString());
      this.setString("charSet", var1.getCharSet());
      var2 = new Integer(var1.getSuggestedCachePages());
      this.setString("suggestedCachePages", var2.toString());
      if (var1.getSweepOnConnect()) {
         this.setString("sweepOnConnect", "true");
      }

      if (var1.getCreateDatabase()) {
         this.setString("createDatabase", "true");
      }

      this.setString("serverManagerHost", var1.getServerManagerHost());
      var2 = new Integer(var1.getSqlDialect());
      if (var2.intValue() != 0) {
         this.setString("sqlDialect", var2.toString());
      }

   }

   boolean setRef(Object var1) {
      if (var1 instanceof Reference) {
         this.ref_ = (Reference)var1;
         return true;
      } else {
         this.ref_ = null;
         return false;
      }
   }

   final Reference getRef(String var1) {
      RefAddr var2 = this.ref_.get(var1);
      return var2 != null ? (Reference)var2.getContent() : null;
   }
}
