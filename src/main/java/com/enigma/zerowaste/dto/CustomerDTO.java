package com.enigma.zerowaste.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
public class CustomerDTO {
    private String id;
    private String customerName;
    private String phoneNumber;
    private String userName;
    private String email;
    private String password;
}
