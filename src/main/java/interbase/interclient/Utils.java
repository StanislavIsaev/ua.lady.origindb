package interbase.interclient;

final class Utils {
   static String getMessage(Exception var0) {
      return var0.getMessage() == null ? "" : var0.getMessage();
   }

   static java.sql.SQLException accumulateSQLExceptions(java.sql.SQLException var0, java.sql.SQLException var1) {
      if (var0 == null) {
         return var1;
      } else {
         var0.setNextException(var1);
         return var0;
      }
   }

   static String getSQLTypeName(int var0) {
      switch(var0) {
      case -7:
         return "BIT";
      case -6:
         return "TINYINT";
      case -5:
         return "BIGINT";
      case -4:
         return "LONGVARBINARY";
      case -3:
         return "VARBINARY";
      case -2:
         return "BINARY";
      case -1:
         return "LONGVARCHAR";
      case 0:
         return "NULL";
      case 1:
         return "CHAR";
      case 2:
         return "NUMERIC";
      case 3:
         return "DECIMAL";
      case 4:
         return "INTEGER";
      case 5:
         return "SMALLINT";
      case 6:
         return "FLOAT";
      case 7:
         return "REAL";
      case 8:
         return "DOUBLE";
      case 12:
         return "VARCHAR";
      case 91:
         return "DATE";
      case 92:
         return "TIME";
      case 93:
         return "TIMESTAMP";
      case 1111:
         return "OTHER";
      default:
         return null;
      }
   }
}
