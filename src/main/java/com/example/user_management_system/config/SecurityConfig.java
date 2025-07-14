package com.example.user_management_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration class for the application.
 *
 * @Configuration indicates that this class contains Spring configuration.
 * @EnableWebSecurity enables Spring Security's web security support.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Defines a PasswordEncoder bean to be used for encoding and validating passwords.
     * We use BCryptPasswordEncoder, which is a strong hashing algorithm.
     *
     * @return A PasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the security filter chain that applies to all HTTP requests.
     * This is where we define our authorization rules.
     *
     * @param http The HttpSecurity object to configure.
     * @return The configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection for this simple REST API.
                // For stateful, browser-based applications, CSRF protection is crucial.
                .csrf(csrf -> csrf.disable())
                // Configure authorization rules for HTTP requests.
                .authorizeHttpRequests(authz -> authz
                        // Allow unauthenticated access to the /public endpoint.
                        .requestMatchers("/public").permitAll()
                        // Allow access to /user endpoint for users with "USER" or "ADMIN" roles.
                        .requestMatchers("/user").hasAnyRole("USER", "ADMIN")
                        // Allow access to /admin and /users endpoints only for users with "ADMIN" role.
                        .requestMatchers("/admin", "/users").hasRole("ADMIN")
                        // All other requests must be authenticated.
                        .anyRequest().authenticated()
                )
                // Enable HTTP Basic Authentication.
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    /**
     * Defines an in-memory user store with two predefined users.
     * This is a simple way to manage users without a database for this assignment.
     *
     * @param passwordEncoder The PasswordEncoder to use for encoding passwords.
     * @return A UserDetailsService with the predefined users.
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // Create the 'intern' user.
        UserDetails internUser = User.builder()
                .username("intern")
                .password(passwordEncoder.encode("password123"))
                .roles("USER")
                .build();

        // Create the 'admin' user.
        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        // InMemoryUserDetailsManager is a non-persistent implementation of UserDetailsService.
        return new InMemoryUserDetailsManager(internUser, adminUser);
    }
}
