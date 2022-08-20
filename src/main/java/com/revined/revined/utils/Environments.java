package com.revined.revined.utils;

import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

@Component
public class Environments implements Serializable {
    @Serial
    private static final long serialVersionUID = -1320185165626007488L;

    public String getVariable(String key) {
        return System.getenv(key);
    }
}
