package com.denbondd.restaurant.db;

import com.denbondd.restaurant.db.entity.Dish;
import com.denbondd.restaurant.exceptions.DbException;

import java.util.Map;

public interface CartDao {
    void addDishToCart(long userId, long dishId, int count) throws DbException;

    Map<Dish, Integer> getCart(long userId) throws DbException;
    void makeAnOrder(long userId, Map<Dish, Integer> cart) throws DbException;
}
