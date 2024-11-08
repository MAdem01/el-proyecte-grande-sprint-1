package com.codecool.codekickfc.exceptions;

public class AlreadySignedUpException extends RuntimeException {
    public AlreadySignedUpException(long matchId) {
        super("Already signed up for this match: " + matchId);
    }
}
