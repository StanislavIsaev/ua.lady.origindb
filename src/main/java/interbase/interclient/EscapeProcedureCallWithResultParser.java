package interbase.interclient;

final class EscapeProcedureCallWithResultParser implements EscapeClauseParser {
   public synchronized String parse(String var1, int var2) throws java.sql.SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__escape__call_with_result__);
   }
}
