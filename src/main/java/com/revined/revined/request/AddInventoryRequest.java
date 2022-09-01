package com.revined.revined.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddInventoryRequest implements Serializable{
    @Serial
    private static final long serialVersionUID = 3436643449089200496L;

    @NotBlank(message = "name must be provided")
    private String name;

    private String description;

    private UUID companyId;
}
