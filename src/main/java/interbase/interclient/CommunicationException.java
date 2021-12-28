package interbase.interclient;

public final class CommunicationException extends interbase.interclient.SQLException {
   private static final String className__ = "CommunicationException";

   CommunicationException(interbase.interclient.ErrorKey var1, String var2, String var3) {
      super("CommunicationException", var1, new Object[]{var2, var3});
   }

   CommunicationException(interbase.interclient.ErrorKey var1, Object var2) {
      super("CommunicationException", var1, var2);
   }

   CommunicationException(interbase.interclient.ErrorKey var1) {
      super("CommunicationException", var1);
   }

   CommunicationException(int var1) {
      super("CommunicationException", var1);
   }
}
