package com.revined.revined.request;

import jakarta.validation.constraints.NotBlank;
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
public class TokenRefreshRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 8246236996829524393L;

    @NotBlank(message = "refresh token must be provided")
    private String refreshToken;
}
