package com.spnsolo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public final class ScooterExceptions {

    private ScooterExceptions() {
    }

    public static ResponseStatusException scooterNotFound(long messageId) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Scooter with id " + messageId + " was not found");
    }


}
