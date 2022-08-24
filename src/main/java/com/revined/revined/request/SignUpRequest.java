package com.revined.revined.request;

import com.revined.revined.model.enums.Roles;
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
public class SignUpRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 5926468583005150707L;

    @NotBlank(message = "email must be provided")
    private String email;

    @NotBlank(message = "password must be provided")
    private String password;

    @NotBlank(message = "matched password must be provided")
    private String matchPassword;

    @NotBlank(message = "first name must be provided")
    private String firstName;

    @NotBlank(message = "last name must be provided")
    private String lastName;
    private Roles role;
}
