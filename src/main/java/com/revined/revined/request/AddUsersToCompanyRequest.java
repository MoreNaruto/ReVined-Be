package com.revined.revined.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddUsersToCompanyRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 6392190184551045035L;

    @NotEmpty(message = "Need to provide user ids")
    private UUID[] users;
}
