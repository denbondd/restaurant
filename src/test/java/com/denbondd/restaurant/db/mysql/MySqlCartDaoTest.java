package com.denbondd.restaurant.db.mysql;

import com.denbondd.restaurant.db.ConnectionPool;
import com.denbondd.restaurant.db.Dao;
import com.denbondd.restaurant.db.entity.Dish;
import com.denbondd.restaurant.exceptions.DbException;
import com.denbondd.restaurant.util.SqlUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.*;

class MySqlCartDaoTest {

    ResultSet resultSet = Mockito.mock(ResultSet.class);
    PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
    Connection connection = Mockito.mock(Connection.class);
    ConnectionPool connectionPool = Mockito.mock(ConnectionPool.class);

    @Test
    void addDishToCart() throws SQLException {
        try {
            Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
            Mockito.when(connection.prepareStatement(SqlUtils.ADD_DISH_TO_CART)).thenReturn(preparedStatement);
            Mockito.when(connectionPool.getConnection()).thenReturn(connection);
            try (MockedStatic<ConnectionPool> cpMock = Mockito.mockStatic(ConnectionPool.class)) {
                cpMock.when(ConnectionPool::getInstance).thenReturn(connectionPool);
                Dao.getDao().getCartDao().addDishToCart(1, 1, 1);
            }
        } catch (DbException e) {
            fail();
        }
    }

    @Test
    void getCart() throws SQLException, DbException {
        Dish expected = MySqlDishDaoTest.getTestDishes(1).get(0);
        Utils.mockResultSetForDish(expected, resultSet);
        Mockito.when(resultSet.getInt("count")).thenReturn(1);
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        mockOtherFields(SqlUtils.GET_CART);
        try (MockedStatic<ConnectionPool> cpMock = Mockito.mockStatic(ConnectionPool.class)) {
            cpMock.when(ConnectionPool::getInstance).thenReturn(connectionPool);

            Map<Dish, Integer> lActual = Dao.getDao().getCartDao().getCart(1);
            assertEquals(lActual.size(), 1);
            for (Map.Entry<Dish, Integer> entry : lActual.entrySet()) {
                Utils.assertFullyEqualsDishes(expected, entry.getKey());
                assertEquals(1, entry.getValue());
            }
        }
    }

    @Test
    void removeDishFromCart() throws SQLException, DbException {
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        Mockito.when(connection.prepareStatement(SqlUtils.REMOVE_DISH_FROM_CART)).thenReturn(preparedStatement);
        Mockito.when(connectionPool.getConnection()).thenReturn(connection);
        try (MockedStatic<ConnectionPool> cpMock = Mockito.mockStatic(ConnectionPool.class)) {
            cpMock.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            Dao.getDao().getCartDao().removeDishFromCart(1, 1);
        }
    }

    @Test
    void makeAnOrder() throws SQLException, DbException {
        Mockito.when(resultSet.getLong(1)).thenReturn(1L);
        Mockito.when(resultSet.next()).thenReturn(true);
        Mockito.when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        Mockito.when(connection.prepareStatement(SqlUtils.REMOVE_DISH_FROM_CART)).thenReturn(preparedStatement);
        Mockito.when(connection.prepareStatement(SqlUtils.ADD_RECEIPT, Statement.RETURN_GENERATED_KEYS)).thenReturn(preparedStatement);
        Mockito.when(connection.prepareStatement(SqlUtils.ADD_RECEIPT_HAS_DISH)).thenReturn(preparedStatement);
        Mockito.when(connectionPool.getConnection()).thenReturn(connection);
        try (MockedStatic<ConnectionPool> cpMock = Mockito.mockStatic(ConnectionPool.class)) {
            cpMock.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            Map<Dish, Integer> dishes = new HashMap<>();
            dishes.put(MySqlDishDaoTest.getTestDishes(1).get(0), 1);
            Dao.getDao().getCartDao().makeAnOrder(1, dishes);
        }
    }

    @Test
        //if exception throws - test won't pass
    void cleanCart() throws SQLException, DbException {
        Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
        Mockito.when(connection.prepareStatement(SqlUtils.CLEAN_USER_CART)).thenReturn(preparedStatement);
        Mockito.when(connectionPool.getConnection()).thenReturn(connection);
        try (MockedStatic<ConnectionPool> cpMock = Mockito.mockStatic(ConnectionPool.class)) {
            cpMock.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            Dao.getDao().getCartDao().cleanCart(1);
        }
    }

    private void mockOtherFields(String sqlQuery) throws SQLException {
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(connection.prepareStatement(sqlQuery)).thenReturn(preparedStatement);
        Mockito.when(connectionPool.getConnection()).thenReturn(connection);
    }
}