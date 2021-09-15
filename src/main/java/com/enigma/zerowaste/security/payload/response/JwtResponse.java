package com.enigma.zerowaste.security.payload.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String id;
    private String customerName;
    private String phone;
    private String userName;
    private String email;
    private String password;
    private List<String> role;

    public JwtResponse(String token, String id, String customerName, String phone, String userName, String email, List<String> role) {
        this.token = token;
        this.id = id;
        this.customerName = customerName;
        this.phone = phone;
        this.userName = userName;
        this.email = email;
        this.role = role;
    }
}
