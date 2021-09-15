package com.enigma.zerowaste.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor

public class CustomerSearchDTO {
    private String searchCustomerName;
    private String searchCustomerPhone;
    private String searchCustomerEmail;
}

