package com.revined.revined.request;

import com.revined.revined.validator.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavePasswordRequest implements Serializable {
    @ValidPassword
    private String password;

    private  String token;

    @ValidPassword
    private String matchPassword;
}
