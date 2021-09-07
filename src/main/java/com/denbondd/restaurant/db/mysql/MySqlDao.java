package com.denbondd.restaurant.db.mysql;

import com.denbondd.restaurant.db.CategoryDao;
import com.denbondd.restaurant.db.Dao;
import com.denbondd.restaurant.db.DishDao;
import com.denbondd.restaurant.db.UserDao;

public class MySqlDao extends Dao {

    @Override
    public UserDao getUserDao() {
        return new MySqlUserDao();
    }

    @Override
    public DishDao getDishDao() {
        return new MySqlDishDao();
    }

    @Override
    public CategoryDao getCategoryDao() {
        return new MySqlCategoryDao();
    }
}
