package dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest extends AbstractDAOTest {


    @DisplayName("Establishing connection")
    @Test
    void getNewConnection() throws SQLException {
        // given
        // when
        assertTrue(connection.isValid(1));
        assertFalse(connection.isClosed());
    }

    @DisplayName("Check if objects from Playground table fetched correct")
    @Test
    void getObjectsFromPlaygroundTable() throws SQLException {
        // given
        String[] expected = {"Xbox", "SonyPlaystation", "PC"};

        // when
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM playground")) {
            List<String> allPlaygrounds = new ArrayList<>();
            while (resultSet.next()) {
                String playground = resultSet.getString(2);
                allPlaygrounds.add(playground);
            }

            // then
            assertArrayEquals(expected, allPlaygrounds.toArray());
        }
    }

    @DisplayName("Check if all ids are incremented")
    @Test
    void checkOnId() throws SQLException {
        // given
        Long[] expected = {1L, 2L, 3L};

        // when
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM playground")) {
            List<Long> allIds = new ArrayList<>();
            while (resultSet.next()) {
                long playground = resultSet.getLong(1);
                allIds.add(playground);
            }

            // then
            assertArrayEquals(expected, allIds.toArray());
        }
    }


}
