package com.enigma.zerowaste.security.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC)
public class SignupRequest {
    @NotBlank
    private String customerName;
    @NotBlank
    private String phone;
    @NotBlank
    private String userName;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private Set<String> role;

}
