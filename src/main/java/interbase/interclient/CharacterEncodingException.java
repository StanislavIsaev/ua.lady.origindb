package interbase.interclient;

public final class CharacterEncodingException extends interbase.interclient.SQLException {
   private static final String className__ = "CharacterEncodingException";

   CharacterEncodingException(interbase.interclient.ErrorKey var1, String var2) {
      super("CharacterEncodingException", var1, (Object)var2);
   }

   CharacterEncodingException(interbase.interclient.ErrorKey var1, int var2) {
      super("CharacterEncodingException", var1, (Object)String.valueOf(var2));
   }

   CharacterEncodingException(interbase.interclient.ErrorKey var1) {
      super("CharacterEncodingException", var1);
   }
}
