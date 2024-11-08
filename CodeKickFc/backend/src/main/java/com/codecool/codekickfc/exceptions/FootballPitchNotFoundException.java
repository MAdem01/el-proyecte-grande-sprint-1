package com.codecool.codekickfc.exceptions;

public class FootballPitchNotFoundException extends RuntimeException {
    public FootballPitchNotFoundException() {
        super("Pitch not found");
    }
}
