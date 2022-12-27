package dao;

import entities.Game;
import entities.Play;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.slf4j.LoggerFactory.getLogger;

@Getter
@Setter
public class PlayDAO implements DAO<Play> {

    private static final Logger log = getLogger(PlayDAO.class);

    private final Connection connection;

    public PlayDAO(Connection connection) {
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
    public boolean create(@NotNull Play name) throws SQLException {
        if (!isExisted(name)) {
            try (PreparedStatement ps = connection.prepareStatement(PlayDAO.SQLPlay.ADD.QUERY)) {
                ps.setString(1, name.getName());
                ps.setBoolean(2, name.isExclusive());
                ps.executeUpdate();
                log.info("Adding new Game object to database");
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
    public Play read(int id) {
        try (PreparedStatement ps = connection.prepareStatement(PlayDAO.SQLPlay.GET.QUERY)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int gameId = resultSet.getInt("game_id");
                String gameName = resultSet.getString("game_name");
                log.info("Game {} is found", gameName);
                resultSet.close();
                return Game.builder()
                        .withId(gameId)
                        .withName(gameName)
                        .build();
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
    public boolean update(@NonNull Play name) throws SQLException {
        if (name.getId() != 0) {
            try (PreparedStatement ps = connection.prepareStatement(PlayDAO.SQLPlay.UPDATE.QUERY)) {
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
     * @param name Game object, that has to be deleted from database
     * @return true if it was deleted
     */
    @Override
    public boolean delete(Play name) throws SQLException {
        if (name.getId() != 0) {
            try (PreparedStatement ps = connection.prepareStatement(PlayDAO.SQLPlay.DELETE.QUERY)) {
                ps.setString(1, name.getName());
                ps.setInt(2, name.getId());
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
        }
        log.info("No game to delete was found or you didn't specify id");
        return false;
    }

    /**
     * Checking whether Game object is in database
     *
     * @param name Playground object
     * @return true if yes
     */
    @Override
    public boolean isExisted(Play name) {
        try (PreparedStatement ps = connection.prepareStatement(PlayDAO.SQLPlay.SEARCH_NAME.QUERY)) {
            ps.setString(1, name.getName());
            ResultSet resultSet = ps.executeQuery();
            log.info("Checking if game exists in database");
            return resultSet.next();
        } catch (SQLException e) {
            log.info("There is an error occurred while checking on existence");
            throw new RuntimeException(e);
        }
    }

    private enum SQLPlay {
        GET("SELECT * FROM play WHERE play_id=(?);"),
        ADD("INSERT INTO play(playground, game, price, amount) VALUES ((?), (?), (?), (?));"),
        UPDATE("UPDATE play SET playground=(?), game=(?), price=(?), amount=(?) WHERE game_id=(?);"),
        DELETE("DELETE FROM play WHERE play_id=(?);"),
        SEARCH_NAME("SELECT playground_id, game_id FROM play WHERE playground_id=(?) AND game_id=(?);");
        final String QUERY;

        SQLPlay(String query) {
            this.QUERY = query;
        }
    }


}
