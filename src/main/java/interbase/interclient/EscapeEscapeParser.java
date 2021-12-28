package interbase.interclient;

import java.util.NoSuchElementException;

final class EscapeEscapeParser implements EscapeClauseParser {
   public synchronized String parse(String var1, int var2) throws java.sql.SQLException {
      interbase.interclient.EscapeLexer var4 = new interbase.interclient.EscapeLexer(var1);
      if (!var4.hasMoreTokens()) {
         throw new interbase.interclient.BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 125);
      } else {
         try {
            String var3 = var4.nextToken();
            if (!var3.toUpperCase().equals("ESCAPE")) {
               throw new BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 126);
            } else {
               var3 = var4.nextToken();
               if (!var3.equals("'")) {
                  throw new interbase.interclient.EscapeSyntaxException(interbase.interclient.ErrorKey.escapeSyntax__escape__no_quote_0__, var1);
               } else {
                  var3 = var4.nextToken();
                  String var5 = "ESCAPE \"" + var3 + "\"";
                  var3 = var4.nextToken();
                  if (!var3.equals("'")) {
                     throw new interbase.interclient.EscapeSyntaxException(interbase.interclient.ErrorKey.escapeSyntax__escape__no_quote_0__, var1);
                  } else {
                     return var5;
                  }
               }
            }
         } catch (NoSuchElementException var6) {
            throw new EscapeSyntaxException(interbase.interclient.ErrorKey.escapeSyntax__escape_0__, var1);
         }
      }
   }
}
