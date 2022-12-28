package dao;

import entities.Game;
import entities.Play;
import entities.Playground;
import entities.Station;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Maps data from database to Objects
 */
public final class Mapper {

    static final String idField = "play_id";
    static final String playgroundIdField = "playground_id";
    static final String gameIdField = "game_id";


    static final String gameNameField = "game_name";
    static final String playgroundNameField = "playground_name";
    static final String exclusiveField = "exclusive";
    static final String priceField = "price";
    static final String amountField = "amount";

    /**
     * Maps play fields into Play Object
     *
     * @param resultSet data from database
     * @return Play object
     * @throws SQLException if SQL command was incorrect
     */
    static Play mapPlay(ResultSet resultSet) throws SQLException {
        return Play.builder()
                .withId(resultSet.getInt(idField))
                .withPlayground(mapPlayground(resultSet))
                .withGame(mapGame(resultSet))
                .withPrice(resultSet.getDouble(priceField))
                .withAmount(resultSet.getInt(amountField))
                .build();
    }


    /**
     * Maps game fields into Game Object
     *
     * @param resultSet data from database
     * @return Game object
     * @throws SQLException if SQL command was incorrect
     */
    static Game mapGame(ResultSet resultSet) throws SQLException {
        return Game.builder()
                .withId(resultSet.getInt(gameIdField))
                .withName(resultSet.getString(gameNameField))
                .withExclusive(resultSet.getBoolean(exclusiveField))
                .build();
    }


    /**
     * Maps playground fields into Playground Object
     *
     * @param resultSet data from database
     * @return Playground object
     * @throws SQLException if SQL command was incorrect
     */
    static Playground mapPlayground(ResultSet resultSet) throws SQLException {
        return Playground.builder()
                .withId(resultSet.getInt(playgroundIdField))
                .withName(Station.valueOf(resultSet.getString(playgroundNameField)))
                .build();
    }

}
