package com.denbondd.restaurant.db.mysql;

import com.denbondd.restaurant.db.CategoryDao;
import com.denbondd.restaurant.db.ConnectionPool;
import com.denbondd.restaurant.db.entity.Category;
import com.denbondd.restaurant.exceptions.DbException;
import com.denbondd.restaurant.util.SqlUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlCategoryDao implements CategoryDao {

    private static Category mapCategory(ResultSet rs) throws SQLException {
        return new Category.Builder()
                .setId(rs.getLong("id"))
                .setName(rs.getString("name"))
                .getCategory();
    }

    @Override
    public List<Category> getAllCategories() throws DbException {
        List<Category> categories = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_ALL_CATEGORIES);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                categories.add(mapCategory(rs));
            }
            return categories;
        } catch (SQLException e) {
            throw new DbException("Cannot getAllCategories", e);
        }
    }
}
