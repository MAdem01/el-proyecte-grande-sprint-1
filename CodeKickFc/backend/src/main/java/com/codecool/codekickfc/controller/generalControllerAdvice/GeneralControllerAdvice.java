package com.codecool.codekickfc.controller.generalControllerAdvice;

import com.codecool.codekickfc.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GeneralControllerAdvice {

    @ResponseBody
    @ExceptionHandler(DatabaseAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleDatabaseAccessException(DatabaseAccessException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFoundException(UserNotFoundException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(MatchNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleMatchNotFoundException(MatchNotFoundException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(FootballPitchNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlePitchNotFoundException(FootballPitchNotFoundException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(AlreadySignedUpException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleAlreadySignedUpException(AlreadySignedUpException e) {
        return e.getMessage();
    }
}
