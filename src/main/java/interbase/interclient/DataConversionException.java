package interbase.interclient;

public class DataConversionException extends InvalidOperationException {
   DataConversionException(String var1, interbase.interclient.ErrorKey var2) {
      super(var1, var2);
   }

   DataConversionException(String var1, interbase.interclient.ErrorKey var2, Object var3) {
      super(var1, var2, var3);
   }
}
