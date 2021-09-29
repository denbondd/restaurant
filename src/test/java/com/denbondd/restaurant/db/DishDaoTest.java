package com.denbondd.restaurant.db;

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

class DishDaoTest {
    ResultSet resultSet = Mockito.mock(ResultSet.class);
    Connection connection = Mockito.mock(Connection.class);
    PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
    ConnectionPool connectionPool = Mockito.mock(ConnectionPool.class);

    @Test
    void getAllDishes() throws SQLException, DbException {
        List<Dish> answer = getTestDishes(1);

        mockResultSet(answer.get(0));
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(connection.prepareStatement(SqlUtils.GET_ALL_DISHES)).thenReturn(preparedStatement);
        Mockito.when(connectionPool.getConnection()).thenReturn(connection);

        try (MockedStatic<ConnectionPool> cpMock = Mockito.mockStatic(ConnectionPool.class)) {
            cpMock.when(ConnectionPool::getInstance).thenReturn(connectionPool);

            List<Dish> dishes = Dao.getDao().getDishDao().getAllDishes();
            assertEquals(dishes.size(), 3);
            for (int i = 0; i < dishes.size(); i++) {
                Dish dish = dishes.get(0);
                Dish actualDish = answer.get(0);
                assertFullyEqualsDishes(dish, actualDish);
            }
        }
    }

    private void assertFullyEqualsDishes(Dish dish, Dish actualDish) {
        assertEquals(dish.getId(), actualDish.getId());
        assertEquals(dish.getName(), actualDish.getName());
        assertEquals(dish.getCategoryId(), actualDish.getCategoryId());
        assertEquals(dish.getPrice(), actualDish.getPrice());
        assertEquals(dish.getWeight(), actualDish.getWeight());
        assertEquals(dish.getDescription(), actualDish.getDescription());
    }

    private void mockResultSet(Dish dish) throws SQLException {
        Mockito.when(resultSet.getString("description")).thenReturn(dish.getDescription());
        Mockito.when(resultSet.getLong("weight")).thenReturn(dish.getWeight());
        Mockito.when(resultSet.getLong("price")).thenReturn(dish.getPrice());
        Mockito.when(resultSet.getLong("category_id")).thenReturn(dish.getCategoryId());
        Mockito.when(resultSet.getString("name")).thenReturn(dish.getName());
        Mockito.when(resultSet.getLong("id")).thenReturn(dish.getId());
    }

    private List<Dish> getTestDishes(long... ids) {
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

    @Test
    void getDishById() throws SQLException, DbException {
        Dish actualDish = getTestDishes(1).get(0);

        mockResultSet(actualDish);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(connection.prepareStatement(SqlUtils.GET_DISH_BY_ID)).thenReturn(preparedStatement);
        Mockito.when(connectionPool.getConnection()).thenReturn(connection);

        try (MockedStatic<ConnectionPool> cpMock = Mockito.mockStatic(ConnectionPool.class)) {
            cpMock.when(ConnectionPool::getInstance).thenReturn(connectionPool);

            Dish dish = Dao.getDao().getDishDao().getDishById(1);
            assertFullyEqualsDishes(dish, actualDish);
        }
    }
}