package dao;

import entities.Play;
import entities.Playground;
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
public class PlayDAO implements DAO<Play> {

    private static final Logger log = getLogger(PlayDAO.class);
    private final Connection connection;

    public PlayDAO(Connection connection) {
        this.connection = connection;
    }


    /**
     * Method adds new play to database, getting the Play
     * from client. First checks whether it is already in database and
     * only then adds it or not
     *
     * @param name Play object sent by client
     * @return true if object was successfully added to database
     * @throws SQLException if there appeared the error in process of adding
     */
    @Override
    public boolean create(@NotNull Play name) throws SQLException {
        if (isExisted(name).isEmpty()) {
            log.info("Play is new. I will create it");
            try (PreparedStatement ps = connection.prepareStatement(PlayDAO.SQLPlay.ADD.QUERY)) {
                ps.setInt(1, name.getPlayground().getId());
                ps.setInt(2, name.getGame().getId());
                ps.setDouble(3, name.getPrice());
                ps.setInt(4, name.getAmount());
                ps.executeUpdate();
                log.info("Adding new Play object to database");
                return true;
            } catch (SQLException e) {
                log.info("Error is appeared while adding to database");
                connection.rollback();
                throw new RuntimeException(e);
            }
        }
        log.info("Play is already in database");
        return false;
    }


    /**
     * Search for the object in database and returns mapped
     * object. In case if there is no such an object returns null
     *
     * @param id given from client id number
     * @return object of Play
     */
    @Override
    public Play read(int id) {
        try (PreparedStatement ps = connection.prepareStatement(PlayDAO.SQLPlay.GET.QUERY)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                Play play = Mapper.mapPlay(resultSet);
                log.info("Play is found");
                resultSet.close();
                return play;
            }
        } catch (SQLException e) {
            log.info("An error occurred while searching for the Play");
            throw new RuntimeException(e);
        }
        log.info("No play has been found");
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
                ps.setInt(1, name.getPlayground().getId());
                ps.setInt(2, name.getGame().getId());
                ps.setDouble(3, name.getPrice());
                ps.setInt(4, name.getAmount());
                ps.setInt(5, name.getGame().getId());
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
        log.info("No such play in database or you didn't specify the id");
        return false;
    }

    /**
     * Checks on id. If it's 0, then no object will be deleted.
     * If there is such an object, deletes it from database
     *
     * @param id Play object, that has to be deleted from database
     * @return true if it was deleted
     */
    @Override
    public boolean delete(int id) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(PlayDAO.SQLPlay.DELETE.QUERY)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                log.info("Play was deleted successfully");
                return true;
            }
        } catch (SQLException e) {
            log.info("There is an error occurred while deleting the play");
            connection.rollback();
            throw new RuntimeException(e);
        }

        log.info("No play to delete was found or you didn't specify id");
        return false;
    }

    /**
     * Get all the playgrounds objects from database
     *
     * @return List of Playgrounds
     */
    @Override
    public List<Play> getAll() {
        List<Play> allPlays = new ArrayList<>();
        log.info("Collecting all plays");
        try(Statement st = connection.createStatement()) {
            ResultSet resultSet = st.executeQuery(PlayDAO.SQLPlay.GET_ALL.QUERY);
            while(resultSet.next()) {
                Play play = Mapper.mapPlay(resultSet);
                allPlays.add(play);
            }
            resultSet.close();
            return allPlays;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * Checking whether Play objects is in database
     *
     * @param name Play object
     * @return true if yes
     */

    public List<Play> isExisted(Play name) {
        List<Play> plays = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(PlayDAO.SQLPlay.SEARCH_NAME.QUERY)) {
            ps.setString(1, name.getGame().getName());
            ResultSet resultSet = ps.executeQuery();
            log.info("Checking if play exists in database");
            while (resultSet.next()) {
                Play play = Mapper.mapPlay(resultSet);
                plays.add(play);
            }
            return plays;
        } catch (SQLException e) {
            log.info("There is an error occurred while checking on existence");
            throw new RuntimeException(e);
        }
    }

    private enum SQLPlay {
        GET("""
                SELECT play_id, playground.playground_id, playground_name, game.game_id, game_name, exclusive, price, amount
                FROM play JOIN game ON play.game_id=game.game_id
                          JOIN playground ON play.playground_id=playground.playground_id
                WHERE play_id=(?);
                """),
        ADD("INSERT INTO play(playground_id, game_id, price, amount) VALUES ((?), (?), (?), (?));"),
        UPDATE("UPDATE play SET playground_id=(?), game_id=(?), price=(?), amount=(?) WHERE game_id=(?);"),
        DELETE("DELETE FROM play WHERE play_id=(?);"),
        SEARCH_NAME("""
                SELECT play_id, playground.playground_id, playground_name, game.game_id, game_name, exclusive, price, amount
                FROM play JOIN game ON play.game_id=game.game_id
                          JOIN playground ON play.playground_id=playground.playground_id
                WHERE game_name ILIKE ((?));
                """
        ),
        GET_ALL("SELECT * FROM play");
        final String QUERY;

        SQLPlay(String query) {
            this.QUERY = query;
        }
    }


}
