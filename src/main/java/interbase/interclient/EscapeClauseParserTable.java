package interbase.interclient;

import java.util.Hashtable;

final class EscapeClauseParserTable extends Hashtable {
   public EscapeClauseParserTable() {
      super(8, 1.0F);
      this.put("CALL", new interbase.interclient.EscapeProcedureCallParser());
      this.put("?", new EscapeProcedureCallWithResultParser());
      this.put("D", new EscapeDateParser());
      this.put("T", new EscapeTimeParser());
      this.put("TS", new EscapeTimestampParser());
      this.put("FN", new EscapeFunctionParser());
      this.put("ESCAPE", new EscapeEscapeParser());
      this.put("OJ", new EscapeOuterJoinParser());
   }
}
