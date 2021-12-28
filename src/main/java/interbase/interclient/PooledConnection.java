package interbase.interclient;

import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;
import javax.sql.StatementEventListener;
import java.io.PrintWriter;
import java.sql.DriverManager;

public class PooledConnection implements javax.sql.PooledConnection, javax.sql.XAConnection {
   private transient ConnectionEventListener[] listeners;
   private transient PrintWriter logWriter;
   private transient boolean closing;
   private transient ConnectionEvent event;
   private transient java.sql.Connection physCon;
   private transient interbase.interclient.Connection bCon;
   private transient interbase.interclient.Connection delegateCon;
   private transient DataSourceProperties props;
   private transient int maxStatements;

   PooledConnection() {
   }

   PooledConnection(java.sql.Connection var1, DataSourceProperties var2) {
      this.props = var2;
      this.physCon = var1;
      if (var1 instanceof interbase.interclient.Connection) {
         this.bCon = (Connection)var1;
      }

      this.event = new ConnectionEvent(this);
      this.logWriter = var2.logWriter_;
   }

   public synchronized java.sql.Connection getConnection() throws java.sql.SQLException {
      if (this.logWriter != null || DriverManager.getLogWriter() != null) {
         this.println("PooledConnection.getConnection()", this);
      }

      return this.physCon;
   }

   public synchronized void close() throws java.sql.SQLException {
      if (!this.closing) {
         try {
            this.closing = true;
            if (this.physCon != null) {
               this.physCon.close();
            }

            this.physCon = null;
            this.delegateCon = null;
            this.listeners = null;
         } finally {
            this.closing = false;
         }
      }

   }

   final synchronized void returnToPool() throws interbase.interclient.SQLException {
      ConnectionEventListener[] var1 = this.listeners;

      for(int var3 = 0; var3 < var1.length; ++var3) {
         ConnectionEventListener var2 = var1[var3];
         if (var2 != null) {
            var2.connectionClosed(this.event);
         }
      }

      this.delegateCon = null;
   }

   public synchronized void addConnectionEventListener(ConnectionEventListener var1) {
      this.addListener(var1);
   }

   public synchronized void removeConnectionEventListener(ConnectionEventListener var1) {
      if (this.listeners != null) {
         this.removeListener(var1);
      }

   }

   @Override
   public void addStatementEventListener(StatementEventListener listener) {

   }

   @Override
   public void removeStatementEventListener(StatementEventListener listener) {

   }

   public javax.transaction.xa.XAResource getXAResource() throws java.sql.SQLException {
      this.getConnection();
      if (this.logWriter != null || DriverManager.getLogWriter() != null) {
         this.println("PooledConnection.getXAResource()", this);
      }

      return null;
   }

   private final void addListener(ConnectionEventListener var1) {
      if (this.listeners == null) {
         this.listeners = new ConnectionEventListener[1];
         this.listeners[0] = var1;
      } else {
         for(int var2 = 0; var2 < this.listeners.length; ++var2) {
            if (this.listeners[var2] == null) {
               this.listeners[var2] = var1;
               return;
            }
         }

         int var3 = this.listeners.length;
         ConnectionEventListener[] var4 = new ConnectionEventListener[var3 + 1];
         System.arraycopy(this.listeners, 0, var4, 0, var3);
         var4[var3] = var1;
         this.listeners = var4;
      }

   }

   private final void removeListener(ConnectionEventListener var1) {
      for(int var2 = 0; var2 < this.listeners.length; ++var2) {
         if (this.listeners[var2] == var1) {
            this.listeners[var2] = null;
            return;
         }
      }

   }

   final void println(String var1, Object var2) {
      if (this.logWriter != null) {
         this.logWriter.println("  \"  :" + var1 + " [" + var2 + "]");
         this.logWriter.flush();
      }

      DriverManager.println(var1);
   }
}
