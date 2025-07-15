package com.example.user_management_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception for handling resource conflicts, such as a user trying to register
 * with a username or email that already exists.
 *
 * @ResponseStatus(HttpStatus.CONFLICT) tells Spring to return a 409 Conflict status
 * when this exception is thrown.
 */
//@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceConflictException extends RuntimeException {

    public ResourceConflictException(String message) {
        super(message);
    }
}
