package dao;

import entities.Entity;

import java.sql.SQLException;

public interface DAO<T extends Entity> {


    boolean create(T name) throws SQLException;

    T read(int id);

    boolean update(T name) throws SQLException;

    boolean delete(T name) throws SQLException;

    boolean isExisted(T name);


}
