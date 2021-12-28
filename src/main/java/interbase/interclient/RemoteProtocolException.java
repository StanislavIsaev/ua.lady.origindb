package interbase.interclient;

public final class RemoteProtocolException extends interbase.interclient.SQLException {
   private static final int lastCodeUsed__ = 105;
   private static final String className__ = "RemoteProtocolException";

   RemoteProtocolException(interbase.interclient.ErrorKey var1, int var2) {
      super("RemoteProtocolException", var1, (Object)String.valueOf(var2));
   }

   RemoteProtocolException(interbase.interclient.ErrorKey var1) {
      super("RemoteProtocolException", var1);
   }

   RemoteProtocolException(int var1) {
      super("RemoteProtocolException", var1);
   }
}
