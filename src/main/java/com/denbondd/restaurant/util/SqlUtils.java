package com.denbondd.restaurant.util;

import java.sql.Connection;
import java.sql.SQLException;

public class SqlUtils {

    public static final String LOG_IN = "SELECT * FROM user WHERE login LIKE ? AND password LIKE ?";
    public static final String SIGN_UP = "INSERT INTO user (login, password) VALUES (?, ?)";
    public static final String FIND_USER_BY_LOGIN = "SELECT * FROM user WHERE login LIKE ?";

    public static void rollback(Connection con) {
        try {
            con.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(AutoCloseable con) {
        try {
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
