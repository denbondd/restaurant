package com.denbondd.restaurant.db;

import com.denbondd.restaurant.db.entity.Receipt;
import com.denbondd.restaurant.exceptions.DbException;

import java.util.List;

public interface ReceiptDao {

    /**
     * Get all user receipts
     * @param userId id of user
     * @return list of receipts with its dishes
     */
    List<Receipt> getUserReceipts(long userId) throws DbException;

    /**
     * Get all receipts in receipt table
     * @return list of receipts with its dishes
     */
    List<Receipt> getAllReceipts() throws DbException;

    /**
     * Get all receipts accepted by this manager
     * @param managerId id of manager
     * @return list of receipts with its dishes
     */
    List<Receipt> getAllReceiptsAcceptedBy(long managerId) throws DbException;

    /**
     * Get all receipts with status NEW in receipt table
     * @return list of receipts with its dishes
     */
    List<Receipt> getNotApproved() throws DbException;

    /**
     * Change status of receipt
     * @param receiptId id of receipt
     * @param statusId new id of status
     * @param managerId id of manager that changed it
     */
    void changeStatus(long receiptId, long statusId, long managerId) throws DbException;
}
