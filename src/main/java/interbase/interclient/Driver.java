package interbase.interclient;

import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Logger;

public final class Driver implements java.sql.Driver {
   boolean expiredDriver_ = false;
   private static java.sql.SQLException exceptionsOnLoadDriver__ = null;
   public static final int testBuild = 0;
   public static final int betaBuild = 1;
   public static final int finalBuild = 2;

   public Driver() {
      if (Globals.interclientExpirationDate__ != null) {
         Date var1 = new Date();
         if (var1.after(Globals.interclientExpirationDate__)) {
            this.expiredDriver_ = true;
         }
      }

   }

   public synchronized java.sql.Connection connect(String var1, Properties var2) throws java.sql.SQLException {
      if (exceptionsOnLoadDriver__ != null) {
         throw exceptionsOnLoadDriver__;
      } else {
         StringTokenizer var3 = this.tokenizeInterBaseProtocol(var1);
         if (var3 == null) {
            return null;
         } else {
            String var4 = this.tokenizeServerNameAndPort(var3, var1);
            int var5 = var4.indexOf(58);
            String var6 = var5 == -1 ? var4 : var4.substring(0, var5);
            int var7 = var5 == -1 ? 3050 : Integer.parseInt(var4.substring(var5 + 1));
            String var8 = this.tokenizeDatabase(var3, var1);
            if (var2 != null && var2.getProperty("user") != null && var2.getProperty("password") != null && !var2.getProperty("user").equals("") && !var2.getProperty("password").equals("")) {
               String var9 = var2.getProperty("sqlDialect");

               int var10;
               try {
                  if (var9 != null) {
                     var10 = Integer.parseInt(var9);
                     if (var10 < 0) {
                        throw new SQLDialectException(ErrorKey.invalidArgument__connection_properties__sql_dialect__, var9);
                     }
                  }
               } catch (NumberFormatException var12) {
                  throw new SQLDialectException(ErrorKey.invalidArgument__connection_properties__sql_dialect__, var9);
               }

               var10 = DriverManager.getLoginTimeout() * 1000;
               Connection var11 = new Connection(var10, var6, var7, var8, var2);
               return var11;
            } else {
               throw new InvalidArgumentException(ErrorKey.invalidArgument__connection_properties__no_user_or_password__);
            }
         }
      }
   }

   public boolean acceptsURL(String var1) throws java.sql.SQLException {
      return this.tokenizeInterBaseProtocol(var1) != null;
   }

   public synchronized DriverPropertyInfo[] getPropertyInfo(String var1, Properties var2) {
      DriverPropertyInfo[] var3 = new DriverPropertyInfo[10];
      if (var2 == null) {
         var2 = new Properties();
      }

      var3[0] = new DriverPropertyInfo("user", var2.getProperty("user"));
      var3[1] = new DriverPropertyInfo("password", var2.getProperty("password"));
      var3[2] = new DriverPropertyInfo("charSet", var2.getProperty("charSet", "NONE"));
      var3[3] = new DriverPropertyInfo("roleName", var2.getProperty("roleName"));
      var3[4] = new DriverPropertyInfo("interBaseLicense", var2.getProperty("interBaseLicense"));
      var3[5] = new DriverPropertyInfo("sweepOnConnect", var2.getProperty("sweepOnConnect", "false"));
      var3[6] = new DriverPropertyInfo("suggestedCachePages", var2.getProperty("suggestedCachePages"));
      var3[7] = new DriverPropertyInfo("sqlDialect", var2.getProperty("sqlDialect"));
      var3[8] = new DriverPropertyInfo("createDatabase", var2.getProperty("createDatabase", "false"));
      var3[9] = new DriverPropertyInfo("portNumber", var2.getProperty("portNumber"));
      var3[0].description = Globals.getResource("107");
      var3[1].description = Globals.getResource("108");
      var3[2].description = Globals.getResource("109");
      var3[3].description = Globals.getResource("110");
      var3[4].description = Globals.getResource("111");
      var3[5].description = Globals.getResource("112");
      var3[6].description = Globals.getResource("114");
      var3[7].description = Globals.getResource("128");
      var3[8].description = Globals.getResource("113");
      var3[9].description = Globals.getResource("136");
      var3[0].required = true;
      var3[1].required = true;
      var3[2].required = false;
      var3[3].required = false;
      var3[4].required = false;
      var3[5].required = false;
      var3[6].required = false;
      var3[7].required = false;
      var3[8].required = false;
      var3[9].required = false;
      var3[2].choices = CharacterEncodings.getSupportedEncodings();
      var3[5].choices = new String[]{"false", "true"};
      var3[7].choices = new String[]{"1", "2", "3"};
      var3[8].choices = new String[]{"false", "true"};
      return var3;
   }

   private StringTokenizer tokenizeInterBaseProtocol(String var1) {
      if (var1 == null) {
         return null;
      } else {
         StringTokenizer var2 = new StringTokenizer(var1, "/ \t\n\r", true);
         if (!var2.hasMoreTokens()) {
            return null;
         } else {
            String var3 = var2.nextToken();
            return !var3.equals("jdbc:interbase:") ? null : var2;
         }
      }
   }

   private String tokenizeServerNameAndPort(StringTokenizer var1, String var2) throws java.sql.SQLException {
      if (!var1.hasMoreTokens()) {
         throw new URLSyntaxException(ErrorKey.urlSyntax__bad_server_prefix_0__, var2);
      } else if (!var1.nextToken().equals("/")) {
         throw new URLSyntaxException(ErrorKey.urlSyntax__bad_server_prefix_0__, var2);
      } else if (!var1.nextToken().equals("/")) {
         throw new URLSyntaxException(ErrorKey.urlSyntax__bad_server_prefix_0__, var2);
      } else {
         try {
            return var1.nextToken();
         } catch (NoSuchElementException var4) {
            throw new URLSyntaxException(ErrorKey.urlSyntax__bad_server_prefix_0__, var2);
         }
      }
   }

   private String tokenizeDatabase(StringTokenizer var1, String var2) throws java.sql.SQLException {
      try {
         if (!var1.nextToken().equals("/")) {
            throw new URLSyntaxException(ErrorKey.urlSyntax__bad_server_suffix_0__, var2);
         } else {
            return var1.nextToken("\t\n\r");
         }
      } catch (NoSuchElementException var4) {
         throw new URLSyntaxException(ErrorKey.urlSyntax__bad_server_suffix_0__, var2);
      }
   }

   public int getMajorVersion() {
      return 4;
   }

   public int getMinorVersion() {
      return 8;
   }

   public static String getInterClientVersionInfo() {
      return "4.8.1 for JRE 1.3, JRE 1.4 and J2SE 5 with InterBase 7.5";
   }

   public boolean jdbcCompliant() {
      return true;
   }

   @Override
   public Logger getParentLogger() throws SQLFeatureNotSupportedException {
      return null;
   }

   public int getBuildNumber() {
      return 1;
   }

   public int getBuildCertificationLevel() {
      return 2;
   }

   public boolean clientServerEdition() {
      return true;
   }

   public boolean expiredDriver() {
      return this.expiredDriver_;
   }

   public String getCompanyName() {
      return Globals.getResource("97");
   }

   public String[] getCompatibleJREVersions() {
      return Globals.interclientCompatibleJREVersions__;
   }

   public int[] getCompatibleIBVersions() {
      return Globals.compatibleIBVersions__;
   }

   public Date getExpirationDate() {
      return Globals.interclientExpirationDate__;
   }

   public String getJDBCNetProtocol() {
      return "jdbc:interbase:";
   }

   static {
      Utils.accumulateSQLExceptions(exceptionsOnLoadDriver__, Globals.exceptionsOnLoadResources__);

      try {
         DriverManager.registerDriver(new Driver());
      } catch (java.sql.SQLException var1) {
         Utils.accumulateSQLExceptions(exceptionsOnLoadDriver__, var1);
      }

   }
}
