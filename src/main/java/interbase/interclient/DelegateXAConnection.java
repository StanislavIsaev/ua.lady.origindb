package interbase.interclient;

import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;
import javax.sql.StatementEventListener;

class DelegateXAConnection implements javax.sql.XAConnection, ConnectionEventListener {
   private javax.sql.XAConnection con;

   DelegateXAConnection(javax.sql.XAConnection var1) {
      this.con = var1;
      var1.addConnectionEventListener(this);
   }

   private final void checkCon() throws java.sql.SQLException {
      if (this.con == null) {
         throw new java.sql.SQLException("connection is already closed");
      }
   }

   private final void checkConRuntime() {
      try {
         this.checkCon();
      } catch (java.sql.SQLException var2) {
         throw new RuntimeException(var2.getMessage());
      }
   }

   public void connectionClosed(ConnectionEvent var1) {
      this.forgetCon();
   }

   private final void forgetCon() {
      this.con.removeConnectionEventListener(this);
      this.con = null;
   }

   public void connectionErrorOccurred(ConnectionEvent var1) {
   }

   public javax.transaction.xa.XAResource getXAResource() throws java.sql.SQLException {
      this.checkCon();
      return this.con.getXAResource();
   }

   public void addConnectionEventListener(ConnectionEventListener var1) {
      this.checkConRuntime();
      this.con.addConnectionEventListener(var1);
   }

   public void close() throws java.sql.SQLException {
      if (this.con != null) {
         this.con.close();
         this.forgetCon();
      }

   }

   public java.sql.Connection getConnection() throws java.sql.SQLException {
      this.checkCon();
      return this.con.getConnection();
   }

   public void removeConnectionEventListener(ConnectionEventListener var1) {
      this.checkConRuntime();
      this.con.removeConnectionEventListener(var1);
   }

   @Override
   public void addStatementEventListener(StatementEventListener listener) {

   }

   @Override
   public void removeStatementEventListener(StatementEventListener listener) {

   }
}
