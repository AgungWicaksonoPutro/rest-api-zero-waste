package com.enigma.zerowaste.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "phone number isrequired")
    private String phoneNumber;
    @NotBlank(message = "descriptionis required")
    private String descriptions;

    @Override
    public String toString() {
        return "AddressDTO{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", descriptions='" + descriptions + '\'' +
                '}';
    }
}
