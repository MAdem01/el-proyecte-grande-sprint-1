package com.codecool.codekickfc.exceptions;

public class DatabaseAccessException extends RuntimeException {
    public DatabaseAccessException(Throwable cause) {
        super("Unable to access database", cause);
    }
}
