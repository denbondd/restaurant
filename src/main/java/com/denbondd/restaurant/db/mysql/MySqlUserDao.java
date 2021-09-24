package com.denbondd.restaurant.db.mysql;

import com.denbondd.restaurant.db.ConnectionPool;
import com.denbondd.restaurant.db.UserDao;
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
import java.util.ArrayList;
import java.util.List;

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
            if (con != null) SqlUtils.rollback(con);
            throw new DbException("Cannot logIn", ex);
        } finally {
            SqlUtils.close(con);
            SqlUtils.close(ps);
        }
    }

    @Override
    public void changePassword(long userId, char[] newPass) throws DbException {
        String hashPass = Utils.hash(newPass);
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.CHANGE_PASSWORD)) {
            int k = 0;
            ps.setString(++k, hashPass);
            ps.setLong(++k, userId);
            if (ps.executeUpdate() == 0) {
                throw new DbException("Changing password failed, no rows were changed");
            }
            c.commit();
        } catch (SQLException e) {
            throw new DbException("Cannot changePassword", e);
        }
    }

    @Override
    public List<User> getAllUsers() throws DbException {
        List<User> users = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_ALL_USERS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                users.add(mapUser(rs));
            }
            return users;
        } catch (SQLException e) {
            throw new DbException("Cannot getAllUsers", e);
        }
    }

    @Override
    public void changeRole(long userId, int roleId) throws DbException {
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.CHANGE_ROLE)) {
            int k = 0;
            ps.setLong(++k, roleId);
            ps.setLong(++k, userId);
            if (ps.executeUpdate() == 0) {
                throw new DbException("Changing role failed, no rows were changed");
            }
            c.commit();
        } catch (SQLException e) {
            throw new DbException("Cannot changeRole", e);
        }
    }

    @Override
    public void deleteUser(long userId) throws DbException {
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.DELETE_USER)) {
            ps.setLong(1, userId);
            if (ps.executeUpdate() == 0) {
                throw new DbException("Deleting user failed, no rows were changed");
            }
            c.commit();
        } catch (SQLException e) {
            throw new DbException("Cannot deleteUser", e);
        }
    }
}
