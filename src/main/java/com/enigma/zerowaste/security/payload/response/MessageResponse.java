package com.enigma.zerowaste.security.payload.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC) @Setter(AccessLevel.PUBLIC) @AllArgsConstructor
public class MessageResponse {
    private String message;

}
