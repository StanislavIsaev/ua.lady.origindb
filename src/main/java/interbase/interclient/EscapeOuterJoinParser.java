package interbase.interclient;

final class EscapeOuterJoinParser implements EscapeClauseParser {
   public synchronized String parse(String var1, int var2) throws interbase.interclient.BugCheckException {
      int var4 = var1.toUpperCase().indexOf("OJ");
      if (var4 == -1) {
         throw new BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 127);
      } else {
         return var1.substring(var4 + 2);
      }
   }
}
