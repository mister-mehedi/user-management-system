package com.example.user_management_system.repository;

import com.example.user_management_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the User entity.
 *
 * This interface extends JpaRepository, which provides a rich set of CRUD (Create, Read, Update, Delete)
 * operations for the User entity out of the box.
 *
 * @Repository annotation marks this interface as a Spring component, making it eligible for
 * dependency injection.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Custom query method to find a user by their username.
     * Spring Data JPA automatically implements this method based on its name.
     * "findByUsername" translates to a "SELECT * FROM users WHERE username = ?" query.
     *
     * @param username The username to search for.
     * @return An Optional containing the User if found, or an empty Optional otherwise.
     * Using Optional is a good practice to handle cases where a user might not exist.
     */
    Optional<User> findByUsername(String username);
}
