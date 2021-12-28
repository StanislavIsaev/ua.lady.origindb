package interbase.interclient;

import javax.naming.Reference;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.Properties;

public class DataSourceProperties implements Serializable {
   int loginTimeout = 0;
   PrintWriter logWriter_ = null;
   private String databaseName = null;
   private String dataSourceName = null;
   private String description = null;
   private String networkProtocol = null;
   private String password = null;
   private int portNumber = 3050;
   private String roleName = null;
   private String serverName = "localhost";
   private String user = null;
   private int sqlDialect = 0;
   private String driver_ = "interbase.interclient.Driver";
   private String charSet = "NONE";
   private String serverManagerHost = null;
   private int suggestedCachePages = 0;
   private boolean sweepOnConnect = false;
   private boolean createDatabase = false;
   Reference ref_ = null;
   Properties props = null;
   private boolean loaded = false;

   void setProperties() {
      this.setPropertiesForDataSource(this.props);
   }

   public String getDatabaseName() {
      return this.databaseName;
   }

   public synchronized void setDatabaseName(String var1) {
      this.databaseName = var1;
   }

   public String getDataSourceName() {
      return this.dataSourceName;
   }

   public synchronized void setDataSourceName(String var1) {
      this.dataSourceName = var1;
   }

   public String getDescription() {
      return this.description;
   }

   public synchronized void setDescription(String var1) {
      this.description = var1;
   }

   public String getNetworkProtocol() {
      return this.networkProtocol;
   }

   public synchronized void setNetworkProtocol(String var1) {
      this.networkProtocol = var1;
   }

   public String getPassword() {
      return this.password;
   }

   public synchronized void setPassword(String var1) {
      this.password = var1;
   }

   public int getPortNumber() {
      return this.portNumber;
   }

   public synchronized void setPortNumber(int var1) {
      this.portNumber = var1;
   }

   public String getRoleName() {
      return this.roleName;
   }

   public synchronized void setRoleName(String var1) {
      this.roleName = var1;
   }

   public String getServerName() {
      return this.serverName;
   }

   public synchronized void setServerName(String var1) {
      this.serverName = var1;
   }

   public String getUser() {
      return this.user;
   }

   public synchronized void setUser(String var1) {
      this.user = var1;
   }

   public String getCharSet() {
      return this.charSet;
   }

   public synchronized void setCharSet(String var1) {
      this.charSet = var1;
   }

   public String getServerManagerHost() {
      return this.serverManagerHost;
   }

   public synchronized void setServerManagerHost(String var1) {
      this.serverManagerHost = var1;
   }

   public int getSuggestedCachePages() {
      return this.suggestedCachePages;
   }

   public synchronized void setSuggestedCachePages(int var1) {
      this.suggestedCachePages = var1;
   }

   public boolean getSweepOnConnect() {
      return this.sweepOnConnect;
   }

   public synchronized void setSweepOnConnect(boolean var1) {
      this.sweepOnConnect = var1;
   }

   public boolean getCreateDatabase() {
      return this.createDatabase;
   }

   public synchronized void setCreateDatabase(boolean var1) {
      this.createDatabase = var1;
   }

   public int getSqlDialect() {
      return this.sqlDialect;
   }

   public synchronized void setSqlDialect(int var1) {
      this.sqlDialect = var1;
   }

   public synchronized void setLogWriter(PrintWriter var1) throws java.sql.SQLException {
      this.logWriter_ = var1;
      DriverManager.setLogWriter(var1);
   }

   public PrintWriter getLogWriter() throws java.sql.SQLException {
      return this.logWriter_;
   }

   public synchronized void setLoginTimeout(int var1) throws java.sql.SQLException {
      this.loginTimeout = var1;
   }

   public int getLoginTimeout() throws java.sql.SQLException {
      return this.loginTimeout;
   }

   public void setReference(Reference var1) {
      this.ref_ = var1;
   }

   void setPropertiesForDataSource(Properties var1) {
      if (this.user != null) {
         var1.put("user", this.user);
      }

      if (this.password != null) {
         var1.put("password", this.password);
      }

      if (this.roleName != null) {
         var1.put("roleName", this.roleName);
      }

      if (this.charSet != null) {
         var1.put("charSet", this.charSet);
      }

      if (this.sweepOnConnect) {
         var1.put("sweepOnConnect", String.valueOf(this.sweepOnConnect));
      }

      if (this.suggestedCachePages != 0) {
         var1.put("suggestedCachePages", String.valueOf(this.suggestedCachePages));
      }

      if (this.sqlDialect != 0) {
         var1.put("sqlDialect", String.valueOf(this.sqlDialect));
      }

      if (this.createDatabase) {
         var1.put("createDatabase", String.valueOf(this.createDatabase));
      }

      if (this.portNumber != 3050) {
         var1.put("portNumber", String.valueOf(this.portNumber));
      }

   }

   final void println(String var1, Object var2) {
      if (this.logWriter_ != null) {
         this.logWriter_.println(":" + var1 + " [" + var2 + "]");
         this.logWriter_.flush();
      } else {
         DriverManager.println(":" + var1 + " [" + var2 + "]");
      }

   }

   public String getDriver() {
      return this.driver_;
   }

   public void setDriver(String var1) {
      this.driver_ = var1;
   }

   final void loadDriver() throws java.sql.SQLException {
      if (!this.loaded) {
         try {
            if (this.props == null) {
               this.props = new Properties();
            }

            ClassLoader var1 = this.getClass().getClassLoader();
            Class var2 = var1 == null ? Class.forName(this.driver_) : var1.loadClass(this.driver_);
            Enumeration var3 = DriverManager.getDrivers();
            boolean var4 = false;

            while(var3.hasMoreElements()) {
               Object var5 = var3.nextElement();
               if (var2.isInstance(var5)) {
                  var4 = true;
                  break;
               }
            }

            if (!var4) {
               var2.newInstance();
            }

            this.loaded = true;
         } catch (Exception var6) {
            throw new java.sql.SQLException(var6.getMessage());
         }
      }

   }

   String makeUrl() {
      StringBuffer var1 = new StringBuffer("jdbc:interbase://");
      var1.append(this.serverName + "/");
      var1.append(this.databaseName);
      return var1.toString();
   }
}
