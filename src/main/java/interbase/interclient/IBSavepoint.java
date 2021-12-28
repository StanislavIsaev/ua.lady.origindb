package interbase.interclient;

import java.sql.Savepoint;

public final class IBSavepoint implements Savepoint {
   private int savepointId;
   private String savepointName;
   private boolean named;
   private boolean opened;
   private interbase.interclient.Connection connection;
   private java.sql.SQLWarning sqlWarning;
   private static String autoString = "IC_AUTO";

   IBSavepoint(String var1, interbase.interclient.Connection var2) throws interbase.interclient.IBException, java.sql.SQLException {
      this.savepointName = var1;
      this.named = true;
      this.connection = var2;
      this.createSavepoint();
   }

   IBSavepoint(int var1, interbase.interclient.Connection var2) throws interbase.interclient.IBException, java.sql.SQLException {
      Integer var3 = new Integer(var1);
      if (var1 >= 0) {
         this.savepointName = autoString + var3.toString();
         this.connection = var2;
         this.named = false;
         this.createSavepoint();
      } else {
         throw new interbase.interclient.IBException(225544003);
      }
   }

   protected void finalize() throws Throwable {
      this.savepointName = null;
      this.connection = null;
      this.sqlWarning = null;
      super.finalize();
   }

   synchronized void closeSavepoint() throws interbase.interclient.IBException, java.sql.SQLException {
      if (this.opened) {
         Object var1 = null;
         this.connection.ibase_.iscReleaseSavepoint(this.savepointName, this.connection.db_, this.connection.tra_, (interbase.interclient.IBException)var1);
         this.opened = false;
         this.setSQLWarnings((interbase.interclient.IBException)var1);
      }

   }

   private synchronized void createSavepoint() throws interbase.interclient.IBException, java.sql.SQLException {
      Object var1 = null;
      this.opened = true;
      this.connection.ibase_.iscStartSavepoint(this.savepointName, this.connection.db_, this.connection.tra_, (interbase.interclient.IBException)var1);
      this.setSQLWarnings((interbase.interclient.IBException)var1);
   }

   synchronized void rollbackSavepoint() throws interbase.interclient.IBException, java.sql.SQLException {
      if (this.opened) {
         Object var1 = null;
         this.connection.ibase_.iscRollbackSavepoint(this.savepointName, this.connection.db_, this.connection.tra_, (interbase.interclient.IBException)var1);
         this.setSQLWarnings((interbase.interclient.IBException)var1);
      }

   }

   public int getSavepointId() throws interbase.interclient.IBException {
      if (!this.named) {
         return this.savepointId;
      } else {
         throw new interbase.interclient.IBException(225544001);
      }
   }

   public String getSavepointName() throws interbase.interclient.IBException {
      if (this.named) {
         return this.savepointName;
      } else {
         throw new interbase.interclient.IBException(225544002);
      }
   }

   private void setSQLWarnings(interbase.interclient.IBException var1) {
      if (var1 != null) {
         if (this.sqlWarning == null) {
            this.sqlWarning = new java.sql.SQLWarning(var1.getMessage(), "", var1.getIbErrorCode());
         } else {
            this.sqlWarning.setNextWarning(new java.sql.SQLWarning(var1.getMessage(), "", var1.getIbErrorCode()));
         }

         interbase.interclient.IBException var2;
         while((var2 = var1.getNext()) != null) {
            this.sqlWarning.setNextWarning(new java.sql.SQLWarning(var2.getMessage(), "", var2.getIbErrorCode()));
         }
      }

   }

   static Savepoint setSavepoint(String var0, interbase.interclient.Connection var1) throws interbase.interclient.IBException, java.sql.SQLException {
      return new IBSavepoint(var0, var1);
   }

   static Savepoint setSavepoint(int var0, Connection var1) throws interbase.interclient.IBException, java.sql.SQLException {
      return new IBSavepoint(var0, var1);
   }
}
