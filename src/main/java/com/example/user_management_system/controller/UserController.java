package com.example.user_management_system.controller;

import com.example.user_management_system.entity.User;
import com.example.user_management_system.exception.ResourceConflictException;
import com.example.user_management_system.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for handling user-related API endpoints.
 *
 * @RestController combines @Controller and @ResponseBody, simplifying the creation
 * of RESTful web services.
 */
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor-based dependency injection.
     * This is the recommended way to inject dependencies in Spring.
     *
     * @param userRepository The repository for user data access.
     * @param passwordEncoder The encoder for hashing passwords.
     */
    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Handles GET requests to the /public endpoint.
     * This endpoint is accessible to everyone without authentication.
     *
     * @return A simple string message.
     */
    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint";
    }

    /**
     * Handles GET requests to the /user endpoint.
     * Accessible only by authenticated users with "USER" or "ADMIN" roles.
     *
     * @return A simple string message.
     */
    @GetMapping("/user")
    public String userEndpoint() {
        return "This is a user endpoint";
    }

    /**
     * Handles GET requests to the /admin endpoint.
     * Accessible only by authenticated users with the "ADMIN" role.
     *
     * @return A simple string message.
     */
    @GetMapping("/admin")
    public String adminEndpoint() {
        return "This is an admin endpoint";
    }

    /**
     * Handles POST requests to the /users endpoint to create a new user.
     * This endpoint is accessible only by users with the "ADMIN" role.
     * The user details are passed in the request body as JSON.
     * The @Valid annotation triggers the validation rules on the newUser object.
     * If validation fails, it will throw a MethodArgumentNotValidException.
     *
     * @param newUser The User object to be created.
     * @return A ResponseEntity containing the created User and HTTP status 201 (Created).
     */
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User newUser) {
        // Check if username or email already exists (we'll handle this better in the next step)
        if (userRepository.findByUsername(newUser.getUsername()).isPresent()) {
            throw new ResourceConflictException("Username '" + newUser.getUsername() + "' is already taken.");
        }

        // If no exception is thrown, we proceed to create the user.
        // Before saving, encode the plain-text password.
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        // Save the new user to the database.
        User savedUser = userRepository.save(newUser);
        // Return the saved user in the response with a 201 Created status.
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}

