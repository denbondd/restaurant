package com.denbondd.restaurant.db;

import com.denbondd.restaurant.db.entity.Dish;
import com.denbondd.restaurant.db.entity.User;
import com.denbondd.restaurant.exceptions.DbException;

import java.util.Map;

public interface UserDao {

    User logIn(String login, char[] password) throws DbException;
    User signUp(String login, char[] password) throws DbException;
    boolean isLoginUnique(String login) throws DbException;
    User getUserByLogin(String login) throws DbException;

    void addDishToCart(long userId, long dishId, int count) throws DbException;

    Map<Dish, Integer> getCart(long userId) throws DbException;
}
