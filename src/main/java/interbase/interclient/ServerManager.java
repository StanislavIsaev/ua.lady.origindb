package interbase.interclient;

import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

public final class ServerManager {
   private int sessionRef_ = 0;
   private interbase.interclient.IBException sqlWarnings_ = null;
   private boolean open_ = true;
   private String user_;
   private String role_;
   private String password_;
   private interbase.interclient.Ibase ibase_;

   ServerManager(String var1, String var2, String var3) {
      this.user_ = var1;
      this.role_ = var3;
      this.password_ = var2;
      this.ibase_ = new interbase.interclient.Ibase();
   }

   private void checkForClosedConnection() throws java.sql.SQLException {
      if (!this.open_) {
         throw new InvalidOperationException(interbase.interclient.ErrorKey.invalidOperation__server_connection_closed__);
      }
   }

   private void connect(int var1, String var2) throws java.sql.SQLException {
   }

   public int getInterServerMajorVersion() {
      return 0;
   }

   public int getInterServerMinorVersion() {
      return 0;
   }

   public int getInterServerBuildNumber() {
      return 0;
   }

   public int getInterServerBuildCertificationLevel() {
      return 0;
   }

   public int getInterServerJDBCNetProtocolVersion() {
      return 0;
   }

   public Date getInterServerExpirationDate() {
      return null;
   }

   public synchronized String getInterBaseVersion() throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized String[] getDatabasesInUse() throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized int getNumberOfInterBaseConnectionsInUse() throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized String[][] getInterBaseLicenses() throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized Map getRegisteredUsers() throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized InputStream getLockActivityStream(Map var1) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized void startInterBase(int var1, int var2) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized void stopInterBase() throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized void addInterBaseLicense(String var1, String var2) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized void deleteInterBaseLicense(String var1) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized void addUser(String var1, String var2, Map var3) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized void deleteUser(String var1) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized void modifyUser(String var1, Map var2) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized void modifyUser(String var1, String var2) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized void reserveSpaceForVersioning(String var1, boolean var2) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized void setDatabaseCachePages(String var1, int var2) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized void setDatabaseReadWrite(String var1, boolean var2) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized void setSweepInterval(String var1, int var2) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized void useSynchronousWrites(String var1, boolean var2) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized void takeOffline(String var1, boolean var2, boolean var3, boolean var4, int var5) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized void bringOnline(String var1) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized String backup(String var1, String[] var2, int[] var3, Map var4) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized String backup(String var1, String var2, Map var3) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized String restore(String var1, String[] var2, int[] var3, Map var4) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized String restore(String var1, String var2, Map var3) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized String sweep(String var1) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized String verify(String var1, Map var2) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized String repair(String var1, Map var2) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized void createShadow(String var1, String[] var2, int[] var3, Map var4) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized void createShadow(String var1, String var2, Map var3) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized void activateShadow(String var1) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized void deleteShadow(String var1) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized void createDatabase(String var1) throws java.sql.SQLException {
      this.checkForClosedConnection();
      this.remote_CREATE_DATABASE(var1);
   }

   private void remote_CREATE_DATABASE(String var1) throws java.sql.SQLException {
      try {
         new IscDbHandle();
         interbase.interclient.IBConnectionRequestInfo var3 = new interbase.interclient.IBConnectionRequestInfo();
         Properties var4 = new Properties();
         var4.setProperty("user", this.user_);
         var4.setProperty("password", this.password_);
         var4.setProperty("roleName", this.role_);
         Connection.setConnectionRequestInfo(var4, var3);
      } catch (interbase.interclient.IBException var5) {
         throw new java.sql.SQLException(var5.getMessage());
      } catch (interbase.interclient.SQLException var6) {
         throw var6;
      }
   }

   public synchronized void deleteDatabase(String var1) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized Object[] getLimboTransactions(String var1) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized void rollbackTransactions(String var1, int[] var2) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized void commitTransactions(String var1, int[] var2) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized void twoPhaseCommitTransactions(String var1, int[] var2) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public synchronized String getStatisticsText(String var1, Map var2) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__extension_not_yet_supported__);
   }

   public java.sql.SQLWarning getWarnings() throws java.sql.SQLException {
      return new java.sql.SQLWarning(this.sqlWarnings_.getMessage());
   }

   public synchronized void clearWarnings() throws java.sql.SQLException {
      this.sqlWarnings_ = null;
   }

   protected void finalize() throws Throwable {
      if (this.open_) {
         this.close();
      }

      super.finalize();
   }

   public synchronized void close() throws java.sql.SQLException {
      if (this.open_) {
         Object var1 = null;
         this.open_ = false;
      }
   }
}
