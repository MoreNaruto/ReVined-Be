package com.revined.revined.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestTokenHeaderException extends Exception {
    @Serial
    private static final long serialVersionUID = 1003807110820146009L;

    public InvalidRequestTokenHeaderException(String message) {
        super(message);
    }
}
