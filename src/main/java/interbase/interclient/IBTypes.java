package interbase.interclient;

final class IBTypes {
   static final int NULL_TYPE__ = 0;
   static final int SMALLINT__ = 1;
   static final int INTEGER__ = 2;
   static final int FLOAT__ = 3;
   static final int DOUBLE__ = 4;
   static final int NUMERIC_DOUBLE__ = 5;
   static final int NUMERIC_INTEGER__ = 6;
   static final int NUMERIC_SMALLINT__ = 7;
   static final int CHAR__ = 8;
   static final int VARCHAR__ = 9;
   static final int CLOB__ = 10;
   static final int DATE__ = 11;
   static final int BLOB__ = 12;
   static final int ARRAY__ = 14;
   static final int SQLDATE__ = 15;
   static final int TIME__ = 16;
   static final int NUMERIC_INT64__ = 17;
   static final int DECIMAL_INT64__ = 18;
   static final int DECIMAL_INTEGER__ = 19;
   static final int VARCHAR_STRING__ = 20;
   static final int SQLBOOLEAN__ = 21;
   static final int SMALLINT_PRECISION__ = 5;
   static final int INTEGER_PRECISION__ = 10;
   static final int FLOAT_PRECISION__ = 7;
   static final int DOUBLE_PRECISION__ = 15;
   static final int NUMERIC_DOUBLE_PRECISION__ = 15;
   static final int NUMERIC_INTEGER_PRECISION__ = 10;
   static final int NUMERIC_SMALLINT_PRECISION__ = 5;
   static final int DATE_PRECISION__ = 19;
   static final int SQLDATE_PRECISION__ = 10;
   static final int TIME_PRECISION__ = 8;
   static final int NUMERIC_INT64_PRECISION__ = 19;
   static final int DECIMAL_SMALLINT_PRECISION__ = 5;
   static final int DECIMAL_INTEGER_PRECISION__ = 10;
   static final int DECIMAL_INT64_PRECISION__ = 19;
   static final int TYPE_NAME_PRECISION__ = 16;
   // $FF: synthetic field
   static Class class$java$lang$String;
   // $FF: synthetic field
   static Class class$java$sql$Blob;
   // $FF: synthetic field
   static Class class$java$sql$Array;
   // $FF: synthetic field
   static Class class$java$sql$Timestamp;
   // $FF: synthetic field
   static Class class$java$lang$Boolean;
   // $FF: synthetic field
   static Class class$java$lang$Short;
   // $FF: synthetic field
   static Class class$java$math$BigDecimal;
   // $FF: synthetic field
   static Class class$java$sql$Date;
   // $FF: synthetic field
   static Class class$java$sql$Time;
   // $FF: synthetic field
   static Class class$java$lang$Double;
   // $FF: synthetic field
   static Class class$java$lang$Float;
   // $FF: synthetic field
   static Class class$java$lang$Integer;

   static boolean isNumeric(int var0) {
      switch(var0) {
      case 5:
      case 6:
      case 7:
      case 17:
      case 18:
      case 19:
         return true;
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 16:
      default:
         return false;
      }
   }

   static int getSQLType(int var0) {
      switch(var0) {
      case 0:
         return 0;
      case 1:
         return 5;
      case 2:
         return 4;
      case 3:
         return 7;
      case 4:
         return 8;
      case 5:
      case 6:
      case 7:
      case 17:
         return 2;
      case 8:
         return 1;
      case 9:
      case 20:
         return 12;
      case 10:
         return -1;
      case 11:
         return 93;
      case 12:
         return -4;
      case 13:
      default:
         return 0;
      case 14:
         return 1111;
      case 15:
         return 91;
      case 16:
         return 92;
      case 18:
      case 19:
         return 3;
      case 21:
         return -7;
      }
   }

   static String getIBTypeName(int var0) {
      switch(var0) {
      case 0:
         return "";
      case 1:
         return "SMALLINT";
      case 2:
         return "INTEGER";
      case 3:
         return "FLOAT";
      case 4:
         return "DOUBLE PRECISION";
      case 5:
      case 6:
      case 7:
      case 17:
         return "NUMERIC";
      case 8:
         return "CHAR";
      case 9:
      case 20:
         return "VARCHAR";
      case 10:
      case 12:
         return "BLOB";
      case 11:
         return "TIMESTAMP";
      case 13:
      default:
         return "";
      case 14:
         return "ARRAY";
      case 15:
         return "DATE";
      case 16:
         return "TIME";
      case 18:
      case 19:
         return "DECIMAL";
      case 21:
         return "BOOLEAN";
      }
   }

   static final int getResultTypes(int var0, XSQLVAR var1) throws java.sql.SQLException {
      switch(var0 & -2) {
      case 448:
         return 9;
      case 452:
         return 8;
      case 480:
         if (var1.sqlscale == 0) {
            return 4;
         }

         return 5;
      case 482:
         return 3;
      case 496:
         if (var1.sqlscale == 0) {
            return 2;
         } else {
            if (var1.sqlsubtype == 2) {
               return 19;
            }

            return 6;
         }
      case 500:
         if (var1.sqlscale == 0) {
            return 1;
         }

         return 7;
      case 510:
         return 11;
      case 520:
         if (var1.sqlsubtype == 1) {
            return 10;
         }

         return 12;
      case 530:
         return 4;
      case 540:
         return 14;
      case 550:
      default:
         throw new interbase.interclient.BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 10017);
      case 560:
         return 16;
      case 570:
         return 15;
      case 580:
         if (var1.sqlsubtype == 2) {
            return 18;
         }

         return 17;
      case 590:
         return 21;
      case 600:
         return 20;
      }
   }

   static final int getDataType(short var0, short var1, short var2) {
      if (var2 < 0) {
         switch(var0) {
         case 7:
         case 8:
         case 16:
         case 27:
            if (var1 == 2) {
               return 3;
            }

            return 2;
         }
      }

      switch(var0) {
      case 7:
         return 5;
      case 8:
         return 4;
      case 9:
         return 1111;
      case 10:
         return 6;
      case 11:
      case 27:
         return 8;
      case 12:
         return 91;
      case 13:
         return 92;
      case 14:
         return 1;
      case 16:
         if (var1 == 2) {
            return 3;
         }

         return 2;
      case 17:
         return -7;
      case 35:
         return 93;
      case 37:
         return 12;
      case 261:
         if (var1 < 0) {
            return 2004;
         } else {
            if (var1 == 1) {
               return 12;
            }

            return -4;
         }
      default:
         return 0;
      }
   }

   static final int getIBType(short var0, short var1, short var2) {
      if (var2 < 0) {
         switch(var0) {
         case 7:
            return 7;
         case 8:
            if (var1 == 2) {
               return 19;
            }

            return 6;
         case 16:
            if (var1 == 2) {
               return 18;
            }

            return 17;
         case 27:
            return 5;
         }
      }

      switch(var0) {
      case 7:
         return 1;
      case 8:
         return 2;
      case 9:
         return 14;
      case 10:
         return 3;
      case 11:
      case 27:
         return 4;
      case 12:
         return 15;
      case 13:
         return 16;
      case 14:
         return 8;
      case 16:
         if (var1 == 2) {
            return 18;
         }

         return 17;
      case 17:
         return 21;
      case 35:
         return 11;
      case 37:
         return 9;
      case 261:
         if (var1 == 1) {
            return 10;
         }

         return 12;
      default:
         return 0;
      }
   }

   static final short getRadix(int var0) {
      switch(var0) {
      case 0:
      case 1:
      case 12:
      case 1111:
         return 0;
      default:
         return 10;
      }
   }

   static final int getPrecision(XSQLVAR var0, int var1) {
      if (var0.sqlPrecision != 0) {
         return var0.sqlPrecision;
      } else {
         switch(var1) {
         case 1:
         case 7:
            return 5;
         case 2:
         case 6:
         case 19:
            return 10;
         case 3:
            return 7;
         case 4:
         case 5:
            return 15;
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         default:
            return 0;
         case 17:
         case 18:
            return 19;
         }
      }
   }

   static final int getPrecision(int var0, int var1) {
      switch(var0) {
      case 1:
      case 7:
         return 5;
      case 2:
      case 6:
      case 19:
         return 10;
      case 3:
         return 7;
      case 4:
      case 5:
         return 15;
      case 8:
      case 9:
      case 20:
         return var1;
      case 10:
      case 12:
      case 13:
      case 14:
      default:
         return 0;
      case 11:
         return 19;
      case 15:
         return 10;
      case 16:
         return 8;
      case 17:
      case 18:
         return 19;
      case 21:
         return 1;
      }
   }

   static final String getClassName(int var0) throws java.sql.SQLException {
      switch(var0) {
      case 1:
         return (class$java$lang$Short == null ? (class$java$lang$Short = class$("java.lang.Short")) : class$java$lang$Short).getName();
      case 2:
         return (class$java$lang$Integer == null ? (class$java$lang$Integer = class$("java.lang.Integer")) : class$java$lang$Integer).getName();
      case 3:
         return (class$java$lang$Float == null ? (class$java$lang$Float = class$("java.lang.Float")) : class$java$lang$Float).getName();
      case 4:
      case 5:
         return (class$java$lang$Double == null ? (class$java$lang$Double = class$("java.lang.Double")) : class$java$lang$Double).getName();
      case 6:
      case 19:
         return (class$java$math$BigDecimal == null ? (class$java$math$BigDecimal = class$("java.math.BigDecimal")) : class$java$math$BigDecimal).getName();
      case 7:
      case 17:
         return (class$java$math$BigDecimal == null ? (class$java$math$BigDecimal = class$("java.math.BigDecimal")) : class$java$math$BigDecimal).getName();
      case 8:
      case 9:
      case 10:
      case 20:
         return (class$java$lang$String == null ? (class$java$lang$String = class$("java.lang.String")) : class$java$lang$String).getName();
      case 11:
         return (class$java$sql$Timestamp == null ? (class$java$sql$Timestamp = class$("java.sql.Timestamp")) : class$java$sql$Timestamp).getName();
      case 12:
         return (class$java$sql$Blob == null ? (class$java$sql$Blob = class$("java.sql.Blob")) : class$java$sql$Blob).getName();
      case 13:
      default:
         throw new interbase.interclient.BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 115);
      case 14:
         return (class$java$sql$Array == null ? (class$java$sql$Array = class$("java.sql.Array")) : class$java$sql$Array).getName();
      case 15:
         return (class$java$sql$Date == null ? (class$java$sql$Date = class$("java.sql.Date")) : class$java$sql$Date).getName();
      case 16:
         return (class$java$sql$Time == null ? (class$java$sql$Time = class$("java.sql.Time")) : class$java$sql$Time).getName();
      case 18:
         return (class$java$math$BigDecimal == null ? (class$java$math$BigDecimal = class$("java.math.BigDecimal")) : class$java$math$BigDecimal).getName();
      case 21:
         return (class$java$lang$Boolean == null ? (class$java$lang$Boolean = class$("java.lang.Boolean")) : class$java$lang$Boolean).getName();
      }
   }

   static final int getColumnSize(int var0, int var1) {
      switch(var0) {
      case 1:
      case 7:
         return 5;
      case 2:
      case 6:
      case 19:
         return 10;
      case 3:
         return 7;
      case 4:
      case 5:
         return 15;
      case 8:
      case 9:
      case 20:
         return var1;
      case 10:
      case 12:
      case 13:
      case 14:
      default:
         return 0;
      case 11:
         return 24;
      case 15:
         return 10;
      case 16:
         return 8;
      case 17:
      case 18:
         return 19;
      case 21:
         return 1;
      }
   }

   static final int getIBArrayPrecision(int var0, int var1) throws java.sql.SQLException {
      switch(var0) {
      case 7:
         return 5;
      case 8:
         return 10;
      case 9:
      case 18:
      case 19:
      case 20:
      case 21:
      case 22:
      case 23:
      case 24:
      case 25:
      case 26:
      case 28:
      case 29:
      case 30:
      case 31:
      case 32:
      case 33:
      case 34:
      case 36:
      case 39:
      case 40:
      case 41:
      case 42:
      case 43:
      case 44:
      case 45:
      default:
         throw new interbase.interclient.BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 10026);
      case 10:
         return 7;
      case 11:
         return 15;
      case 12:
         return 10;
      case 13:
         return 8;
      case 14:
      case 15:
      case 37:
      case 38:
         return var1;
      case 16:
         return 19;
      case 17:
         return 1;
      case 27:
         return 15;
      case 35:
         return 19;
      }
   }

   static final int getIBArrayType(int var0, int var1, int var2) throws java.sql.SQLException {
      if (var1 < 0) {
         switch(var0) {
         case 7:
            return 7;
         case 8:
            if (var2 == 2) {
               return 19;
            }

            return 6;
         case 16:
            if (var2 == 2) {
               return 18;
            }

            return 17;
         case 27:
            return 5;
         }
      }

      switch(var0) {
      case 7:
         if (var1 == 0) {
            return 1;
         }

         return 7;
      case 8:
         if (var1 == 0) {
            return 2;
         }

         return 6;
      case 9:
      case 18:
      case 19:
      case 20:
      case 21:
      case 22:
      case 23:
      case 24:
      case 25:
      case 26:
      case 28:
      case 29:
      case 30:
      case 31:
      case 32:
      case 33:
      case 34:
      case 36:
      case 39:
      case 40:
      case 41:
      case 42:
      case 43:
      case 44:
      case 45:
      default:
         throw new BugCheckException(interbase.interclient.ErrorKey.bugCheck__0__, 10017);
      case 10:
         return 3;
      case 11:
         return 4;
      case 12:
         return 91;
      case 13:
         return 16;
      case 14:
      case 15:
         return 8;
      case 16:
         if (var2 == 1) {
            return 17;
         }

         return 18;
      case 17:
         return 21;
      case 27:
         if (var1 == 0) {
            return 4;
         }

         return 5;
      case 35:
         return 11;
      case 37:
      case 38:
         return 9;
      }
   }

   public static int getCharacterLength(XSQLVAR var0) throws java.sql.SQLException {
      if ((var0.sqltype & -2) != 452 && (var0.sqltype & -2) != 448) {
         return var0.sqllen;
      } else {
         int var1 = var0.sqlsubtype % 128;
         if (interbase.interclient.CharacterEncodings.isOneByteCharSet(var1)) {
            return var0.sqllen;
         } else if (interbase.interclient.CharacterEncodings.isTwoByteCharSet(var1)) {
            return var0.sqllen / 2;
         } else if (CharacterEncodings.isThreeByteCharSet(var1)) {
            return var0.sqllen / 3;
         } else {
            throw new UnsupportedCharacterSetException(128, var1);
         }
      }
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }
}
