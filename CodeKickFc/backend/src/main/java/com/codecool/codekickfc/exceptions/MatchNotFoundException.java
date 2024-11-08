package com.codecool.codekickfc.exceptions;

public class MatchNotFoundException extends RuntimeException {
    public MatchNotFoundException() {
        super("Match not found");
    }
}
