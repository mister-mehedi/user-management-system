package com.example.user_management_system.entity;

import jakarta.persistence.*;
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
     * @Column specifies the mapping to the database column.
     * 'unique = true' ensures that every username in the database is distinct.
     * 'nullable = false' means this field cannot be empty.
     */
    @Column(unique = true, nullable = false)
    private String username;

    /**
     * The password for the user.
     * 'nullable = false' ensures that a password is always provided.
     * Note: This will be stored in an encoded format, not as plain text.
     */
    @Column(nullable = false)
    private String password;

    /**
     * The role assigned to the user (e.g., "USER", "ADMIN").
     * 'nullable = false' ensures every user has a role.
     */
    @Column(nullable = false)
    private String role;
}
