package dao;

import entities.Play;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.util.List;

@Getter
@Setter
public class PlayDAO implements DAO<Play> {

    private final Connection connection;

    public PlayDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public boolean create(Play name) {
        return false;


    }

    @Override
    public Play read(int id) {
        return null;
    }

    @Override
    public void update(Play name) {

    }

    @Override
    public Play delete(Play name) {
        return null;
    }

    @Override
    public boolean isExist(Play name) {
        return false;
    }


}
