package com.denbondd.restaurant.db.mysql;

import com.denbondd.restaurant.db.Dao;
import com.denbondd.restaurant.db.UserDao;

public class MySqlDao extends Dao {

    @Override
    public UserDao getUserDao() {
        return new MySqlUserDao();
    }
}
