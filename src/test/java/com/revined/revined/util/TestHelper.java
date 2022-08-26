package com.revined.revined.util;

import com.google.gson.Gson;

public class TestHelper {
    public static String asJsonString(final Object obj) {
        try {
            return new Gson().toJson(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
