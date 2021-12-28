package interbase.interclient;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.Reference;
import java.util.Hashtable;

public class CPDSObject extends DataSourcePropertiesObject {
   // $FF: synthetic field
   static Class class$interbase$interclient$JdbcConnectionFactory;

   public Object getObjectInstance(Object var1, Name var2, Context var3, Hashtable var4) throws Exception {
      interbase.interclient.JdbcConnectionFactory var6;
      try {
         interbase.interclient.JdbcConnectionFactory var5;
         if (!this.setRef(var1) || !this.ref_.getClassName().equals((class$interbase$interclient$JdbcConnectionFactory == null ? (class$interbase$interclient$JdbcConnectionFactory = class$("interbase.interclient.JdbcConnectionFactory")) : class$interbase$interclient$JdbcConnectionFactory).getName())) {
            var5 = null;
            return var5;
         }

         var5 = new interbase.interclient.JdbcConnectionFactory();
         this.getDataSourcePropertiesFromRef(var5);
         var6 = var5;
      } finally {
         this.ref_ = null;
      }

      return var6;
   }

   Reference getReference(JdbcConnectionFactory var1) throws NamingException {
      this.ref_ = new Reference(var1.getClass().getName(), this.getClass().getName(), (String)null);
      this.setDataSourcePropertiesForRef(var1);
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
