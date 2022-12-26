package dao;

import entities.Playground;
import entities.Station;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class PlaygroundDAOTest extends AbstractDAOTest {

    private static PlaygroundDAO playgroundDAO;

    @BeforeAll
    static void initPlayground() {
        playgroundDAO = new PlaygroundDAO(connection);
    }


    @DisplayName("Check on creating new playground")
    @Test
    void createNewPlayground() throws SQLException {
        Playground newPlayground = new Playground();
        newPlayground.setName(Station.NINTENDO);
        playgroundDAO.create(newPlayground);
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM playground")) {
            resultSet.next();
            int amountOfRows = resultSet.getInt(1);
            assertEquals(4, amountOfRows);
        }


    }
}