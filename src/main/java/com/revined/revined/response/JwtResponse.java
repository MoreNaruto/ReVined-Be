package com.revined.revined.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@Builder
public class JwtResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -8091879091924046844L;
    private final String token;
    private final String refreshToken;
}
