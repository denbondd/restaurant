package com.denbondd.restaurant.db.mysql;

import com.denbondd.restaurant.db.ConnectionPool;
import com.denbondd.restaurant.db.UserDao;
import com.denbondd.restaurant.db.entity.Dish;
import com.denbondd.restaurant.db.entity.User;
import com.denbondd.restaurant.exceptions.DbException;
import com.denbondd.restaurant.util.SqlUtils;
import com.denbondd.restaurant.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MySqlUserDao implements UserDao {

    private static final Logger log = LogManager.getLogger(MySqlUserDao.class.getName());

    private static User mapUser(ResultSet rs) throws SQLException {
        int k = 0;
        return new User.Builder()
                .setId(rs.getLong(++k))
                .setLogin(rs.getString(++k))
                .setRoleId(rs.getLong(k += 2))
                .setCreateDate(rs.getTimestamp(++k))
                .getUser();
    }

    @Override
    public boolean isLoginUnique(String login) throws DbException {
        return getUserByLogin(login) == null;
    }

    @Override
    public User getUserByLogin(String login) throws DbException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlUtils.FIND_USER_BY_LOGIN)) {
            ps.setString(1, login);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return mapUser(rs);
            }
        } catch (SQLException ex) {
            log.error(ex);
            throw new DbException("Cannot getUserByLogin", ex);
        }
    }

    @Override
    public User logIn(String login, char[] password) throws DbException {
        String hashPass = Utils.hash(password);
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(SqlUtils.LOG_IN)) {
            int k = 0;
            ps.setString(++k, login);
            ps.setString(++k, hashPass);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return mapUser(rs);
            }
        } catch (SQLException ex) {
            log.error(ex);
            throw new DbException("Cannot logIn", ex);
        }
    }

    @Override
    public User signUp(String login, char[] password) throws DbException {
        String hashPass = Utils.hash(password);
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ConnectionPool.getInstance().getConnection();
            ps = con.prepareStatement(SqlUtils.SIGN_UP);
            int k = 0;
            ps.setString(++k, login);
            ps.setString(++k, hashPass);
            if (ps.executeUpdate() == 0) {
                throw new DbException("SignUp failed, no rows attached");
            }
            con.commit();
            return getUserByLogin(login);
        } catch (SQLException ex) {
            log.error(ex);
            if (con != null) SqlUtils.rollback(con);
            throw new DbException("Cannot logIn", ex);
        } finally {
            SqlUtils.close(con);
            SqlUtils.close(ps);
        }
    }

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
            log.error(e);
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
            log.error(e);
            throw new DbException("Cannot getCart", e);
        }
    }
}
