package interbase.interclient;

import java.util.NoSuchElementException;

final class EscapeDateParser implements EscapeClauseParser {
   public synchronized String parse(String var1, int var2) throws java.sql.SQLException {
      StringBuffer var4 = new StringBuffer();
      interbase.interclient.EscapeLexer var6 = new interbase.interclient.EscapeLexer(var1, " \n\t\r'-");
      if (!var6.hasMoreTokens()) {
         throw new interbase.interclient.BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 119);
      } else {
         try {
            String var3 = var6.nextToken();
            if (!var3.toUpperCase().equals("D")) {
               throw new BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 120);
            } else {
               var3 = var6.nextToken();
               if (!var3.equals("'")) {
                  throw new interbase.interclient.EscapeSyntaxException(interbase.interclient.ErrorKey.escapeSyntax__d_0__, var1);
               } else {
                  String var7 = var6.nextToken();
                  var3 = var6.nextToken();
                  if (!var3.equals("-")) {
                     throw new interbase.interclient.EscapeSyntaxException(interbase.interclient.ErrorKey.escapeSyntax__d_0__, var1);
                  } else {
                     String var8 = var6.nextToken();
                     var3 = var6.nextToken();
                     if (!var3.equals("-")) {
                        throw new interbase.interclient.EscapeSyntaxException(interbase.interclient.ErrorKey.escapeSyntax__d_0__, var1);
                     } else {
                        String var9 = var6.nextToken();
                        var3 = var6.nextToken();
                        if (!var3.equals("'")) {
                           throw new interbase.interclient.EscapeSyntaxException(interbase.interclient.ErrorKey.escapeSyntax__d_0__, var1);
                        } else {
                           var4.append('\'' + var8 + ' ' + var9 + ' ' + var7 + '\'');
                           return var4.toString();
                        }
                     }
                  }
               }
            }
         } catch (NoSuchElementException var10) {
            throw new EscapeSyntaxException(interbase.interclient.ErrorKey.escapeSyntax__d_0__, var1);
         }
      }
   }
}
