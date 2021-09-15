package com.enigma.zerowaste.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DataOutOfStock extends RuntimeException {
    public DataOutOfStock(String message) {
        super(message);
    }
}
