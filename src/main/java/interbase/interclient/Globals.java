package interbase.interclient;

import java.text.MessageFormat;
import java.util.Date;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

final class Globals {
   static final int testBuild_ = 0;
   static final int betaBuild_ = 1;
   static final int finalBuild_ = 2;
   static final int interclientMajorVersion__ = 4;
   static final int interclientMinorVersion__ = 8;
   static final int interclientBuildNumber__ = 1;
   static final int interclientBuildCertification__ = 2;
   static final String[] interclientCompatibleJREVersions__ = new String[]{"1.3", "1.4", "1.5"};
   static final int[] compatibleIBVersions__ = new int[]{7};
   static final String interclientVersionString__ = "4.8.1 for JRE 1.3, JRE 1.4 and J2SE 5 with InterBase 7.5";
   static final String jdbcNetProtocol__ = "jdbc:interbase:";
   static final String localHostOnlyRestrictedServer__ = "localhost";
   static final String sysdba__ = "SYSDBA";
   static final Date interclientExpirationDate__ = null;
   static final boolean clientServerEdition__ = true;
   static final int defaultServerPort__ = 3050;
   private static ResourceBundle resources__;
   static java.sql.SQLException exceptionsOnLoadResources__ = null;
   private static final Object[] emptyArgs__ = new Object[0];
   static final boolean debug__ = false;
   static long endTime__;
   static long startTime__;
   static interbase.interclient.BufferCache cache__ = new interbase.interclient.BufferCache();

   static void loadResources() throws java.sql.SQLException {
      try {
         resources__ = ResourceBundle.getBundle("interbase.interclient.Resources");
      } catch (MissingResourceException var1) {
         throw new java.sql.SQLException("[interclient] Missing resource bundle: an InterClient resource bundle could not be found in the interbase.interclient package.", "ICJJ0", 12);
      }
   }

   static String getResource(String var0, Object[] var1) {
      if (resources__ == null) {
         try {
            loadResources();
         } catch (java.sql.SQLException var6) {
            return var6.getMessage();
         }
      }

      try {
         return MessageFormat.format(resources__.getString(var0), var1);
      } catch (MissingResourceException var5) {
         MissingResourceException var2 = var5;

         try {
            return MessageFormat.format(resources__.getString("1"), var2.getKey(), var2.getClassName());
         } catch (MissingResourceException var4) {
            return MessageFormat.format("No resource for key {0} could be found in resource bundle {1}.", var5.getKey(), var5.getClassName());
         }
      }
   }

   static String getResource(String var0) {
      return getResource(var0, emptyArgs__);
   }

   static void trace(Object var0) {
   }
}
