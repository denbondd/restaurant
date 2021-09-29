package com.denbondd.restaurant.exceptions;

/**
 * Custom checked exception for database errors
 */
public class DbException extends Exception {

    public DbException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbException(String message) {
        super(message);
    }
}
