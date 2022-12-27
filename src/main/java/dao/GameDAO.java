package dao;

import entities.Game;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
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


    @Override
    public boolean create(Game name) {
        return false;
    }

    @Override
    public Game read(int id) {
        return null;
    }

    @Override
    public boolean update(Game name) {
        return false;
    }

    @Override
    public boolean delete(Game name) {
        return false;
    }

    @Override
    public boolean isExisted(Game name) {
        return false;
    }
}
