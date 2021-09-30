package com.denbondd.restaurant.db.mysql;

import com.denbondd.restaurant.db.ConnectionPool;
import com.denbondd.restaurant.db.Dao;
import com.denbondd.restaurant.db.entity.Category;
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

import static org.junit.jupiter.api.Assertions.*;

class MySqlCategoryDaoTest {

    ResultSet resultSet = Mockito.mock(ResultSet.class);
    PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
    Connection connection = Mockito.mock(Connection.class);
    ConnectionPool connectionPool = Mockito.mock(ConnectionPool.class);

    @Test
    void getAllCategories() throws SQLException, DbException {
        List<Category> expected = new ArrayList<>();
        expected.add(new Category.Builder().setId(1).setName("Name1").getCategory());

        Mockito.when(resultSet.getLong("id")).thenReturn(1L);
        Mockito.when(resultSet.getString("name")).thenReturn("Name1");
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(connection.prepareStatement(SqlUtils.GET_ALL_CATEGORIES)).thenReturn(preparedStatement);
        Mockito.when(connectionPool.getConnection()).thenReturn(connection);
        try (MockedStatic<ConnectionPool> cpMock = Mockito.mockStatic(ConnectionPool.class)) {
            cpMock.when(ConnectionPool::getInstance).thenReturn(connectionPool);

            List<Category> actual = Dao.getDao().getCategoryDao().getAllCategories();
            assertEquals(expected.size(), actual.size());
            Category expCat = expected.get(0);
            Category actCat = actual.get(0);
            assertEquals(expCat.getId(), actCat.getId());
            assertEquals(expCat.getName(), actCat.getName());
        }
    }
}