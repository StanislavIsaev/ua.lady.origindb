package interbase.interclient;

import java.io.IOException;

public final class BlobIOException extends IOException {
   private static final String className__ = "BlobIOException";

   BlobIOException(String var1, String var2) {
      super(Globals.getResource(var1, new Object[]{var2}) + Globals.getResource("0") + "BlobIOException");
   }

   BlobIOException(String var1) {
      super(Globals.getResource(var1) + Globals.getResource("0") + "BlobIOException");
   }
}
