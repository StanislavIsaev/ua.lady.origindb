package interbase.interclient;

import java.util.NoSuchElementException;

final class EscapeTimestampParser implements interbase.interclient.EscapeClauseParser {
   public synchronized String parse(String var1, int var2) throws java.sql.SQLException {
      StringBuffer var4 = new StringBuffer();
      interbase.interclient.EscapeLexer var5 = new interbase.interclient.EscapeLexer(var1, " \n\t\r'-:.");
      if (!var5.hasMoreTokens()) {
         throw new interbase.interclient.BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 121);
      } else {
         try {
            String var3 = var5.nextToken();
            if (!var3.toUpperCase().equals("TS")) {
               throw new BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 122);
            } else {
               var3 = var5.nextToken();
               if (!var3.equals("'")) {
                  throw new interbase.interclient.EscapeSyntaxException(interbase.interclient.ErrorKey.escapeSyntax__ts_0__, var1);
               } else {
                  String var6 = var5.nextToken();
                  var3 = var5.nextToken();
                  if (!var3.equals("-")) {
                     throw new interbase.interclient.EscapeSyntaxException(interbase.interclient.ErrorKey.escapeSyntax__ts_0__, var1);
                  } else {
                     String var7 = var5.nextToken();
                     var3 = var5.nextToken();
                     if (!var3.equals("-")) {
                        throw new interbase.interclient.EscapeSyntaxException(interbase.interclient.ErrorKey.escapeSyntax__ts_0__, var1);
                     } else {
                        String var8 = var5.nextToken();
                        String var9 = var5.nextToken();
                        var3 = var5.nextToken();
                        if (!var3.equals(":")) {
                           throw new interbase.interclient.EscapeSyntaxException(interbase.interclient.ErrorKey.escapeSyntax__ts_0__, var1);
                        } else {
                           String var10 = var5.nextToken();
                           var3 = var5.nextToken();
                           if (!var3.equals(":")) {
                              throw new interbase.interclient.EscapeSyntaxException(interbase.interclient.ErrorKey.escapeSyntax__ts_0__, var1);
                           } else {
                              String var11 = var5.nextToken();
                              var3 = var5.nextToken();
                              String var12 = "000";
                              if (var3.equals(".")) {
                                 var12 = var5.nextToken();
                                 var3 = var5.nextToken();
                              }

                              if (!var3.equals("'")) {
                                 throw new interbase.interclient.EscapeSyntaxException(interbase.interclient.ErrorKey.escapeSyntax__ts_0__, var1);
                              } else {
                                 var4.append('\'' + var6 + "-" + var7 + "-" + var8 + ' ' + var9 + ":" + var10 + ":" + var11 + "." + var12 + '\'');
                                 return var4.toString();
                              }
                           }
                        }
                     }
                  }
               }
            }
         } catch (NoSuchElementException var13) {
            throw new EscapeSyntaxException(interbase.interclient.ErrorKey.escapeSyntax__ts_0__, var1);
         }
      }
   }
}
