package com.revined.revined.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidUuidException extends Exception{
    @Serial
    private static final long serialVersionUID = -1561242048084829637L;

    public InvalidUuidException(String message) {
        super(message);
    }
}
