package com.revined.revined.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenRefreshRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 4867613225918743267L;

    @NotBlank(message = "refresh token must be provided")
    private String refreshToken;
}
