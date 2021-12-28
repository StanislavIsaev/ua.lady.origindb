package interbase.interclient;

final class EscapeProcessor {
   private static final String EMPTY_STR__ = "";
   private boolean isEscapedProcedureCall_ = false;

   private synchronized String mapOneEscapeClause(boolean var1, String var2, EscapeClauseParserTable var3, int var4) throws java.sql.SQLException {
      EscapeLexer var5 = new EscapeLexer(var2);
      if (var2.length() > 0) {
         String var6 = var5.nextToken();
         String var7 = var6.toUpperCase();
         if (var1) {
            this.isEscapedProcedureCall_ = var7.equals("CALL");
         }

         EscapeClauseParser var8 = (EscapeClauseParser)var3.get(var7);
         if (var8 == null) {
            throw new EscapeSyntaxException(ErrorKey.escapeSyntax__unrecognized_keyword_0__, var2);
         } else {
            return var8.parse(var2, var4);
         }
      } else {
         return "";
      }
   }

   synchronized String doEscapeProcessing(String var1, int var2) throws java.sql.SQLException {
      boolean var3 = true;
      boolean var4 = true;
      boolean var5 = false;
      boolean var6 = false;
      String var7 = var1;
      EscapeClauseParserTable var8 = new EscapeClauseParserTable();
      boolean var9 = true;

      while(true) {
         var3 = true;
         var4 = true;
         int var15 = 0;
         var6 = false;
         StringBuffer var10 = new StringBuffer();
         EscapeLexer var11 = new EscapeLexer(var7);
         int var13 = var11.findNextNotInQuotedString('{');
         var6 = var13 > -1;
         if (!var6) {
            return var7;
         }

         while(var6) {
            int var14 = var11.findNextMatching('}');
            if (var14 == -1) {
               throw new EscapeSyntaxException(ErrorKey.escapeSyntax__no_closing_escape_delimeter_0__, var7);
            }

            var10.append(var7.substring(var15, var13));
            var10.append(' ');
            String var12 = new String(var7.substring(var13 + 1, var14));
            var10.append(this.mapOneEscapeClause(var9, var12, var8, var2));
            var10.append(' ');
            var9 = false;
            var15 = var14 + 1;
            var13 = var11.findNextNotInQuotedString('{');
            var6 = var13 > -1;
         }

         var10.append(var7.substring(var15));
         var7 = var10.toString();
      }
   }

   boolean isEscapedProcedureCall() {
      return this.isEscapedProcedureCall_;
   }
}
