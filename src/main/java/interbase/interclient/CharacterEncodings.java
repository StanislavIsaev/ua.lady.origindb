package interbase.interclient;

import java.util.Hashtable;

public final class CharacterEncodings {
   static final int NONE__ = 0;
   private static final int OCTETS__ = 1;
   private static final int ASCII__ = 2;
   private static final int UNICODE_FSS__ = 3;
   private static final int SJIS_0208__ = 5;
   private static final int EUCJ_0208__ = 6;
   private static final int DOS437__ = 10;
   private static final int DOS850__ = 11;
   private static final int DOS865__ = 12;
   private static final int DOS860__ = 13;
   private static final int DOS863__ = 14;
   private static final int NEXT__ = 19;
   private static final int ISO8859_1__ = 21;
   private static final int ISO8859_2__ = 22;
   private static final int ISO8859_15__ = 39;
   private static final int KSC_5601__ = 44;
   private static final int DOS852__ = 45;
   private static final int DOS857__ = 46;
   private static final int DOS861__ = 47;
   private static final int CYRL__ = 50;
   private static final int WIN1250__ = 51;
   private static final int WIN1251__ = 52;
   private static final int WIN1252__ = 53;
   private static final int WIN1253__ = 54;
   private static final int WIN1254__ = 55;
   private static final int BIG_5__ = 56;
   private static final int GB_2312__ = 57;
   private static final int KOI8_R__ = 58;
   private static final int DYNAMIC__ = 127;
   public static final String NONE = "NONE";
   public static final String _8859_1 = "8859_1";
   public static final String _8859_2 = "8859_2";
   public static final String _8859_15 = "8859_15";
   public static final String _KOI8_R = "KOI8_R";
   public static final String Big5 = "Big5";
   public static final String MS950 = "MS950";
   public static final String Cp1250 = "Cp1250";
   public static final String Cp1251 = "Cp1251";
   public static final String Cp1252 = "Cp1252";
   public static final String Cp1253 = "Cp1253";
   public static final String Cp1254 = "Cp1254";
   public static final String Cp437 = "Cp437";
   public static final String Cp850 = "Cp850";
   public static final String Cp852 = "Cp852";
   public static final String Cp857 = "Cp857";
   public static final String Cp860 = "Cp860";
   public static final String Cp861 = "Cp861";
   public static final String Cp863 = "Cp863";
   public static final String Cp865 = "Cp865";
   public static final String EUCJIS = "EUCJIS";
   public static final String GB2312 = "GB2312";
   public static final String GBK = "GBK";
   public static final String KSC5601 = "KSC5601";
   public static final String MS949 = "MS949";
   public static final String SJIS = "SJIS";
   public static final String MS932 = "MS932";
   public static final String UTF8 = "UTF8";
   public static final String DYNAMIC = "DYNAMIC";
   private static final String[] supportedEncodings__ = new String[]{"NONE", "8859_1", "8859_2", "8859_15", "Big5", "Cp1250", "Cp1251", "Cp1252", "Cp1253", "Cp1254", "Cp437", "Cp850", "Cp852", "Cp857", "Cp860", "Cp861", "Cp863", "Cp865", "EUCJIS", "GB2312", "GBK", "KSC5601", "KOI8_R", "MS932", "MS949", "MS950", "SJIS", "UTF8"};
   private static Hashtable ianaToIBCharNameTable__ = new Hashtable();

   static final String getCharacterSetName(int var0) {
      switch(var0) {
      case 0:
         return null;
      case 1:
         return null;
      case 2:
         return "Cp437";
      case 3:
         return "UTF8";
      case 4:
      case 7:
      case 8:
      case 9:
      case 15:
      case 16:
      case 17:
      case 18:
      case 20:
      case 23:
      case 24:
      case 25:
      case 26:
      case 27:
      case 28:
      case 29:
      case 30:
      case 31:
      case 32:
      case 33:
      case 34:
      case 35:
      case 36:
      case 37:
      case 38:
      case 40:
      case 41:
      case 42:
      case 43:
      case 48:
      case 49:
      default:
         return null;
      case 5:
         return "SJIS";
      case 6:
         return "EUCJIS";
      case 10:
         return "Cp437";
      case 11:
         return "Cp850";
      case 12:
         return "Cp865";
      case 13:
         return "Cp860";
      case 14:
         return "Cp863";
      case 19:
         return null;
      case 21:
         return "8859_1";
      case 22:
         return "8859_2";
      case 39:
         return "8859_15";
      case 44:
         return "KSC5601";
      case 45:
         return "Cp852";
      case 46:
         return "Cp857";
      case 47:
         return "Cp861";
      case 50:
         return null;
      case 51:
         return "Cp1250";
      case 52:
         return "Cp1251";
      case 53:
         return "Cp1252";
      case 54:
         return "Cp1253";
      case 55:
         return "Cp1254";
      case 56:
         return "Big5";
      case 57:
         return "GBK";
      case 58:
         return "KOI8_R";
      }
   }

   public static String getInterBaseCharacterSetName(String var0) throws UnsupportedCharacterSetException {
      String var1 = (String)ianaToIBCharNameTable__.get(var0);
      if (var1 == null) {
         throw new UnsupportedCharacterSetException(ErrorKey.unsupportedCharacterSet__0__, var0);
      } else {
         return var1;
      }
   }

   public static String[] getSupportedEncodings() {
      return supportedEncodings__;
   }

   static boolean isOneByteCharSet(int var0) {
      switch(var0) {
      case 0:
      case 1:
      case 2:
      case 3:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 19:
      case 21:
      case 22:
      case 39:
      case 45:
      case 46:
      case 47:
      case 50:
      case 51:
      case 52:
      case 53:
      case 54:
      case 55:
      case 58:
      case 127:
         return true;
      default:
         return false;
      }
   }

   static boolean isTwoByteCharSet(int var0) {
      switch(var0) {
      case 5:
      case 6:
      case 44:
      case 56:
      case 57:
         return true;
      default:
         return false;
      }
   }

   static boolean isThreeByteCharSet(int var0) {
      return var0 == 3;
   }

   static {
      ianaToIBCharNameTable__.put("NONE", "NONE");
      ianaToIBCharNameTable__.put("8859_1", "ISO8859_1");
      ianaToIBCharNameTable__.put("8859_2", "ISO8859_2");
      ianaToIBCharNameTable__.put("8859_15", "ISO8859_15");
      ianaToIBCharNameTable__.put("Big5", "BIG_5");
      ianaToIBCharNameTable__.put("MS950", "BIG_5");
      ianaToIBCharNameTable__.put("Cp1250", "WIN1250");
      ianaToIBCharNameTable__.put("Cp1251", "WIN1251");
      ianaToIBCharNameTable__.put("Cp1252", "WIN1252");
      ianaToIBCharNameTable__.put("Cp1253", "WIN1253");
      ianaToIBCharNameTable__.put("Cp1254", "WIN1254");
      ianaToIBCharNameTable__.put("Cp437", "DOS437");
      ianaToIBCharNameTable__.put("Cp850", "DOS850");
      ianaToIBCharNameTable__.put("Cp852", "DOS852");
      ianaToIBCharNameTable__.put("Cp857", "DOS857");
      ianaToIBCharNameTable__.put("Cp860", "DOS860");
      ianaToIBCharNameTable__.put("Cp861", "DOS861");
      ianaToIBCharNameTable__.put("Cp863", "DOS863");
      ianaToIBCharNameTable__.put("Cp865", "DOS865");
      ianaToIBCharNameTable__.put("EUCJIS", "EUCJ_0208");
      ianaToIBCharNameTable__.put("GB2312", "GB_2312");
      ianaToIBCharNameTable__.put("GBK", "GB_2312");
      ianaToIBCharNameTable__.put("KSC5601", "KSC_5601");
      ianaToIBCharNameTable__.put("MS949", "KSC_5601");
      ianaToIBCharNameTable__.put("KOI8_R", "KOI8R");
      ianaToIBCharNameTable__.put("SJIS", "SJIS_0208");
      ianaToIBCharNameTable__.put("MS932", "SJIS_0208");
      ianaToIBCharNameTable__.put("UTF8", "UNICODE_FSS");
   }
}
