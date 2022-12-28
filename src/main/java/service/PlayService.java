package service;

import dao.GameDAO;
import dao.PlayDAO;
import dao.PlaygroundDAO;
import entities.Play;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PlayService {

    private final PlayDAO playDAO;
    private final PlaygroundDAO playgroundDAO;
    private final GameDAO gameDAO;


    public PlayService(Connection connection) {
        this.playDAO = new PlayDAO(connection);
        this.playgroundDAO = new PlaygroundDAO(connection);
        this.gameDAO = new GameDAO(connection);
    }

    /**
     * Get all the play objects from database
     *
     * @return List of Plays
     */
    public List<Play> getAll() {
        return playDAO.getAll();
    }


    /**
     * Method adds new play to database, getting the Play
     * from client. First checks whether it is already in database and
     * only then adds it or not
     *
     * @param playgroundId id of the playground got from client
     * @param gameId       id of the game sent by client
     * @param price        name of the game for specific playground
     * @param amount       amount of the specific game for particular playground
     * @return true if object was successfully added to database
     */
    public boolean add(int playgroundId, int gameId, double price, int amount) {
        Play play = Play.builder()
                .withPlayground(playgroundDAO.read(playgroundId))
                .withGame(gameDAO.read(gameId))
                .withPrice(price)
                .withAmount(amount)
                .build();
        try {
            return playDAO.create(play);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Search for the object in database and returns mapped
     * object. In case if there is no such an object returns null
     *
     * @param id given from client id number
     * @return object of Play
     */
    public Play get(int id) {
        return playDAO.read(id);
    }

    /**
     * Updating only existing Play object
     *
     * @param id           the play id got from client
     * @param playgroundId id of the playground for play
     * @param gameId       id of the game for play
     * @param price        updated data for price
     * @param amount       updated data for amount
     * @return true if updating was successfully
     */
    public boolean update(int id, int playgroundId, int gameId, double price, int amount) {
        Play updated = Play.builder()
                .withId(id)
                .withPlayground(playgroundDAO.read(playgroundId))
                .withGame(gameDAO.read(gameId))
                .withPrice(price)
                .withAmount(amount)
                .build();
        try {
            return playDAO.update(updated);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Checks on id.
     * If there is such an object, deletes it from database
     *
     * @param id id of the Play, that has to be deleted from database
     * @return true if it was deleted
     */
    public boolean delete(int id) {
        try {
            return playDAO.delete(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
