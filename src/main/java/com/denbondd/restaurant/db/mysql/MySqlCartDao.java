package com.denbondd.restaurant.db.mysql;

import com.denbondd.restaurant.db.CartDao;
import com.denbondd.restaurant.db.ConnectionPool;
import com.denbondd.restaurant.db.entity.Dish;
import com.denbondd.restaurant.exceptions.DbException;
import com.denbondd.restaurant.util.SqlUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class MySqlCartDao implements CartDao {

    private static final Logger log = LogManager.getLogger(MySqlCartDao.class.getName());

    @Override
    public void addDishToCart(long userId, long dishId, int count) throws DbException {
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.ADD_DISH_TO_CART)) {
            int k = 0;
            ps.setLong(++k, userId);
            ps.setLong(++k, dishId);
            ps.setInt(++k, count);
            ps.setInt(++k, count);
            if (ps.executeUpdate() == 0) {
                throw new DbException("AddDishToCart failed, no rows attached");
            }
            c.commit();
        } catch (SQLException e) {
            throw new DbException("Cannot addDishToCart", e);
        }
    }

    @Override
    public Map<Dish, Integer> getCart(long userId) throws DbException {
        Map<Dish, Integer> cart = new HashMap<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_CART)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Dish dish = MySqlDishDao.mapDish(rs);
                    int count = rs.getInt("count");
                    cart.put(dish, count);
                }
            }
            return cart;
        } catch (SQLException e) {
            throw new DbException("Cannot getCart", e);
        }
    }

    @Override
    public void removeDishFromCart(long userId, long dishId) throws DbException {
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.REMOVE_DISH_FROM_CART)) {
            int k = 0;
            ps.setLong(++k, userId);
            ps.setLong(++k, dishId);
            ps.executeUpdate();
            c.commit();
        } catch (SQLException e) {
            throw new DbException("Cannot removeDishFromCart", e);
        }
    }

    @Override
    public void makeAnOrder(long userId, Map<Dish, Integer> cart) throws DbException {
        Connection c = null;
        Savepoint savepoint = null;
        try {
            c = ConnectionPool.getInstance().getConnection();
            savepoint = c.setSavepoint();
            long receiptId = addReceipt(c, userId);
            for (Map.Entry<Dish, Integer> entry : cart.entrySet()) {
                addReceiptHasDish(c, receiptId, entry.getKey(), entry.getValue());
            }
        } catch (SQLException e) {
            if (c != null) SqlUtils.rollback(c, savepoint);
            throw new DbException("Cannot makeAnOrder", e);
        } finally {
            SqlUtils.close(c);
        }
    }

    private void addReceiptHasDish(Connection c, long receiptId, Dish dish, int count) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(SqlUtils.ADD_RECEIPT_HAS_DISH)) {
            int k = 0;
            ps.setLong(++k, receiptId);
            ps.setLong(++k, dish.getId());
            ps.setLong(++k, count);
            ps.setLong(++k, dish.getPrice());
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Adding receipt_has_dish failed, no rows were attached");
            }
            c.commit();
        }
    }

    private long addReceipt(Connection c, long userId) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(SqlUtils.ADD_RECEIPT, Statement.RETURN_GENERATED_KEYS)) {
            int k = 0;
            ps.setLong(++k, userId);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Adding receipt failed, no rows were attached");
            }
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    c.commit();
                    return rs.getLong(1);
                }
            }
        }
        return -1;
    }

    @Override
    public void cleanCart(long userId) throws DbException {
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.CLEAN_USER_CART)) {
            ps.setLong(1, userId);
            if (ps.executeUpdate() == 0) {
                throw new SQLException("Cleaning cart failed, no rows were deleted");
            }
            c.commit();
        } catch (SQLException e) {
            throw new DbException("Cannot cleanCart", e);
        }
    }
}
