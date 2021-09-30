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

    public static Dish mapDish(ResultSet rs) throws SQLException {
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
    public Dish getDishById(long id) throws DbException {
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_DISH_BY_ID)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapDish(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new DbException("Cannot getDishById", e);
        }
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
            throw new DbException("Cannot getAllDishes", e);
        }
    }

    @Override
    public List<Dish> getSortedDishesFromCategoryOnPage(int categoryId, String sortBy, int dishesInPage, int pageNum) throws DbException {
        List<Dish> dishes = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(
                     SqlUtils.GET_SORTED_DISHES_FROM_CATEGORY + sortBy + " LIMIT " + pageNum * dishesInPage + ", " + dishesInPage)) {
            ps.setLong(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dishes.add(mapDish(rs));
                }
            }
            return dishes;
        } catch (SQLException e) {
            throw new DbException("Cannot getSortedDishesFromCategory" + categoryId, e);
        }
    }

    @Override
    public List<Dish> getSortedDishesOnPage(String sortBy, int dishesInPage, int pageNum) throws DbException {
        List<Dish> dishes = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(
                     SqlUtils.GET_SORTED_DISHES + sortBy + " LIMIT " + pageNum * dishesInPage + ", " + dishesInPage)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dishes.add(mapDish(rs));
                }
            }
            return dishes;
        } catch (SQLException e) {
            throw new DbException("Cannot getSortedDishes", e);
        }
    }

    @Override
    public int getDishesNumber() throws DbException {
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_DISHES_COUNT);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        } catch (SQLException e) {
            throw new DbException("Cannot getDishesNumber", e);
        }
    }

    @Override
    public int getDishesNumberInCategory(int categoryId) throws DbException {
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_DISHES_COUNT_IN_CATEGORY)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return -1;
            }
        } catch (SQLException e) {
            throw new DbException("Cannot getDishesNumberInCategory", e);
        }
    }
}
