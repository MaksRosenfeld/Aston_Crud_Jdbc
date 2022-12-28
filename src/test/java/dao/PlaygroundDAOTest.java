package dao;

import entities.Playground;
import entities.Station;
import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class PlaygroundDAOTest extends AbstractDAOTest {


    @DisplayName("Check on creating new playground")
    @Test
    void createNewPlayground() throws SQLException {
        // given
        Playground alreadyExisted = Playground.builder()
                .withName(Station.PC).build();
        Playground newPlayground = Playground.builder()
                .withName(Station.NINTENDO).build();

        // when
        boolean hasNotToBeAdded = playgroundDAO.create(alreadyExisted);
        boolean hasToBeAdded = playgroundDAO.create(newPlayground);
        Playground playground = playgroundDAO.read(4);
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM playground")) {
            resultSet.next();
            int amountOfRows = resultSet.getInt(1);

            // then
            assertFalse(hasNotToBeAdded);
            assertTrue(hasToBeAdded);
            assertEquals(4, amountOfRows);
            assertEquals(playground.getName(), Station.NINTENDO);
        }
    }

    @DisplayName("Check on incorrect id")
    @Test
    void getAbsentPlayground() {
        // given

        // when
        Playground noSuchId = playgroundDAO.read(10);
        Playground negativeId = playgroundDAO.read(-5);

        // then
        assertNull(noSuchId);
        assertNull(negativeId);
    }

    @DisplayName("Updating the existing and non existing playground")
    @Test
    void updatingExistingAndNonExistingPlayground() throws SQLException {
        // given
        Playground updateExisting = Playground.builder()
                .withName(Station.NINTENDO)
                .withId(1).build();
        Playground updateNonExisting = Playground.builder()
                .withName(Station.PC)
                .withId(25)
                .build();

        // when
        boolean resultOnExisting = playgroundDAO.update(updateExisting);
        boolean resultOnNonExisting = playgroundDAO.update(updateNonExisting);
        Playground hasToBeUpdated = playgroundDAO.read(1);

        // then
        assertTrue(resultOnExisting);
        assertFalse(resultOnNonExisting);
        assertEquals(Station.NINTENDO, hasToBeUpdated.getName());
    }

    @DisplayName("Deleting non existing and existing playgroung")
    @Test
    void deletePlaygrounds() throws SQLException {
        // given
        int existedPlayground = 2;
        int nonExistedPlayground = 9;

        // when
        boolean deleteTrue = playgroundDAO.delete(existedPlayground);
        boolean deleteFalse = playgroundDAO.delete(nonExistedPlayground);

        // then
        assertFalse(deleteFalse);
        assertTrue(deleteTrue);

    }
}