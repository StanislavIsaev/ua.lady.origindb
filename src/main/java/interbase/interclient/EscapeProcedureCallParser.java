package interbase.interclient;

import java.util.NoSuchElementException;

final class EscapeProcedureCallParser implements EscapeClauseParser {
   public synchronized String parse(String var1, int var2) throws java.sql.SQLException {
      EscapeLexer var3 = new EscapeLexer(var1);
      if (!var3.hasMoreTokens()) {
         throw new BugCheckException(ErrorKey.bugCheck__0__, 117);
      } else {
         try {
            String var4 = var3.nextToken();
            if (!var4.toUpperCase().equals("CALL")) {
               throw new BugCheckException(ErrorKey.bugCheck__0__, 118);
            } else {
               var4 = var3.nextToken();
               StringBuffer var5 = new StringBuffer();
               var5.append("                  " + var4);
               if (var3.hasMoreTokens()) {
                  var4 = var3.nextToken();
                  if (!var4.equals("(")) {
                     throw new EscapeSyntaxException(ErrorKey.escapeSyntax__call_0__, var1);
                  }

                  var5.append("(");
                  var4 = var3.nextToken();
                  var5.append(var4);
                  boolean var6 = false;

                  while(!var6) {
                     var4 = var3.nextToken();
                     if (var4.equals(")")) {
                        var6 = true;
                        var5.append(")");
                     } else {
                        var5.append(var4);
                     }
                  }
               }

               return var5.toString();
            }
         } catch (NoSuchElementException var7) {
            throw new EscapeSyntaxException(ErrorKey.escapeSyntax__call_0__, var1);
         }
      }
   }
}
