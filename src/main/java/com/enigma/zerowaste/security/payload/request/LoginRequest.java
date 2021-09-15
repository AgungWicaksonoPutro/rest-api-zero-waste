package com.enigma.zerowaste.security.payload.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC)
public class LoginRequest {
    @NotBlank
    private String userName;
    @NotBlank
    private String userPassword;

}
