package interbase.interclient;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

public class DataSourceObject extends DataSourcePropertiesObject implements ObjectFactory {
   // $FF: synthetic field
   static Class class$interbase$interclient$DataSource;

   public Object getObjectInstance(Object var1, Name var2, Context var3, Hashtable var4) throws Exception {
      interbase.interclient.DataSource var5;
      try {
         if (this.setRef(var1) && this.ref_.getClassName().equals((class$interbase$interclient$DataSource == null ? (class$interbase$interclient$DataSource = class$("interbase.interclient.DataSource")) : class$interbase$interclient$DataSource).getName())) {
            var5 = new interbase.interclient.DataSource();
            this.getDataSourcePropertiesFromRef(var5);
            interbase.interclient.DataSource var6 = var5;
            return var6;
         }

         this.ref_ = null;
         var5 = null;
      } finally {
         this.ref_ = null;
      }

      return var5;
   }

   Reference getReference(DataSource var1) throws NamingException {
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
