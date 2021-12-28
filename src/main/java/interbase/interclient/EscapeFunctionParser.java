package interbase.interclient;

import java.util.NoSuchElementException;

final class EscapeFunctionParser implements EscapeClauseParser {
   static String[] userTokens = new String[]{"FN", "USER", "(", ")"};
   static String[] nowTokens = new String[]{"FN", "NOW", "(", ")"};
   static String[] curdateTokens = new String[]{"FN", "CURDATE", "(", ")"};
   static String[] ucaseTokens = new String[]{"FN", "UCASE", "("};
   static String[] lengthTokens = new String[]{"FN", "LENGTH", "("};
   static String[] subStringTokens = new String[]{"FN", "SUBSTRING", "("};
   static String[] lowerTokens = new String[]{"FN", "LCASE", "("};
   static String[] asciiValTokens = new String[]{"FN", "CHAR", "("};
   static String[] asciiCharTokens = new String[]{"FN", "ASCII", "("};
   static String escapeString = "escape ";

   private synchronized boolean matchStringToTokens(String var1, String[] var2, boolean var3) throws java.sql.SQLException {
      interbase.interclient.EscapeLexer var5 = new interbase.interclient.EscapeLexer(var1);
      if (!var5.hasMoreTokens()) {
         throw new interbase.interclient.BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 123);
      } else {
         for(int var6 = 0; var6 < var2.length; ++var6) {
            String var4;
            try {
               var4 = var5.nextToken();
            } catch (NoSuchElementException var8) {
               return false;
            }

            if (var4.toUpperCase().compareTo(var2[var6]) != 0) {
               return false;
            }
         }

         return !var5.hasMoreTokens() || !var3;
      }
   }

   public synchronized String parse(String var1, int var2) throws java.sql.SQLException {
      if (this.matchStringToTokens(var1, userTokens, true)) {
         return "USER";
      } else if (this.matchStringToTokens(var1, nowTokens, true)) {
         return "'NOW'";
      } else if (this.matchStringToTokens(var1, curdateTokens, true)) {
         return "'TODAY'";
      } else {
         int var4;
         if (this.matchStringToTokens(var1, lengthTokens, false)) {
            var4 = var1.indexOf("(");
            if (var4 == -1) {
               throw new interbase.interclient.BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 124);
            } else {
               return "STRLEN " + var1.substring(var4);
            }
         } else if (this.matchStringToTokens(var1, subStringTokens, false)) {
            var4 = var1.indexOf("(");
            if (var4 == -1) {
               throw new interbase.interclient.BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 124);
            } else {
               return "SUBSTR " + var1.substring(var4);
            }
         } else if (this.matchStringToTokens(var1, ucaseTokens, false)) {
            var4 = var1.indexOf("(");
            if (var4 == -1) {
               throw new interbase.interclient.BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 124);
            } else {
               return "UPPER " + var1.substring(var4);
            }
         } else if (this.matchStringToTokens(var1, ucaseTokens, false)) {
            var4 = var1.indexOf("(");
            if (var4 == -1) {
               throw new interbase.interclient.BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 124);
            } else {
               return "LOWER " + var1.substring(var4);
            }
         } else if (this.matchStringToTokens(var1, asciiValTokens, false)) {
            var4 = var1.indexOf("(");
            if (var4 == -1) {
               throw new interbase.interclient.BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 124);
            } else {
               return "CHAR " + var1.substring(var4);
            }
         } else if (this.matchStringToTokens(var1, asciiCharTokens, false)) {
            var4 = var1.indexOf("(");
            if (var4 == -1) {
               throw new BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 124);
            } else {
               return "ASCII " + var1.substring(var4);
            }
         } else {
            var4 = var1.toUpperCase().indexOf("FN");
            if (var4 == -1) {
               throw new EscapeSyntaxException(interbase.interclient.ErrorKey.escapeSyntax__fn_0__, var1);
            } else {
               return var1.substring(var4 + 2);
            }
         }
      }
   }
}
