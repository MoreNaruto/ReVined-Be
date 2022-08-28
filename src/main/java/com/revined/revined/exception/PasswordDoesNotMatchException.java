package com.revined.revined.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordDoesNotMatchException extends Exception {
    @Serial
    private static final long serialVersionUID = 8256278440750968707L;
    public PasswordDoesNotMatchException(String errorMessage){
        super(errorMessage);
    }
}
