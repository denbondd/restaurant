package com.denbondd.restaurant.db;

import com.denbondd.restaurant.db.entity.Dish;
import com.denbondd.restaurant.exceptions.DbException;

import java.util.Map;

public interface CartDao {

    /**
     * Add cart_has_dish to db
     * @param userId id of user
     * @param dishId id of dish to add
     * @param count count of portions
     */
    void addDishToCart(long userId, long dishId, int count) throws DbException;

    /**
     * Get all cart_has_dish of user from db
     * @param userId id of user
     * @return Map(Dish, it's count)
     */
    Map<Dish, Integer> getCart(long userId) throws DbException;

    /**
     * Make receipt in db and add receipt_has_dish for every dish in cart
     * @param userId id user
     * @param cart list of dishes to add
     */
    void makeAnOrder(long userId, Map<Dish, Integer> cart) throws DbException;

    /**
     * Remove one cart_has_dish from user cart
     * @param userId id of user
     * @param dishId id of dish
     */
    void removeDishFromCart(long userId, long dishId) throws DbException;

    /**
     * Remove all cart_has_dish of user
     * @param userId id of user
     */
    void cleanCart(long userId) throws DbException;
}
