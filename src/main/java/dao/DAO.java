package dao;

import entities.Entity;

import java.sql.SQLException;

public interface DAO<T extends Entity> {


    boolean create(T name) throws SQLException;

    T read(int id);

    void update(T name);

    T delete(T name);

    boolean isExist(T name);


}
