package com.revined.revined.utils;

import com.revined.revined.config.JwtTokenUtil;
import com.revined.revined.exception.InvalidRequestTokenHeaderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

@Component
public class TokenHandler implements Serializable {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Serial
    private static final long serialVersionUID = -2203932289683046039L;

    public String getUsernameFromJwtToken(String requestTokenHeader)
            throws InvalidRequestTokenHeaderException {
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String jwtToken = requestTokenHeader.substring(7);
            return jwtTokenUtil.getUsernameFromToken(jwtToken);
        } else {
            throw new InvalidRequestTokenHeaderException("Provided request authorization is not valid");
        }
    }
}
