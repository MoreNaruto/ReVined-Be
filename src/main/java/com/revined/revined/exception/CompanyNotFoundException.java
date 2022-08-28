package com.revined.revined.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CompanyNotFoundException extends Exception {
    @Serial
    private static final long serialVersionUID = -5158421808926572842L;

    public CompanyNotFoundException(String message) {
        super(message);
    }
}
