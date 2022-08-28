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
public class LogoutRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -8076881707910103598L;

    private String email;
}
