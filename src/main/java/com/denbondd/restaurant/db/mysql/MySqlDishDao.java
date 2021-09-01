package com.denbondd.restaurant.db.mysql;

import com.denbondd.restaurant.db.ConnectionPool;
import com.denbondd.restaurant.db.DishDao;
import com.denbondd.restaurant.db.entity.Dish;
import com.denbondd.restaurant.exceptions.DbException;
import com.denbondd.restaurant.util.SqlUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlDishDao implements DishDao {

    private static Dish mapDish(ResultSet rs) throws SQLException {
        return new Dish.Builder()
                .setId(rs.getLong("id"))
                .setName(rs.getString("name"))
                .setCategoryId(rs.getLong("category_id"))
                .setPrice(rs.getLong("price"))
                .getDish();
    }

    @Override
    public List<Dish> getAllDishes() throws DbException {
        List<Dish> dishes = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_ALL_DISHES);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                dishes.add(mapDish(rs));
            }
            return dishes;
        } catch (SQLException e) {
            //TODO log with log4j
            e.printStackTrace();
            throw new DbException("Cannot getAllDishes", e);
        }
    }
}
