package com.denbondd.restaurant.db.mysql;

import com.denbondd.restaurant.db.ConnectionPool;
import com.denbondd.restaurant.db.Dao;
import com.denbondd.restaurant.db.entity.Dish;
import com.denbondd.restaurant.exceptions.DbException;
import com.denbondd.restaurant.util.SqlUtils;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MySqlDishDaoTest {

    ResultSet resultSet = Mockito.mock(ResultSet.class);
    PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
    Connection connection = Mockito.mock(Connection.class);
    ConnectionPool connectionPool = Mockito.mock(ConnectionPool.class);

    @Test
    void getAllDishes() throws SQLException, DbException {
        List<Dish> answer = getTestDishes(1);
        Utils.mockResultSetForDish(answer.get(0), resultSet);
        mockOtherFields(SqlUtils.GET_ALL_DISHES);
        try (MockedStatic<ConnectionPool> cpMock = Mockito.mockStatic(ConnectionPool.class)) {
            cpMock.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            List<Dish> dishes = Dao.getDao().getDishDao().getAllDishes();

            assertEquals(dishes.size(), 1);
            Utils.assertFullyEqualsDishes(dishes.get(0), answer.get(0));
        }
    }

    @Test
    void getDishById() throws SQLException, DbException {
        Dish actualDish = getTestDishes(1).get(0);
        Utils.mockResultSetForDish(actualDish, resultSet);
        mockOtherFields(SqlUtils.GET_DISH_BY_ID);
        try (MockedStatic<ConnectionPool> cpMock = Mockito.mockStatic(ConnectionPool.class)) {
            cpMock.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            Dish dish = Dao.getDao().getDishDao().getDishById(1);
            Utils.assertFullyEqualsDishes(dish, actualDish);
        }
    }

    //all sorting and paginating happens in sql queries, so there isn't need to test them
    @Test
    void getSortedDishesFromCategoryOnPage() throws SQLException, DbException {
        Dish actualDish = getTestDishes(1).get(0);
        Utils.mockResultSetForDish(actualDish, resultSet);
        mockOtherFields(SqlUtils.GET_SORTED_DISHES_FROM_CATEGORY + "sortingField LIMIT 0, 5");
        try (MockedStatic<ConnectionPool> cpMock = Mockito.mockStatic(ConnectionPool.class)) {
            cpMock.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            Dish dish = Dao.getDao().getDishDao()
                    .getSortedDishesFromCategoryOnPage(1, "sortingField", 5, 0).get(0);
            Utils.assertFullyEqualsDishes(dish, actualDish);
        }
    }

    @Test
    void getSortedDishesOnPage() throws SQLException, DbException {
        Dish actualDish = getTestDishes(1).get(0);
        Utils.mockResultSetForDish(actualDish, resultSet);
        mockOtherFields(SqlUtils.GET_SORTED_DISHES + "sortingField LIMIT 0, 5");
        try (MockedStatic<ConnectionPool> cpMock = Mockito.mockStatic(ConnectionPool.class)) {
            cpMock.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            Dish dish = Dao.getDao().getDishDao()
                    .getSortedDishesOnPage("sortingField", 5, 0).get(0);
            Utils.assertFullyEqualsDishes(dish, actualDish);
        }
    }

    @Test
    void getDishesNumber() throws SQLException, DbException {
        int expected = 5;
        Mockito.when(resultSet.getInt(1)).thenReturn(expected);
        mockOtherFields(SqlUtils.GET_DISHES_COUNT);
        try (MockedStatic<ConnectionPool> cpMock = Mockito.mockStatic(ConnectionPool.class)) {
            cpMock.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            int actual = Dao.getDao().getDishDao().getDishesNumber();
            assertEquals(expected, actual);
        }
    }

    @Test
    void getDishesNumberInCategory() throws SQLException, DbException {
        int expected = 5;
        Mockito.when(resultSet.getInt(1)).thenReturn(expected);
        mockOtherFields(SqlUtils.GET_DISHES_COUNT_IN_CATEGORY);
        try (MockedStatic<ConnectionPool> cpMock = Mockito.mockStatic(ConnectionPool.class)) {
            cpMock.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            int actual = Dao.getDao().getDishDao().getDishesNumberInCategory(1);
            assertEquals(expected, actual);
        }
    }

    private void mockOtherFields(String sqlQuery) throws SQLException {
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(connection.prepareStatement(sqlQuery)).thenReturn(preparedStatement);
        Mockito.when(connectionPool.getConnection()).thenReturn(connection);
    }

    public static List<Dish> getTestDishes(long... ids) {
        List<Dish> dishes = new ArrayList<>();
        for (long id : ids) {
            dishes.add(new Dish.Builder()
                    .setId(id)
                    .setName("DishName" + id)
                    .setCategoryId(1)
                    .setPrice(1000)
                    .setWeight(100)
                    .setDescription("DishDescription" + id)
                    .getDish());
        }
        return dishes;
    }

}