package com.denbondd.restaurant.db.mysql;

import com.denbondd.restaurant.db.ConnectionPool;
import com.denbondd.restaurant.db.DishDao;
import com.denbondd.restaurant.db.entity.Dish;
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

public class MySqlDishDao implements DishDao {

    private static final Logger log = LogManager.getLogger(MySqlDishDao.class.getName());

    private static Dish mapDish(ResultSet rs) throws SQLException {
        return new Dish.Builder()
                .setId(rs.getLong("id"))
                .setName(rs.getString("name"))
                .setCategoryId(rs.getLong("category_id"))
                .setPrice(rs.getLong("price"))
                .setWeight(rs.getLong("weight"))
                .setDescription(rs.getString("description"))
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
            log.error(e);
            throw new DbException("Cannot getAllDishes", e);
        }
    }

    @Override
    public List<Dish> getDishesFromCategory(int categoryId) throws DbException {
        List<Dish> dishes = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_DISHES_FROM_CATEGORY)) {
            ps.setLong(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dishes.add(mapDish(rs));
                }
            }
            return dishes;
        } catch (SQLException e) {
            log.error(e);
            throw new DbException("Cannot getDishesFromCategory" + categoryId, e);
        }
    }

    @Override
    public List<Dish> getSortedDishesFromCategory(int categoryId, String sortBy) throws DbException {
        List<Dish> dishes = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_SORTED_DISHES_FROM_CATEGORY + sortBy)) {
            ps.setLong(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dishes.add(mapDish(rs));
                }
            }
            return dishes;
        } catch (SQLException e) {
            log.error(e);
            throw new DbException("Cannot getSortedDishesFromCategory" + categoryId, e);
        }
    }

    @Override
    public List<Dish> getSortedDishes(String sortBy) throws DbException {
        List<Dish> dishes = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_SORTED_DISHES + sortBy)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dishes.add(mapDish(rs));
                }
            }
            return dishes;
        } catch (SQLException e) {
            log.error(e);
            throw new DbException("Cannot getSortedDishes", e);
        }
    }
}
