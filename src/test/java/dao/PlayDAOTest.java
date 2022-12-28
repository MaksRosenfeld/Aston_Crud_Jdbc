package dao;

import entities.Game;
import entities.Play;
import entities.Playground;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class PlayDAOTest extends AbstractDAOTest {

    @DisplayName("Creates new Play in database")
    @Test
    void create() throws SQLException {
        // given
        Game existedGame = gameDAO.read(2);
        Playground pg1 = playgroundDAO.read(3);
        Play play = Play.builder().withPlayground(pg1).withGame(existedGame)
                .withPrice(25.55).withAmount(233).build();

        Game newGame = Game.builder().withName("Best Game").withExclusive(true).build();
        gameDAO.create(newGame);
        Game bestGameWithId = gameDAO.read(5);
        Play anotherPlay = Play.builder().withPlayground(pg1).withGame(bestGameWithId)
                .withPrice(25.55).withAmount(233).build();

        // when
        boolean createFalse = playDAO.create(play);
        boolean createTrue = playDAO.create(anotherPlay);

        // then
        assertTrue(createTrue);
        assertFalse(createFalse);
    }

    @DisplayName("Check on reading play object")
    @Test
    void read() {
        // given

        // when
        Play existedPlay = playDAO.read(2);
        Play nonExistedPlay = playDAO.read(10);

        // then
        assertEquals("Half-Life 2", existedPlay.getGame().getName());
        assertNull(nonExistedPlay);
    }

    @DisplayName("Updating play object")
    @Test
    void update() throws SQLException {
        // given
        Play existedGame = playDAO.read(1);
        existedGame.setAmount(299);
        existedGame.setPrice(24.95);

        // when
        playDAO.update(existedGame);
        Play updated = playDAO.read(1);

        // then
        assertEquals(24.95, updated.getPrice());
    }

    @DisplayName("Deleting the object")
    @Test
    void delete() throws SQLException {
        // given

        // when
        playDAO.delete(2);
        boolean notDeleted = playDAO.delete(99);
        Play notFound = playDAO.read(2);

        // then
        assertNull(notFound);
        assertFalse(notDeleted);
    }
}