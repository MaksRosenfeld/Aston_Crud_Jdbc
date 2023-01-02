package dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.beans.PropertyVetoException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConnectionPool {

    private static ComboPooledDataSource cpds;

    public static ComboPooledDataSource getDataSource() throws PropertyVetoException {
        if (cpds == null) {
            cpds = new ComboPooledDataSource();
            cpds.setDriverClass("org.postgresql.Driver");
            cpds.setJdbcUrl(System.getenv("POSTGRES_URL"));
            cpds.setUser(System.getenv("POSTGRES_USER"));
            cpds.setPassword(System.getenv("POSTGRES_PW"));

            cpds.setInitialPoolSize(5);
            cpds.setMinPoolSize(5);
            cpds.setAcquireIncrement(5);
            cpds.setMaxPoolSize(20);
            cpds.setMaxStatements(100);
            return cpds;
        }
        return cpds;
    }
}