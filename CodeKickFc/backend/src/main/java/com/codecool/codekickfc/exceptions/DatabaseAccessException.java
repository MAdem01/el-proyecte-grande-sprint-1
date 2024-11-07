package com.codecool.codekickfc.exceptions;

public class DatabaseAccessException extends RuntimeException {
    public DatabaseAccessException() {
        super("Unable to access database");
    }

    public DatabaseAccessException(Throwable cause) {
        super("Unable to access database", cause);
    }
}
