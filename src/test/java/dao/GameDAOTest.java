package dao;

import entities.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class GameDAOTest extends AbstractDAOTest {

    private GameDAO gameDAO;

    @BeforeEach
    void initPlayground() throws SQLException {
        gameDAO = new GameDAO(connection);
    }

    @DisplayName("Add non existed and existed game")
    @Test
    void create() throws SQLException {
        // given
        Game nonExisted = Game.builder().withName("Mario").withExclusive(true).build();
        Game existedGame = Game.builder().withName("Gears of War 3").withExclusive(true).build();
        Game existedSmallLetters = Game.builder().withName("last of us").withExclusive(true).build();

        // when
        boolean nonExistedTrue = gameDAO.create(nonExisted);
        boolean existedFalse = gameDAO.create(existedGame);
        boolean existedWithMistakeFalse = gameDAO.create(existedSmallLetters);

        // then
        assertTrue(nonExistedTrue);
        assertFalse(existedFalse);
        assertFalse(existedWithMistakeFalse);
    }

    @DisplayName("Get non existed and existed game")
    @Test
    void read() {
        // given

        // when
        Game existedGame = gameDAO.read(3);
        Game nonExisted = gameDAO.read(25);

        // then
        assertEquals("Gears of War 3", existedGame.getName());
        assertNull(nonExisted);
    }

    @Test
    void update() throws SQLException {
        // given
        Game updatedGame = Game.builder().withId(1).withName("Mafia 2").build();
        Game gameWithoutId = Game.builder().withName("I am Game").build();

        // when
        boolean updatedTrue = gameDAO.update(updatedGame);
        boolean updatedFalse = gameDAO.update(gameWithoutId);

        // then
        assertTrue(updatedTrue);
        assertFalse(updatedFalse);

    }

    @Test
    void delete() throws SQLException {
        // give
        Game existedGame = Game.builder().withId(4).withName("Last of Us").build();
        Game wrongId = Game.builder().withId(1).withName("Last of Us").build();
        Game smallLetters = Game.builder().withId(2).withName("Doom").build();
        Game nonExistedGame = Game.builder().withName("Call of Duty").build();

        // when
        boolean deleteTrue = gameDAO.delete(existedGame);
        boolean deleteFalseWrongId = gameDAO.delete(wrongId);
        boolean deleteTrueSmall = gameDAO.delete(smallLetters);
        boolean deleteFalse = gameDAO.delete(nonExistedGame);

        // then
        assertTrue(deleteTrue);
        assertFalse(deleteFalseWrongId);
        assertTrue(deleteTrueSmall);
        assertFalse(deleteFalse);
    }
}