package com.denbondd.restaurant.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.Map;

public class SqlUtils {

    private static final Logger log = LogManager.getLogger(SqlUtils.class.getName());

    public static final String LOG_IN = "SELECT * FROM user WHERE login LIKE ? AND password LIKE ?";
    public static final String SIGN_UP = "INSERT INTO user (login, password) VALUES (?, ?)";
    public static final String FIND_USER_BY_LOGIN = "SELECT * FROM user WHERE login LIKE ?";
    public static final String CHANGE_PASSWORD = "UPDATE user SET password = ? WHERE id = ?";
    public static final String GET_ALL_USERS = "SELECT * FROM user";
    public static final String CHANGE_ROLE = "UPDATE user SET role_id = ? WHERE id = ?";
    public static final String DELETE_USER = "DELETE FROM user WHERE id = ?";

    public static final String GET_DISH_BY_ID = "SELECT * FROM dish WHERE id = ?";
    public static final String GET_ALL_DISHES = "SELECT * FROM dish";
    public static final String GET_DISHES_FROM_CATEGORY = "SELECT * FROM dish WHERE category_id = ?";
    public static final String GET_SORTED_DISHES_FROM_CATEGORY = "SELECT * FROM dish WHERE category_id = ? ORDER BY ";
    public static final String GET_SORTED_DISHES = "SELECT * FROM dish ORDER BY ";
    public static final String GET_DISHES_COUNT = "SELECT COUNT(*) FROM dish";
    public static final String GET_DISHES_COUNT_IN_CATEGORY = "SELECT COUNT(*) FROM dish WHERE category_id = ?";
    public static final String GET_ALL_CATEGORIES = "SELECT * FROM category";

    public static final String ADD_DISH_TO_CART = "INSERT INTO cart_has_dish (user_id, dish_id, count) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE count = ?";
    public static final String GET_CART = "SELECT dish_id AS id, name, category_id, price, weight, description, count FROM cart_has_dish AS chd INNER JOIN dish ON dish.id = chd.dish_id WHERE chd.user_id = ?";
    public static final String CLEAN_USER_CART = "DELETE FROM cart_has_dish WHERE user_id = ?";

    public static final String ADD_RECEIPT = "INSERT INTO receipt (user_id) VALUES (?)";
    public static final String ADD_RECEIPT_HAS_DISH = "INSERT INTO receipt_has_dish (receipt_id, dish_id, count, price) VALUES (?, ?, ?, ?)";
    public static final String REMOVE_DISH_FROM_CART = "DELETE FROM cart_has_dish WHERE user_id = ? AND dish_id = ?";

    public static final String GET_USER_RECEIPTS = "SELECT * FROM receipt WHERE user_id = ?";
    public static final String GET_RECEIPT_DISHES = "SELECT dish.id, dish.name, rhd.price, rhd.count FROM receipt_has_dish AS rhd INNER JOIN dish ON dish.id = rhd.dish_id WHERE receipt_id = ?";
    public static final String GET_ALL_RECEIPTS = "SELECT * FROM receipt";
    public static final String GET_RECEIPTS_APPROVED_BY = "SELECT * FROM receipt WHERE manager_id = ?";
    public static final String GET_NOT_APPROVED = "SELECT * FROM receipt WHERE manager_id IS NULL";
    public static final String CHANGE_RECEIPT_STATUS = "UPDATE receipt SET status_id = ?, manager_id = ? WHERE id = ?";


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
            log.error(e);
        }
    }

    public static void rollback(Connection con, Savepoint s) {
        try {
            con.rollback(s);
        } catch (SQLException e) {
            log.error(e);
        }
    }

    public static void close(AutoCloseable con) {
        try {
            con.close();
        } catch (Exception e) {
            log.error(e);
        }
    }
}
