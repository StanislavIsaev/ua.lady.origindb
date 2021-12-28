package interbase.interclient;

public final class BadInstallationException extends interbase.interclient.SQLException {
   private static final String className__ = "BadInstallationException";

   BadInstallationException(interbase.interclient.ErrorKey var1, Object[] var2) {
      super("BadInstallationException", var1, var2);
   }

   BadInstallationException(interbase.interclient.ErrorKey var1) {
      super("BadInstallationException", var1);
   }
}
