package com.revined.revined.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RefreshTokenNotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = -6930731995730375023L;
    public RefreshTokenNotFoundException(String message) {
        super(message);
    }
}
