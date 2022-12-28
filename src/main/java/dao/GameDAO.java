package dao;

import entities.Game;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Getter
@Setter
public class GameDAO implements DAO<Game> {

    private static final Logger log = getLogger(GameDAO.class);

    private final Connection connection;

    public GameDAO(Connection connection) {
        this.connection = connection;


    }

    /**
     * Method adds new playground to database, getting the Playground
     * from client. First checks whether it is already in database and
     * only then adds it or not
     *
     * @param name Playground object sent by client
     * @return true if object was successfully added to database
     * @throws SQLException if there appeared the error in process of adding
     */
    @Override
    public boolean create(@NotNull Game name) throws SQLException {
        if (!isExisted(name)) {
            try (PreparedStatement ps = connection.prepareStatement(GameDAO.SQLGame.ADD.QUERY)) {
                ps.setString(1, name.getName());
                ps.setBoolean(2, name.isExclusive());
                ps.executeUpdate();
                log.info("Adding '{}' object to database", name.getName());
                return true;
            } catch (SQLException e) {
                log.info("Error is appeared while adding to database");
                connection.rollback();
                throw new RuntimeException("Game failed to be inserted");
            }
        }
        log.info("Game is already in database");
        return false;
    }


    /**
     * Search for the object in database and returns mapped
     * object. In case if there is no such an object returns null
     *
     * @param id given from client id number
     * @return object of Game
     */
    @Override
    public Game read(int id) {
        try (PreparedStatement ps = connection.prepareStatement(GameDAO.SQLGame.GET.QUERY)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                Game game = Mapper.mapGame(resultSet);
                log.info("Game {} is found", game.getName());
                resultSet.close();
                return game;
            }
        } catch (SQLException e) {
            log.info("An error occurred while searching for the Game");
            throw new RuntimeException(e);
        }
        log.info("No game has been found");
        return null;
    }

    /**
     * Updating only existing Game object, searching by id
     *
     * @param name object that has to be updated
     * @return true if updating was successfully
     * @throws SQLException in case of error in updating
     */
    @Override
    public boolean update(@NonNull Game name) throws SQLException {
        if (name.getId() != 0) {
            try (PreparedStatement ps = connection.prepareStatement(SQLGame.UPDATE.QUERY)) {
                ps.setString(1, name.getName());
                ps.setBoolean(2, name.isExclusive());
                ps.setInt(3, name.getId());
                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    log.info("Updated successfully");
                    return true;
                }
            } catch (SQLException e) {
                log.info("There is an error occurred during updating. Rolling back operation");
                connection.rollback();
                throw new RuntimeException(e);
            }
        }
        log.info("No such game in database or you didn't specify the id");
        return false;
    }

    /**
     * Checks on id. If it's 0, then no object will be deleted.
     * If there is such an object, deletes it from database
     *
     * @param id id of the game, that has to be deleted from database
     * @return true if it was deleted
     */
    @Override
    public boolean delete(int id) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(GameDAO.SQLGame.DELETE.QUERY)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                log.info("game was deleted successfully");
                return true;
            }
        } catch (SQLException e) {
            log.info("There is an error occurred while deleting the game");
            connection.rollback();
            throw new RuntimeException(e);
        }

        log.info("No game to delete was found or you didn't specify id");
        return false;
    }

    /**
     * Get all the game objects from database
     *
     * @return List of Games
     */
    @Override
    public List<Game> getAll() {
        List<Game> allGames = new ArrayList<>();
        log.info("Collecting all games");
        try(Statement st = connection.createStatement()) {
            ResultSet resultSet = st.executeQuery(SQLGame.GET_ALL.QUERY);
            while(resultSet.next()) {
                Game game = Mapper.mapGame(resultSet);
                allGames.add(game);
            }
            resultSet.close();
            return allGames;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Checking whether Game object is in database
     *
     * @param name Playground object
     * @return true if yes
     */

    public boolean isExisted(Game name) {
        try (PreparedStatement ps = connection.prepareStatement(GameDAO.SQLGame.SEARCH_NAME.QUERY)) {
            ps.setString(1, name.getName());
            ResultSet resultSet = ps.executeQuery();
            log.info("Checking if game '{}' exists in database", name.getName());
            return resultSet.next();
        } catch (SQLException e) {
            log.info("There is an error occurred while checking on existence");
            throw new RuntimeException(e);
        }
    }

    private enum SQLGame {
        GET("SELECT * FROM game WHERE game_id=(?);"),
        ADD("INSERT INTO game(game_name, exclusive) VALUES ((?), (?));"),
        UPDATE("UPDATE game SET game_name=(?), exclusive=(?) WHERE game_id=(?);"),
        DELETE("DELETE FROM game WHERE game_id=(?);"),
        SEARCH_NAME("SELECT game_name FROM game WHERE game_name ILIKE ((?));"),
        GET_ALL("SELECT * FROM game");
        final String QUERY;

        SQLGame(String query) {
            this.QUERY = query;
        }
    }
}
