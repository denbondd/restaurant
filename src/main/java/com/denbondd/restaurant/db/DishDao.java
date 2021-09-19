package com.denbondd.restaurant.db;

import com.denbondd.restaurant.db.entity.Dish;
import com.denbondd.restaurant.exceptions.DbException;

import java.util.List;

public interface DishDao {

    List<Dish> getAllDishes() throws DbException;
    List<Dish> getDishesFromCategory(int categoryId) throws DbException;
    List<Dish> getSortedDishesFromCategoryOnPage(int categoryId, String sortBy, int dishesInPage, int pageNum) throws DbException;
    List<Dish> getSortedDishesOnPage(String sortBy, int dishesInPage, int pageNum) throws DbException;

    int getDishesNumber() throws DbException;
    int getDishesNumberInCategory(int categoryId) throws DbException;
}
