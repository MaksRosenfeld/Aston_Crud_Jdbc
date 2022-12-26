package dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractDAOTest {

    static Connection connection;

    @BeforeAll
    static void initialize() throws SQLException {
        connection = DriverManager.getConnection(
                Driver.H2_TEST.getUrl(), "sa", "sa");
    }

    @AfterAll
    static void close() throws SQLException {
        connection.close();
    }

}
