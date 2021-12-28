package interbase.interclient;

class UnsupportedCharacterEncodingException extends interbase.interclient.SQLException {
   private static final String className__ = "UnsupportedCharacterEncodingException";

   UnsupportedCharacterEncodingException(interbase.interclient.ErrorKey var1, String var2) {
      super("UnsupportedCharacterEncodingException", var1, (Object)var2);
   }

   UnsupportedCharacterEncodingException(int var1, int var2) {
      super("UnsupportedCharacterEncodingException", var1, (Object)String.valueOf(var2));
   }
}
