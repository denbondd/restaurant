package com.denbondd.restaurant.db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static ConnectionPool instance;

    private final DataSource ds;

    private ConnectionPool() {
        try {
            Context context = new InitialContext();
            ds = (DataSource) context.lookup("java:comp/env/jdbc/restaurant");
        } catch (NamingException ex) {
            throw new IllegalStateException("Cannot init DBManager", ex);
        }
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    /**
     * Get connection from pool
     * @return connection
     */
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
