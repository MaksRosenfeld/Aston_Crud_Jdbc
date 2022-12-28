package dao;

import entities.Game;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class GameDAOTest extends AbstractDAOTest {


    @DisplayName("Add non existed and existed game")
    @Test
    void create() throws SQLException {
        // given
        Game nonExisted = Game.builder().withName("Mario").withExclusive(true).build();
        Game existedGame = Game.builder().withName("Gears of War 3").withExclusive(true).build();
        Game existedSmallLetters = Game.builder().withName("last of us").withExclusive(true).build();

        // when
        boolean nonExistedTrue = gameDAO.create(nonExisted);
        Game mario = gameDAO.read(5);
        boolean existedFalse = gameDAO.create(existedGame);
        boolean existedWithMistakeFalse = gameDAO.create(existedSmallLetters);

        // then
        assertTrue(nonExistedTrue);
        assertEquals("Mario", mario.getName());
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
        int existedGame = 3;
        int nonExistedGame = 9;
        int anotherNonExistedGame = -5;


        // when
        boolean deleteTrue = gameDAO.delete(existedGame);
        boolean deleteFalse = gameDAO.delete(nonExistedGame);
        boolean deleteFalseNegativeId = gameDAO.delete(anotherNonExistedGame);

        // then
        assertTrue(deleteTrue);
        assertFalse(deleteFalseNegativeId);
        assertFalse(deleteFalse);
    }
}