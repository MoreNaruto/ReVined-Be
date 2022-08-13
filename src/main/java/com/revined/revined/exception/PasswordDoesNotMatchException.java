package com.revined.revined.exception;

public class PasswordDoesNotMatchException extends Exception {
    public PasswordDoesNotMatchException(String errorMessage){
        super(errorMessage);
    }
}
