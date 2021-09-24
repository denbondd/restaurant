package com.denbondd.restaurant.db.mysql;

import com.denbondd.restaurant.db.ConnectionPool;
import com.denbondd.restaurant.db.ReceiptDao;
import com.denbondd.restaurant.db.entity.Receipt;
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

public class MySqlReceiptDao implements ReceiptDao {

    private static final Logger log = LogManager.getLogger(MySqlReceiptDao.class.getName());

    private static Receipt mapReceipt(ResultSet rs) throws SQLException {
        return new Receipt.Builder()
                .setId(rs.getLong("id"))
                .setUserId(rs.getLong("user_id"))
                .setStatusId(rs.getLong("status_id"))
                .setTotal(rs.getInt("total"))
                .setManagerId(rs.getLong("manager_id"))
                .setCreateDate(rs.getTimestamp("create_date"))
                .getReceipt();
    }

    @Override
    public List<Receipt> getUserReceipts(long userId) throws DbException {
        List<Receipt> receipts = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_USER_RECEIPTS)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Receipt receipt = mapReceipt(rs);
                    receipt.setDishes(getReceiptDishes(receipt.getId()));
                    receipts.add(receipt);
                }
            }
            return receipts;
        } catch (SQLException e) {
            throw new DbException("Cannot getUserReceipts", e);
        }
    }

    private List<Receipt.Dish> getReceiptDishes(long receiptId) throws SQLException {
        List<Receipt.Dish> dishes = new ArrayList<>();
        try (Connection c = ConnectionPool.getInstance().getConnection();
             PreparedStatement ps = c.prepareStatement(SqlUtils.GET_RECEIPT_DISHES)) {
            ps.setLong(1, receiptId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Receipt.Dish dish = new Receipt.Dish.Builder()
                            .setId(rs.getLong("id"))
                            .setName(rs.getString("name"))
                            .setPrice(rs.getInt("price"))
                            .setCount(rs.getInt("count"))
                            .getDish();
                    dishes.add(dish);
                }
            }
        }
        return dishes;
    }
}
