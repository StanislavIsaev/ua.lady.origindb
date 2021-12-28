package interbase.interclient;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public final class CallableStatement extends PreparedStatement implements java.sql.CallableStatement {
   CallableStatement(Connection var1, String var2) throws SQLException {
      super(var1, var2, var1.getAttachmentSQLDialect());
   }

   public synchronized void registerOutParameter(int var1, int var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__out_parameters__);
   }

   public synchronized void registerOutParameter(int var1, int var2, int var3) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__out_parameters__);
   }

   public synchronized boolean wasNull() throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__out_parameters__);
   }

   public synchronized String getString(int var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__out_parameters__);
   }

   public synchronized boolean getBoolean(int var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__out_parameters__);
   }

   public synchronized byte getByte(int var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__out_parameters__);
   }

   public synchronized short getShort(int var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__out_parameters__);
   }

   public synchronized int getInt(int var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__out_parameters__);
   }

   public synchronized long getLong(int var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__out_parameters__);
   }

   public synchronized float getFloat(int var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__out_parameters__);
   }

   public synchronized double getDouble(int var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__out_parameters__);
   }

   /** @deprecated */
   public synchronized BigDecimal getBigDecimal(int var1, int var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__out_parameters__);
   }

   public synchronized byte[] getBytes(int var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__out_parameters__);
   }

   public synchronized Date getDate(int var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__out_parameters__);
   }

   public synchronized Time getTime(int var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__out_parameters__);
   }

   public synchronized Timestamp getTimestamp(int var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__out_parameters__);
   }

   public synchronized Object getObject(int var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__out_parameters__);
   }

   public synchronized BigDecimal getBigDecimal(int var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Object getObject(int var1, Map var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Ref getRef(int var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Blob getBlob(int var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Clob getClob(int var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized java.sql.Array getArray(int var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Date getDate(int var1, Calendar var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Time getTime(int var1, Calendar var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Timestamp getTimestamp(int var1, Calendar var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void registerOutParameter(int var1, int var2, String var3) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void registerOutParameter(String var1, int var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void registerOutParameter(String var1, int var2, int var3) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void registerOutParameter(String var1, int var2, String var3) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized URL getURL(int var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setURL(String var1, URL var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setNull(String var1, int var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setBoolean(String var1, boolean var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setByte(String var1, byte var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setShort(String var1, short var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setInt(String var1, int var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setLong(String var1, long var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setFloat(String var1, float var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setDouble(String var1, double var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setBigDecimal(String var1, BigDecimal var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setString(String var1, String var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setBytes(String var1, byte[] var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setDate(String var1, Date var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setTime(String var1, Time var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setTimestamp(String var1, Timestamp var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setAsciiStream(String var1, InputStream var2, int var3) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setBinaryStream(String var1, InputStream var2, int var3) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setObject(String var1, Object var2, int var3, int var4) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setObject(String var1, Object var2, int var3) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setObject(String var1, Object var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setCharacterStream(String var1, Reader var2, int var3) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setDate(String var1, Date var2, Calendar var3) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setTime(String var1, Time var2, Calendar var3) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setTimestamp(String var1, Timestamp var2, Calendar var3) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized void setNull(String var1, int var2, String var3) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized String getString(String var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized boolean getBoolean(String var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized byte getByte(String var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized short getShort(String var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized int getInt(String var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized long getLong(String var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized float getFloat(String var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized double getDouble(String var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized byte[] getBytes(String var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Date getDate(String var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Time getTime(String var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Timestamp getTimestamp(String var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Object getObject(String var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized BigDecimal getBigDecimal(String var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Object getObject(String var1, Map var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Ref getRef(String var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Blob getBlob(String var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Clob getClob(String var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized java.sql.Array getArray(String var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Date getDate(String var1, Calendar var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Time getTime(String var1, Calendar var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized Timestamp getTimestamp(String var1, Calendar var2) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   public synchronized URL getURL(String var1) throws SQLException {
      throw new DriverNotCapableException(interbase.interclient.ErrorKey.driverNotCapable__jdbc2_not_yet_supported__);
   }

   @Override
   public RowId getRowId(int parameterIndex) throws SQLException {
      return null;
   }

   @Override
   public RowId getRowId(String parameterName) throws SQLException {
      return null;
   }

   @Override
   public void setRowId(String parameterName, RowId x) throws SQLException {

   }

   @Override
   public void setNString(String parameterName, String value) throws SQLException {

   }

   @Override
   public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {

   }

   @Override
   public void setNClob(String parameterName, NClob value) throws SQLException {

   }

   @Override
   public void setClob(String parameterName, Reader reader, long length) throws SQLException {

   }

   @Override
   public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {

   }

   @Override
   public void setNClob(String parameterName, Reader reader, long length) throws SQLException {

   }

   @Override
   public NClob getNClob(int parameterIndex) throws SQLException {
      return null;
   }

   @Override
   public NClob getNClob(String parameterName) throws SQLException {
      return null;
   }

   @Override
   public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {

   }

   @Override
   public SQLXML getSQLXML(int parameterIndex) throws SQLException {
      return null;
   }

   @Override
   public SQLXML getSQLXML(String parameterName) throws SQLException {
      return null;
   }

   @Override
   public String getNString(int parameterIndex) throws SQLException {
      return null;
   }

   @Override
   public String getNString(String parameterName) throws SQLException {
      return null;
   }

   @Override
   public Reader getNCharacterStream(int parameterIndex) throws SQLException {
      return null;
   }

   @Override
   public Reader getNCharacterStream(String parameterName) throws SQLException {
      return null;
   }

   @Override
   public Reader getCharacterStream(int parameterIndex) throws SQLException {
      return null;
   }

   @Override
   public Reader getCharacterStream(String parameterName) throws SQLException {
      return null;
   }

   @Override
   public void setBlob(String parameterName, Blob x) throws SQLException {

   }

   @Override
   public void setClob(String parameterName, Clob x) throws SQLException {

   }

   @Override
   public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {

   }

   @Override
   public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {

   }

   @Override
   public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {

   }

   @Override
   public void setAsciiStream(String parameterName, InputStream x) throws SQLException {

   }

   @Override
   public void setBinaryStream(String parameterName, InputStream x) throws SQLException {

   }

   @Override
   public void setCharacterStream(String parameterName, Reader reader) throws SQLException {

   }

   @Override
   public void setNCharacterStream(String parameterName, Reader value) throws SQLException {

   }

   @Override
   public void setClob(String parameterName, Reader reader) throws SQLException {

   }

   @Override
   public void setBlob(String parameterName, InputStream inputStream) throws SQLException {

   }

   @Override
   public void setNClob(String parameterName, Reader reader) throws SQLException {

   }

   @Override
   public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
      return null;
   }

   @Override
   public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
      return null;
   }
}
