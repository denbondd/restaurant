package com.denbondd.restaurant.db;

import com.denbondd.restaurant.db.entity.Receipt;
import com.denbondd.restaurant.exceptions.DbException;

import java.util.List;

public interface ReceiptDao {

    List<Receipt> getUserReceipts(long userId) throws DbException;
}
