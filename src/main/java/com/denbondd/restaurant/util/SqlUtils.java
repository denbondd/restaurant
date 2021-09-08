package com.denbondd.restaurant.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SqlUtils {

    public static final String LOG_IN = "SELECT * FROM user WHERE login LIKE ? AND password LIKE ?";
    public static final String SIGN_UP = "INSERT INTO user (login, password) VALUES (?, ?)";
    public static final String FIND_USER_BY_LOGIN = "SELECT * FROM user WHERE login LIKE ?";
    public static final String GET_ALL_DISHES = "SELECT * FROM dish";
    public static final String GET_DISHES_FROM_CATEGORY = "SELECT * FROM dish WHERE category_id = ?";
    public static final String GET_SORTED_DISHES_FROM_CATEGORY = "SELECT * FROM dish WHERE category_id = ? ORDER BY ";
    public static final String GET_SORTED_DISHES = "SELECT * FROM dish ORDER BY ";
    public static final String GET_ALL_CATEGORIES = "SELECT * FROM category";

    public static final Map<String, String> sortingTypes = new HashMap<>();

    static {
        sortingTypes.put("Price", "price");
        sortingTypes.put("Name", "name");
        sortingTypes.put("Category", "category_id");
    }

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
