package dao;

import entities.Playground;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.sql.*;

@Slf4j
@Getter
@Setter
public class PlaygroundDAO implements DAO<Playground> {
    private final Connection connection;

    public PlaygroundDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean create(@NotNull Playground name) throws SQLException {
        if (!isExist(name)) {
            try(PreparedStatement ps = connection.prepareStatement(SQLPlayground.ADD.QUERY,
                    Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, name.getName().name());
                return ps.execute();
            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("Playground failed to be inserted");
            }
        }
        return false;
    }


    @Override
    public Playground read(int id) {

        return null;
    }

    @Override
    public void update(Playground name) {

    }

    @Override
    public Playground delete(Playground name) {
        return null;
    }

    @Override
    public boolean isExist(Playground name) {
        return false;
    }

    private enum SQLPlayground {
        GET("SELECT * FROM playground WHERE playground_id=(?);"),
        ADD("INSERT INTO playground(playground_name) VALUES ((?));"),
        UPDATE("UPDATE playground SET playground_name=(?) WHERE id=(?);"),
        DELETE("DELETE FROM playground WHERE playground_name=(?);");
        String QUERY;
        SQLPlayground(String query) {
            this.QUERY = query;
        }
    }
}
