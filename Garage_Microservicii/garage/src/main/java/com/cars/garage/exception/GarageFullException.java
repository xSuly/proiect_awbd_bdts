package com.cars.garage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class GarageFullException extends RuntimeException {
    public GarageFullException(String message) {
        super(message);
    }
}
