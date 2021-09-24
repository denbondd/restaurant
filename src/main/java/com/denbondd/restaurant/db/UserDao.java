package com.denbondd.restaurant.db;

import com.denbondd.restaurant.db.entity.User;
import com.denbondd.restaurant.exceptions.DbException;

public interface UserDao {

    User logIn(String login, char[] password) throws DbException;

    User signUp(String login, char[] password) throws DbException;

    boolean isLoginUnique(String login) throws DbException;

    User getUserByLogin(String login) throws DbException;

    void changePassword(String login, char[] newPass) throws DbException;
}
