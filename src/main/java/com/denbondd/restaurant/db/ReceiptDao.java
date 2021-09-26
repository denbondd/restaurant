package com.denbondd.restaurant.db;

import com.denbondd.restaurant.db.entity.Receipt;
import com.denbondd.restaurant.exceptions.DbException;

import java.util.List;

public interface ReceiptDao {

    List<Receipt> getUserReceipts(long userId) throws DbException;

    List<Receipt> getAllReceipts() throws DbException;
    List<Receipt> getAllReceiptsAcceptedBy(long managerId) throws DbException;
    List<Receipt> getNotApproved() throws DbException;
    void changeStatus(long receiptId, long statusId, long managerId) throws DbException;
}
