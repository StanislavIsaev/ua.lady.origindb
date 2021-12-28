package interbase.interclient;

import java.util.Properties;

public final class ConnectionProperties extends Properties {
   static final String databaseNameKey__ = "databaseName";
   static final String dataSourceNameKey__ = "dataSourceName";
   static final String descriptionKey__ = "description";
   static final String networkProtocolKey__ = "networkProtocol";
   static final String portNumberKey__ = "portNumber";
   static final String serverNameKey__ = "serverName";
   static final String userKey__ = "user";
   static final String passwordKey__ = "password";
   static final String roleNameKey__ = "roleName";
   static final String charSetKey__ = "charSet";
   static final String interBaseLicenseKey__ = "interBaseLicense";
   static final String sweepOnConnectKey__ = "sweepOnConnect";
   static final String suggestedCachePagesKey__ = "suggestedCachePages";
   static final String sqlDialectKey__ = "sqlDialect";
   static final String serverManagerHostKey__ = "serverManagerHost";
   static final String defaultCharSet__ = "NONE";
   static final int defaultPortNumberInt__ = 3050;
   static final String defaultNetworkProtocol__ = "jdbc:interbase:";
   static final String defaultSweepOnConnect__ = "false";
   static final String defaultSQLDialect__ = "0";
   static final String connectionFactoryKey__ = "connectionFactory";
   static final String maxConnectionsKey__ = "maxConnections";
   static final String maxPoolKey__ = "maxPool";
   static final String minPoolKey__ = "minPool";
   static final String loginTimeoutKey__ = "loginTimeout";
   static final String maxStatementsKey__ = "maxConStatements";
   static final String createDatabaseKey__ = "createDatabase";

   /** @deprecated */
   public void setCharacterEncoding(String var1) {
      this.put("charSet", var1);
   }

   /** @deprecated */
   public void setUser(String var1, String var2) {
      this.put("user", var1);
      this.put("password", var2);
   }
}
