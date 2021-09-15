package com.enigma.zerowaste.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor

public class AddressSearchDTO {
    private String searchAddressName;
    private String searchAddressPhone;
    private String searchAddressDescriptions;
}
