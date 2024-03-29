package com.revined.revined.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class WineAlreadyExistsException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 6592056749101773980L;
    public WineAlreadyExistsException(String message) {
        super(message);
    }
}
