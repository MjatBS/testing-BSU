package com.epam.ta.exceptions;

public class BadUserDataException extends Exception { 

    public BadUserDataException(String errorMessage) {
        super(errorMessage);
    }

    public BadUserDataException(){
        super("Incorrect user data");
    }
}