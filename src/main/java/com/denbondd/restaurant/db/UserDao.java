package com.denbondd.restaurant.db;

import com.denbondd.restaurant.db.entity.User;
import com.denbondd.restaurant.exceptions.DbException;

import java.util.List;

public interface UserDao {

    /**
     * Hash password and getting user from db. If no user returns, then wrong
     * login or password was entered and function return null object.
     * Otherwise, return user object
     * @param login login of user
     * @param password original(not hashed) password in char array
     * @return null if there isn't user with this login and password
     * or user object
     */
    User logIn(String login, char[] password) throws DbException;

    /**
     * Create new user in db. Then gets user by login
     * from db for fully filled user object (with id, default roleId and createDate).
     * @see UserDao#getUserByLogin(String)
     * @param login new user login
     * @param password original(not hashed) password in char array
     * @return new user object
     */
    User signUp(String login, char[] password) throws DbException;

    /**
     * Get user by login. If user is null, then there isn't
     * user with this login and it's unique
     * @param login login to check
     */
    boolean isLoginUnique(String login) throws DbException;

    /**
     * Gets user by login
     * @param login users login
     * @return null if there isn't user with this login
     * or user object
     */
    User getUserByLogin(String login) throws DbException;

    /**
     * Change users login in db
     * @param userId id of user
     * @param newPass original(not hashed) password in char array\
     */
    void changePassword(long userId, char[] newPass) throws DbException;

    /**
     * Get all users from db
     * @return list of users
     */
    List<User> getAllUsers() throws DbException;

    /**
     * Change user role in db
     * @param userId id of user
     * @param roleId new role id
     */
    void changeRole(long userId, int roleId) throws DbException;

    /**
     * Delete user from db
     * @implNote deleted user doesn't get any notifications
     * if he/she is currently logged in and stored in session on browser
     * @param userId is of user
     */
    void deleteUser(long userId) throws DbException;
}
