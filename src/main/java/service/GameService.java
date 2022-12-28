package service;

import dao.GameDAO;
import entities.Game;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Service for Game object, that communicates with DAO
 */
public class GameService {

    private final GameDAO gameDAO;

    public GameService(Connection connection) {
        this.gameDAO = new GameDAO(connection);
    }

    public List<Game> getAll() {
        return gameDAO.getAll();
    }

    public boolean add(String name, boolean exclusive) {
        Game game = Game.builder()
                .withName(name)
                .withExclusive(exclusive)
                .build();
        try {
            return gameDAO.create(game);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Game get(int id) {
        return gameDAO.read(id);
    }

    public boolean update(int id, String name, boolean exclusive) {
        Game updated = Game.builder()
                .withId(id)
                .withName(name)
                .withExclusive(exclusive)
                .build();
        try {
            return gameDAO.update(updated);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean delete(int id) {
        try {
            return gameDAO.delete(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




}
