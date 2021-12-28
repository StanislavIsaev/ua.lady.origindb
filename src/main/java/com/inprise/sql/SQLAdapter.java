package com.inprise.sql;

import java.sql.SQLException;

public interface SQLAdapter {
   int RIGHT_TRIM_STRINGS = 1;
   int SINGLE_INSTANCE_TIME = 2;
   int RESETABLE_STREAM = 3;

   boolean adapt(int var1, Object var2) throws SQLException;

   void revert(int var1) throws SQLException;
}
