package ua.lady.origindb;

import interbase.interclient.Driver;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;

class JdbcTest {

    private String url = "jdbc:interbase://localhost:3050/f:/downloads/prices_and gdb/PARFUM.GDB";
    private String password = "1";
    private String sysdba = "sysdba";

    @Test
    void jdbcUp() throws Exception {
        final Driver driver = new Driver();

        DriverManager.registerDriver(driver);
        System.out.println(Arrays.toString(driver.getCompatibleJREVersions()));
        System.out.println(driver.getJDBCNetProtocol());
//        DriverManager.drivers().forEach(System.out::println);
        try (Connection c = DriverManager.getConnection(url, sysdba, password);
             final Statement st = c.createStatement()) {

            st.execute("select * from FAST_JURNAL");
            try (ResultSet resultSet = st.getResultSet()) {
                resultSet.next();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    System.out.println(resultSet.getMetaData().getColumnName(i) + ": " + resultSet.getString(i));
                }
            }
        }
    }
}
