package com.denbondd.restaurant.db;

import com.denbondd.restaurant.db.entity.Category;
import com.denbondd.restaurant.exceptions.DbException;

import java.util.List;

public interface CategoryDao {
    List<Category> getAllCategories() throws DbException;
}
