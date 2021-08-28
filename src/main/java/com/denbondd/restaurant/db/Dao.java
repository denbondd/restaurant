package com.denbondd.restaurant.db;

import com.denbondd.restaurant.db.mysql.MySqlDao;

public abstract class Dao {

    public abstract UserDao getUserDao();

    public static Dao getDao() {
        //check what database to return
        return new MySqlDao();
    }
}
