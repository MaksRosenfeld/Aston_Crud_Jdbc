package dao;

import entities.Playground;
import entities.Station;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Class, that works with Playground object. All standard
 * operations of CRUD. Cannot exist without Connection to DB
 */
@Getter
@Setter
public class PlaygroundDAO implements DAO<Playground> {

    private static final Logger log = LoggerFactory.getLogger(PlaygroundDAO.class);
    private final Connection connection;

    public PlaygroundDAO(Connection connection) {
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
    public boolean create(@NotNull Playground name) throws SQLException {
        if (!isExisted(name)) {
            try (PreparedStatement ps = connection.prepareStatement(SQLPlayground.ADD.QUERY,
                    Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, name.getName().name());
                ps.executeUpdate();
                log.info("Adding new Playground object to database");
                return true;
            } catch (SQLException e) {
                log.info("Error is appeared while adding to database");
                connection.rollback();
                throw new RuntimeException("Playground failed to be inserted");
            }
        }
        log.info("Playground is already in database");
        return false;
    }


    /**
     * Search for the object in database and returns mapped
     * object. In case if there is no such an object returns null
     *
     * @param id given from client id number
     * @return object of Playground
     */
    @Override
    public Playground read(int id) {
        try (PreparedStatement ps = connection.prepareStatement(SQLPlayground.GET.QUERY);
        ) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                int playgroundId = resultSet.getInt("playground_id");
                String playgroundName = resultSet.getString("playground_name");
                log.info("Playground {} is found", playgroundName);
                resultSet.close();
                return Playground.builder()
                        .withId(playgroundId)
                        .withName(Station.valueOf(playgroundName))
                        .build();
            }
        } catch (SQLException e) {
            log.info("An error occurred while searching for the Playground");
            throw new RuntimeException(e);
        }
        log.info("No playground has been found");
        return null;
    }

    /**
     * Updating only existing Playground object
     *
     * @param name object that has to be updated
     * @return true if updating was successfully
     * @throws SQLException in case of error in updating
     */
    @Override
    public boolean update(@NonNull Playground name) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SQLPlayground.UPDATE.QUERY)) {
            ps.setString(1, name.getName().name());
            ps.setInt(2, name.getId());
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
        log.info("No such playground in database");
        return false;
    }

    /**
     * Checks on id. If it's 0, then no object will be deleted.
     * If there is such an object, deletes it from database
     *
     * @param name Playground object, that has to be deleted from database
     * @return true if it was deleted
     */
    @Override
    public boolean delete(Playground name) {
        if (name.getId() != 0) {
            try (PreparedStatement ps = connection.prepareStatement(SQLPlayground.DELETE.QUERY)) {
                ps.setString(1, name.getName().name());
                ps.setInt(2, name.getId());
                int affectedRows = ps.executeUpdate();
                if (affectedRows > 0) {
                    log.info("Playground was deleted successfully");
                    return true;
                }
            } catch (SQLException e) {
                log.info("There is an error occurred while deleting the playground");
                throw new RuntimeException(e);
            }
        }
        log.info("No playground to delete was found");
        return false;
    }

    /**
     * Checking whether Playground object is in database
     *
     * @param name Playground object
     * @return true if yes
     */
    @Override
    public boolean isExisted(Playground name) {
        try (PreparedStatement ps = connection.prepareStatement(SQLPlayground.SEARCH_NAME.QUERY)) {
            ps.setString(1, name.getName().name());
            ResultSet resultSet = ps.executeQuery();
            log.info("Checking if playground exists in database");
            return resultSet.next();
        } catch (SQLException e) {
            log.info("There is an error occurred while checking on existence");
            throw new RuntimeException(e);
        }
    }

    private enum SQLPlayground {
        GET("SELECT * FROM playground WHERE playground_id=(?);"),
        ADD("INSERT INTO playground(playground_name) VALUES ((?));"),
        UPDATE("UPDATE playground SET playground_name=(?) WHERE playground_id=(?);"),
        DELETE("DELETE FROM playground WHERE playground_name ILIKE ((?)) AND playground_id=(?);"),
        SEARCH_NAME("SELECT playground_name FROM playground WHERE playground_name=(?);");
        final String QUERY;

        SQLPlayground(String query) {
            this.QUERY = query;
        }
    }
}
