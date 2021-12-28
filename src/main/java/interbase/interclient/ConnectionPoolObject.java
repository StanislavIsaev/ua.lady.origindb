package interbase.interclient;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.Reference;
import java.util.Hashtable;

public class ConnectionPoolObject extends DataSourcePropertiesObject {
   // $FF: synthetic field
   static Class class$interbase$interclient$ConnectionPoolModule;

   public Object getObjectInstance(Object var1, Name var2, Context var3, Hashtable var4) throws Exception {
      interbase.interclient.ConnectionPoolModule var6;
      try {
         interbase.interclient.ConnectionPoolModule var5;
         if (!this.setRef(var1) || !this.ref_.getClassName().equals((class$interbase$interclient$ConnectionPoolModule == null ? (class$interbase$interclient$ConnectionPoolModule = class$("interbase.interclient.ConnectionPoolModule")) : class$interbase$interclient$ConnectionPoolModule).getName())) {
            var5 = null;
            return var5;
         }

         var5 = new interbase.interclient.ConnectionPoolModule();
         var5.setDataSourceName(this.getString("dataSourceName"));
         var5.setLoginTimeout(Integer.parseInt(this.getString("loginTimeout")));
         var5.setMaxConnections(Integer.parseInt(this.getString("maxConnections")));
         var5.setMaxPool(Integer.parseInt(this.getString("maxPool")));
         var5.setMinPool(Integer.parseInt(this.getString("minPool")));
         var6 = var5;
      } finally {
         this.ref_ = null;
      }

      return var6;
   }

   Reference getReference(ConnectionPoolModule var1) throws NamingException {
      this.ref_ = new Reference(var1.getClass().getName(), this.getClass().getName(), (String)null);

      try {
         this.setString("dataSourceName", var1.getDataSourceName());
         this.setString("loginTimeout", String.valueOf(var1.getLoginTimeout()));
         this.setString("maxConnections", String.valueOf(var1.getMaxConnections()));
         this.setString("maxPool", String.valueOf(var1.getMaxPool()));
         this.setString("minPool", String.valueOf(var1.getMinPool()));
      } catch (java.sql.SQLException var3) {
         throw new NamingException(var3.getMessage());
      }

      return this.ref_;
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }
}
