package interbase.interclient;

import java.io.IOException;

interface XdrEnabled {
   int getSize();

   void read(interbase.interclient.XdrInputStream var1, int var2) throws IOException;

   void write(interbase.interclient.XdrOutputStream var1) throws IOException;
}
