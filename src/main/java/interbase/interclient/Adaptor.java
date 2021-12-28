package interbase.interclient;

public interface Adaptor {
   int RIGHT_TRIM_STRINGS = 1;
   int SINGLE_INSTANCE_TIME = 2;

   boolean adapt(int var1, Object var2) throws java.sql.SQLException;

   void revert(int var1) throws java.sql.SQLException;
}
