package com.denbondd.restaurant.db;

import com.denbondd.restaurant.db.entity.Dish;
import com.denbondd.restaurant.exceptions.DbException;

import java.util.List;

public interface DishDao {

    List<Dish> getAllDishes() throws DbException;
    List<Dish> getDishesFromCategory(int categoryId) throws DbException;
    List<Dish> getSortedDishesFromCategory(int categoryId, String sortBy) throws DbException;
    List<Dish> getSortedDishes(String sortBy) throws DbException;
}
