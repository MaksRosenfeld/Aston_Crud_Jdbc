package dao;

import entities.Entity;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T extends Entity> {


    boolean create(T name) throws SQLException;

    T read(int id);

    boolean update(T name) throws SQLException;

    boolean delete(int id) throws SQLException;

    List<T> getAll();




}
