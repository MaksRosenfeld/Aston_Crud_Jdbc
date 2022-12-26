package dao;

import entities.Game;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.util.List;

@Getter
@Setter
public class GameDAO implements DAO<Game> {

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
    public void update(Game name) {

    }

    @Override
    public Game delete(Game name) {
        return null;
    }

    @Override
    public boolean isExist(Game name) {
        return false;
    }
}
