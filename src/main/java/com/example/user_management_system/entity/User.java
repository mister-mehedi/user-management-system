package com.example.user_management_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the User entity and maps to the "users" table in the database.
 *
 * We are using Lombok annotations to reduce boilerplate code:
 * @Data: Generates getters, setters, toString(), equals(), and hashCode() methods.
 * @NoArgsConstructor: Generates a no-argument constructor.
 * @AllArgsConstructor: Generates a constructor with all arguments.
 */
@Entity
@Table(name = "users") // Specifies the table name in the database
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * The primary key for the user entity.
     * @Id marks this field as the primary key.
     * @GeneratedValue specifies that the ID should be generated automatically.
     * GenerationType.IDENTITY is suitable for auto-incrementing columns in PostgreSQL.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The username of the user.
     * @NotBlank ensures the username is not null and not just whitespace.
     * @Size ensures the username is between 3 and 20 characters.
     * @Column specifies the mapping to the database column.
     * 'unique = true' ensures that every username in the database is distinct.
     * 'nullable = false' means this field cannot be empty.
     */
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * A new field for the user's email.
     * @Email ensures the value is a well-formed email address.
     * @NotBlank ensures it's not empty.
     */
//    @NotBlank(message = "Email cannot be blank")
//    @Email(message = "Please provide a valid email address")
//    @Column(unique = true, nullable = false)
//    private String email;

    /**
     * The password for the user.
     * @NotBlank ensures the password is not null and not just whitespace.
     * @Size ensures the password is at least 8 characters long.
     * 'nullable = false' ensures that a password is always provided.
     * Note: This will be stored in an encoded format, not as plain text.
     */
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 4, message = "Password must be at least 4 characters long")
    @Column(nullable = false)
    private String password;

    /**
     * The role assigned to the user (e.g., "USER", "ADMIN").
     * @NotBlank ensures a role is always provided.
     * 'nullable = false' ensures every user has a role.
     */
    @NotBlank(message = "Role cannot be blank")
    @Column(nullable = false)
    private String role;
}
