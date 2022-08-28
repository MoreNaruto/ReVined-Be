package com.revined.revined.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -6003423841144871179L;

    private String email;
    private String password;
}
