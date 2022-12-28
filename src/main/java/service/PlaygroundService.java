package service;

import dao.PlaygroundDAO;
import entities.Playground;
import entities.Station;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Service for Game object, that communicates with DAO
 */
public class PlaygroundService {

    private final PlaygroundDAO playgroundDAO;

    public PlaygroundService(Connection connection) {
        this.playgroundDAO = new PlaygroundDAO(connection);
    }

    /**
     * Get all the playgrounds objects from database
     *
     * @return List of Playgrounds
     */
    public List<Playground> getAll() {
        return playgroundDAO.getAll();
    }


    /**
     * Method adds new playground to database, getting the Playground
     * from client. First checks whether it is already in database and
     * only then adds it or not
     *
     * @param name name of the Playground sent by client
     * @return true if object was successfully added to database
     */
    public boolean add(String name) {
        Playground playground = Playground.builder()
                .withName(Station.valueOf(name))
                .build();
        try {
            return playgroundDAO.create(playground);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Search for the object in database and returns mapped
     * object. In case if there is no such an object returns null
     *
     * @param id given from client id number
     * @return object of Playground
     */
    public Playground get(int id) {
        return playgroundDAO.read(id);
    }

    /**
     * Updating only existing Playground object
     *
     * @param id   the playground id got from client
     * @param name object that has to be updated
     * @return true if updating was successfully
     */
    public boolean update(int id, String name) {
        Playground updated = Playground.builder()
                .withId(id)
                .withName(Station.valueOf(name))
                .build();
        try {
            return playgroundDAO.update(updated);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Checks on id. If it's 0, then no object will be deleted.
     * If there is such an object, deletes it from database
     *
     * @param id id of the Playground, that has to be deleted from database
     * @return true if it was deleted
     */
    public boolean delete(int id) {
        try {
            return playgroundDAO.delete(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
