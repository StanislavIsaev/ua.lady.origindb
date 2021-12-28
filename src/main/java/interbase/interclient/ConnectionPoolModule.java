package interbase.interclient;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;
import javax.sql.ConnectionPoolDataSource;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.DriverManager;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class ConnectionPoolModule implements javax.sql.DataSource, javax.sql.XADataSource, ConnectionEventListener, Referenceable, Serializable {
   private ConnectionPoolDataSource cpds = null;
   private String user_ = null;
   private String password_ = null;
   private String factoryName_;
   private transient PoolItem usedList_;
   private transient PoolItem pooledList_;
   private int maxPoolCount_;
   private int minPoolCount_;
   private int maxCount_;
   private transient int poolCount;
   private transient int totalCount;
   transient PrintWriter logWriter_;
   private int timeout_;
   private int waitCount_;

   public java.sql.Connection getConnection() throws java.sql.SQLException {
      return this.getPooledConnection().getConnection();
   }

   public java.sql.Connection getConnection(String var1, String var2) throws java.sql.SQLException {
      return this.getPooledConnection(var1, var2).getConnection();
   }

   private final javax.sql.PooledConnection getPooledConnection() throws java.sql.SQLException {
      return this.getPooledConnection(this.user_, this.password_);
   }

   public synchronized PrintWriter getLogWriter() throws java.sql.SQLException {
      return this.logWriter_;
   }

   public synchronized void setLogWriter(PrintWriter var1) throws java.sql.SQLException {
      this.logWriter_ = var1;
   }

   public synchronized void setLoginTimeout(int var1) throws java.sql.SQLException {
      this.timeout_ = var1;
   }

   public synchronized int getLoginTimeout() throws java.sql.SQLException {
      return this.timeout_;
   }

   @Override
   public Logger getParentLogger() throws SQLFeatureNotSupportedException {
      return null;
   }

   public Reference getReference() throws NamingException {
      return (new ConnectionPoolObject()).getReference(this);
   }

   public javax.sql.XAConnection getXAConnection() throws java.sql.SQLException {
      javax.sql.XAConnection var1 = (javax.sql.XAConnection)this.getPooledConnection();
      return new interbase.interclient.DelegateXAConnection(var1);
   }

   public javax.sql.XAConnection getXAConnection(String var1, String var2) throws java.sql.SQLException {
      javax.sql.XAConnection var3 = (javax.sql.XAConnection)this.getPooledConnection(var1, var2);
      return new interbase.interclient.DelegateXAConnection(var3);
   }

   private final synchronized javax.sql.PooledConnection getPooledConnection(String var1, String var2) throws java.sql.SQLException {
      Object var4 = null;
      PoolItem var3;
      if ((var3 = this.removeFromPooled(var1)) != null) {
         if (this.logWriter_ != null || DriverManager.getLogWriter() != null) {
            this.println(var3.con, "getPooledConnection(" + var1 + ",***) <from pool>");
         }

         this.addToUsed(var3);
         return var3.con;
      } else {
         if (this.maxCount_ != 0 && this.totalCount >= this.maxCount_) {
            ++this.waitCount_;

            try {
               if (this.timeout_ == 0) {
                  this.wait();
               } else {
                  this.wait((long)this.timeout_);
               }
            } catch (InterruptedException var7) {
               ;
            }

            --this.waitCount_;
            if (this.totalCount >= this.maxCount_) {
               throw new java.sql.SQLException("Too many connections");
            }
         }

         ++this.totalCount;
         if (this.cpds == null) {
            if (this.factoryName_ == null) {
               throw new java.sql.SQLException("Factory not set");
            }

            try {
               InitialContext var5 = new InitialContext();
               this.cpds = (ConnectionPoolDataSource)var5.lookup(this.factoryName_);
            } catch (NamingException var6) {
               throw new java.sql.SQLException(var6.getMessage());
            }
         }

         javax.sql.PooledConnection var8 = this.cpds.getPooledConnection(var1, var2);
         if (var8 == null) {
            throw new java.sql.SQLException("pooled connection error, this should be exception");
         } else {
            var8.addConnectionEventListener(this);
            var3 = new PoolItem(var8, var1);
            if (this.logWriter_ != null || DriverManager.getLogWriter() != null) {
               this.println(var3.con, "getPooledConnection(" + var1 + ",***) <created>");
            }

            this.addToUsed(var3);
            return var8;
         }
      }
   }

   final void println(Object var1, String var2) {
      if (this.logWriter_ != null) {
         this.logWriter_.println(var2);
      }

      DriverManager.println(var2);
   }

   public final void setUser(String var1) {
      this.user_ = var1;
   }

   public final String getUser() {
      return this.user_;
   }

   public final void setPassword(String var1) {
      this.password_ = var1;
   }

   public final String getPassword() {
      return this.password_;
   }

   public final void setMinPool(int var1) {
      this.minPoolCount_ = var1;
   }

   public int getMinPool() {
      return this.minPoolCount_;
   }

   public final void setMaxPool(int var1) {
      this.maxPoolCount_ = var1;
   }

   public int getMaxPool() {
      return this.maxPoolCount_;
   }

   public final void setMaxConnections(int var1) {
      this.maxCount_ = var1;
   }

   public int getMaxConnections() {
      return this.maxCount_;
   }

   public final void setConnectionFactory(ConnectionPoolDataSource var1) throws SQLException {
      this.cpds = var1;
   }

   public final ConnectionPoolDataSource getConnectionFactory() {
      return this.cpds;
   }

   public void setDataSourceName(String var1) {
      this.factoryName_ = var1;
   }

   public String getDataSourceName() {
      return this.factoryName_;
   }

   public final synchronized void connectionClosed(ConnectionEvent var1) {
      PoolItem var2 = this.removeFromUsed(var1.getSource());
      if (var2 != null) {
         try {
            this.addToPooled(var2);
         } catch (java.sql.SQLException var4) {
            throw new RuntimeException(var4.getMessage());
         }

         if (this.waitCount_ > 0) {
            this.notify();
         }
      }

   }

   public final void connectionErrorOccurred(ConnectionEvent var1) {
   }

   public final synchronized void shutdown() throws java.sql.SQLException {
      this.shutdown(this.usedList_);
      this.usedList_ = null;
      this.shutdown(this.pooledList_);
      this.pooledList_ = null;
      this.poolCount = 0;
   }

   private final void shutdown(PoolItem var1) throws java.sql.SQLException {
      while(var1 != null) {
         var1.con.close();
         var1 = var1.next;
         --this.totalCount;
      }

   }

   private final void shrink() throws java.sql.SQLException {
      if (this.minPoolCount_ > 0 && this.minPoolCount_ < this.maxPoolCount_) {
         while(this.poolCount > this.minPoolCount_ && this.pooledList_ != null) {
            this.pooledList_.con.close();
            this.pooledList_ = this.pooledList_.next;
            --this.totalCount;
            --this.poolCount;
         }
      }

   }

   private final void addToPooled(PoolItem var1) throws java.sql.SQLException {
      if (this.poolCount >= this.maxPoolCount_ && this.maxPoolCount_ != 0) {
         var1.con.close();
         --this.totalCount;
         this.shrink();
      } else {
         this.pooledList_ = this.add(this.pooledList_, var1);
         ++this.poolCount;
      }

   }

   private final void addToUsed(PoolItem var1) {
      this.usedList_ = this.add(this.usedList_, var1);
   }

   private final PoolItem add(PoolItem var1, PoolItem var2) {
      var2.next = var1;
      return var2;
   }

   private final PoolItem removeFromPooled(String var1) {
      PoolItem var2 = this.removeFromPooled(var1, true);
      if (var2 == null) {
         var2 = this.removeFromPooled(var1, false);
      }

      if (var2 != null) {
         --this.poolCount;
      }

      return var2;
   }

   private final PoolItem removeFromPooled(String var1, boolean var2) {
      PoolItem var3 = this.pooledList_;

      for(PoolItem var4 = this.pooledList_; var3 != null; var3 = var3.next) {
         if (var2 && var3.name == var1 || !var2 && var3.name.equals(var1)) {
            if (var3 == this.pooledList_) {
               this.pooledList_ = var3.next;
            } else {
               var4.next = var3.next;
            }

            var3.next = null;
            return var3;
         }

         var4 = var3;
      }

      return null;
   }

   private final PoolItem removeFromUsed(Object var1) {
      PoolItem var2 = this.usedList_;
      PoolItem var3 = this.usedList_;

      for(var2 = this.usedList_; var2 != null; var2 = var2.next) {
         if (var2.con == var1) {
            if (var2 == this.usedList_) {
               this.usedList_ = var2.next;
            } else {
               var3.next = var2.next;
            }

            var2.next = null;
            return var2;
         }

         var3 = var2;
      }

      return null;
   }

   @Override
   public <T> T unwrap(Class<T> iface) throws java.sql.SQLException {
      return null;
   }

   @Override
   public boolean isWrapperFor(Class<?> iface) throws java.sql.SQLException {
      return false;
   }

   class PoolItem {
      javax.sql.PooledConnection con;
      String name;
      PoolItem next;

      PoolItem(javax.sql.PooledConnection var2, String var3) {
         this.con = var2;
         this.name = var3;
      }
   }
}
