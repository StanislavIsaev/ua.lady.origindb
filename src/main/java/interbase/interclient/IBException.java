package interbase.interclient;

import java.io.InputStream;
import java.util.Properties;

class IBException extends java.sql.SQLException {
   protected int type;
   protected int intParam;
   protected String strParam;
   protected IBException next;
   private static final String MESSAGES = "isc_error_msg";
   private static Properties messages = new Properties();
   private static boolean initialized = false;
   // $FF: synthetic field
   static Class class$interbase$interclient$IBException;

   protected String getParam() {
      if (this.type != 5 && this.type != 2) {
         return this.type == 4 ? "" + this.intParam : "";
      } else {
         return this.strParam;
      }
   }

   int getIntParam() {
      return this.intParam;
   }

   IBException(int var1, int var2) {
      this.type = var1;
      this.intParam = var2;
   }

   IBException(int var1, String var2) {
      this.type = var1;
      this.strParam = var2;
   }

   IBException(int var1) {
      this.intParam = var1;
      this.type = 1;
   }

   IBException(String var1) {
      super(var1);
      this.type = 2;
   }

   protected void finalize() throws Throwable {
      this.next = null;
      this.strParam = null;
      super.finalize();
   }

   public int getErrorCode() {
      return this.getIbErrorCode();
   }

   int getIbErrorCode() {
      return this.intParam;
   }

   void setNext(IBException var1) {
      this.next = var1;
   }

   IBException getNext() {
      return this.next;
   }

   public String getMessage() {
      IBException var2 = this.next;
      String var1;
      if (this.type != 1 && this.type != 18) {
         var1 = super.getMessage();
      } else {
         IBMessage var3 = getMessage(this.intParam);
         int var4 = var3.getParamCount();

         for(int var5 = 0; var5 < var4 && var2 != null; ++var5) {
            var3.setParameter(var5, var2.getParam());
            var2 = var2.next;
         }

         var1 = var3.toString();
      }

      if (var2 != null) {
         var1 = var1 + "\n" + var2.getMessage();
         var2 = var2.next;
      }

      return var1;
   }

   public String getSQLState() {
      return ErrorKey.getIBSQLState();
   }

   private static void init() {
      Properties var0 = messages;
      synchronized(messages) {
         if (!initialized) {
            try {
               String var1 = "isc_error_msg.properties";
               InputStream var2 = (class$interbase$interclient$IBException == null ? (class$interbase$interclient$IBException = class$("interbase.interclient.IBException")) : class$interbase$interclient$IBException).getResourceAsStream(var1);
               if (messages == null) {
                  messages = new Properties();
               }

               messages.load(var2);
            } catch (Exception var9) {
               var9.printStackTrace();
            } finally {
               initialized = true;
            }

         }
      }
   }

   static IBMessage getMessage(int var0) {
      if (!initialized) {
         init();
      }

      return new IBMessage(messages.getProperty("" + var0, "Message for error code " + var0 + " not found in resource file."));
   }

   java.sql.SQLException getSQLExceptionFromIBE() {
      return new java.sql.SQLException(this.getMessage(), this.getSQLState(), this.getIbErrorCode());
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   static class IBMessage {
      private String template;
      private String[] params;

      public IBMessage(String var1) {
         this.template = "[interclient][interbase]" + var1;
         this.params = new String[this.getParamCount()];
      }

      protected void finalize() throws Throwable {
         this.params = null;
         this.template = null;
         super.finalize();
      }

      int getParamCount() {
         int var1 = 0;

         for(int var2 = 0; var2 < this.template.length(); ++var2) {
            if (this.template.charAt(var2) == '{') {
               ++var1;
            }
         }

         return var1;
      }

      void setParameter(int var1, String var2) {
         if (var1 < this.params.length) {
            this.params[var1] = var2;
         }

      }

      public String toString() {
         String var1 = this.template;

         for(int var2 = 0; var2 < this.params.length; ++var2) {
            String var3 = "{" + var2 + "}";
            int var4 = var1.indexOf(var3);
            if (var4 > 0 && var4 < var1.length()) {
               String var5 = var1.substring(0, var4);
               var5 = var5 + (this.params[var2] == null ? "" : this.params[var2]);
               var5 = var5 + var1.substring(var4 + var3.length());
               var1 = var5;
            }
         }

         return var1;
      }
   }
}
