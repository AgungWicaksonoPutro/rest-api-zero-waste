package com.enigma.zerowaste.utils;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private String message;
    private T data;
    private Boolean status;
    private List<String> messages = new ArrayList<>();

}

