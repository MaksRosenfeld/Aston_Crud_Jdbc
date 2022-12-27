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
    public boolean update(Play name) {
        return false;

    }

    @Override
    public boolean delete(Play name) {
        return false;
    }

    @Override
    public boolean isExisted(Play name) {
        return false;
    }


}
