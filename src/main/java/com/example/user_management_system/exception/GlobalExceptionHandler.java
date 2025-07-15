package com.example.user_management_system.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A global exception handler for the application.
 *
 * @ControllerAdvice allows this class to be shared across all @Controllers to handle exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles validation errors from @Valid annotation.
     * This method is automatically called when a validation rule is violated.
     *
     * @param ex      The exception thrown.
     * @param headers The HTTP headers.
     * @param status  The HTTP status.
     * @param request The current web request.
     * @return A ResponseEntity with a structured error message.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());

        // Get all validation errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }

    /**
     * Handles our custom ResourceConflictException.
     *
     * @param ex The exception thrown.
     * @return A ResponseEntity with a structured error message.
     */
    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<Object> handleResourceConflictException(ResourceConflictException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    /**
     * Handles database integrity constraint violations.
     * This is triggered when a unique constraint (e.g., for username or email) is violated
     * at the database level.
     *
     * @param ex The exception thrown by the database.
     * @return A ResponseEntity with a user-friendly error message.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.CONFLICT.value());

        // Provide a more specific message if possible
        if (ex.getMostSpecificCause().getMessage().contains("users_username_key")) {
            body.put("error", "This username is already taken.");
        } else if (ex.getMostSpecificCause().getMessage().contains("users_email_key")) {
            body.put("error", "This email is already registered.");
        } else {
            body.put("error", "A database constraint was violated.");
        }

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }


}