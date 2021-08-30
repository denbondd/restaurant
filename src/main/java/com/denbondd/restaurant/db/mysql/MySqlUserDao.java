package com.denbondd.restaurant.db.mysql;

import com.denbondd.restaurant.db.ConnectionPool;
import com.denbondd.restaurant.db.UserDao;
import com.denbondd.restaurant.db.entity.User;
import com.denbondd.restaurant.exceptions.DbException;
import com.denbondd.restaurant.util.SqlUtils;
import com.denbondd.restaurant.util.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlUserDao implements UserDao {

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
            //TODO log with log4j
            ex.printStackTrace();
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
            //TODO log with log4j
            ex.printStackTrace();
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
            //TODO log with log4j
            ex.printStackTrace();
            if (con != null) SqlUtils.rollback(con);
            throw new DbException("Cannot logIn", ex);
        } finally {
            SqlUtils.close(con);
            SqlUtils.close(ps);
        }
    }
}
