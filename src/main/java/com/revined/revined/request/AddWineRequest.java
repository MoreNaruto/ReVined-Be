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
public class AddWineRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -7699524624965973237L;

    @NotBlank(message = "name must be provided")
    private String name;

    private String description;
    private String alohaCode;
    private String color;
    @NotBlank(message = "producer must be provided")
    private String producer;
    @NotBlank(message = "the year must be provided")
    private String vintage;
    private String[] grapes;
    private String[] aromas;
    private String effervescence;
    private String country;
    private String region;
    private String subRegion;
    private String farmingPractices;
    private String body;
    private String photoLink;
    private String[] foodPairing;
}
