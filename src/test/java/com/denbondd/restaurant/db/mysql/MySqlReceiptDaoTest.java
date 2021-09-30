package com.denbondd.restaurant.db.mysql;

import com.denbondd.restaurant.db.ConnectionPool;
import com.denbondd.restaurant.db.Dao;
import com.denbondd.restaurant.db.entity.Receipt;
import com.denbondd.restaurant.exceptions.DbException;
import com.denbondd.restaurant.util.SqlUtils;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MySqlReceiptDaoTest {

    ResultSet resultSet = Mockito.mock(ResultSet.class);
    PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
    Connection connection = Mockito.mock(Connection.class);
    ConnectionPool connectionPool = Mockito.mock(ConnectionPool.class);

    @Test
    void getUserReceipts() throws SQLException, DbException {
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(connection.prepareStatement(SqlUtils.GET_USER_RECEIPTS)).thenReturn(preparedStatement);
        Mockito.when(connection.prepareStatement(SqlUtils.GET_RECEIPT_DISHES)).thenReturn(preparedStatement);
        Mockito.when(connectionPool.getConnection()).thenReturn(connection);
        try (MockedStatic<ConnectionPool> cpMock = Mockito.mockStatic(ConnectionPool.class)) {
            cpMock.when(ConnectionPool::getInstance).thenReturn(connectionPool);

            Dao.getDao().getReceiptDao().getUserReceipts(1);
        }
    }

    @Test
    void getAllReceipts() throws SQLException, DbException {
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(connection.prepareStatement(SqlUtils.GET_ALL_RECEIPTS)).thenReturn(preparedStatement);
        Mockito.when(connection.prepareStatement(SqlUtils.GET_RECEIPT_DISHES)).thenReturn(preparedStatement);
        Mockito.when(connectionPool.getConnection()).thenReturn(connection);
        try (MockedStatic<ConnectionPool> cpMock = Mockito.mockStatic(ConnectionPool.class)) {
            cpMock.when(ConnectionPool::getInstance).thenReturn(connectionPool);

            Dao.getDao().getReceiptDao().getAllReceipts();
        }
    }

    @Test
    void getAllReceiptsAcceptedBy() throws SQLException, DbException {
        Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        Mockito.when(preparedStatement.executeQuery()).thenReturn(resultSet);
        Mockito.when(connection.prepareStatement(SqlUtils.GET_RECEIPTS_APPROVED_BY)).thenReturn(preparedStatement);
        Mockito.when(connection.prepareStatement(SqlUtils.GET_RECEIPT_DISHES)).thenReturn(preparedStatement);
        Mockito.when(connectionPool.getConnection()).thenReturn(connection);
        try (MockedStatic<ConnectionPool> cpMock = Mockito.mockStatic(ConnectionPool.class)) {
            cpMock.when(ConnectionPool::getInstance).thenReturn(connectionPool);

            List<Receipt> receiptList = Dao.getDao().getReceiptDao().getAllReceiptsAcceptedBy(1);
            receiptList.forEach(System.out::println);
        }
    }
}