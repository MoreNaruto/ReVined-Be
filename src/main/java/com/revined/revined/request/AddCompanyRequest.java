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
public class    AddCompanyRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -8864415490708795585L;

    @NotBlank(message = "name must be provided")
    private String name;

    @NotBlank(message = "description must be provided")
    private String description;
}
