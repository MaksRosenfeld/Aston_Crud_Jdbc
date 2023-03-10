package dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Savepoint;

public abstract class AbstractDAOTest {

    static Connection connection;
    static GameDAO gameDAO;
    static PlaygroundDAO playgroundDAO;
    static PlayDAO playDAO;

    @BeforeAll
    static void initialize() throws SQLException {
        connection = DriverManager.getConnection(
                Driver.H2_TEST.getUrl(), "sa", "sa");
        connection.setAutoCommit(false);
        gameDAO = new GameDAO(connection);
        playgroundDAO = new PlaygroundDAO(connection);
        playDAO = new PlayDAO(connection);
    }

    @AfterAll
    static void close() throws SQLException {
        connection.close();
    }

    @AfterEach
    void rollback() throws SQLException {
        connection.rollback();
    }



}
