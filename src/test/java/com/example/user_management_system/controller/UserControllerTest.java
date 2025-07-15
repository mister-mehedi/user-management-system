package com.example.user_management_system.controller;

import com.example.user_management_system.entity.User;
import com.example.user_management_system.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the UserController.
 *
 * @SpringBootTest loads the full application context, ensuring all our configurations
 * (like SecurityConfig) are loaded exactly as they are when the app runs.
 * @AutoConfigureMockMvc gives us a configured MockMvc instance to perform requests.
 */
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // We only need to mock the UserRepository to prevent the tests from hitting the actual database.
    // The rest of the application context (Security, PasswordEncoder, etc.) is real.
    @MockBean
    private UserRepository userRepository;

    @Test
    void whenGetPublicEndpoint_thenSucceed() throws Exception {
        mockMvc.perform(get("/public"))
                .andExpect(status().isOk())
                .andExpect(content().string("This is a public endpoint"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER") // Simulates a request from a logged-in user with the "USER" role.
    void whenGetUserEndpointAsUser_thenSucceed() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(content().string("This is a user endpoint"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN") // Simulates a request from an "ADMIN" user.
    void whenGetAdminEndpointAsAdmin_thenSucceed() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string("This is an admin endpoint"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void whenGetAdminEndpointAsUser_thenIsForbidden() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    void whenGetAdminEndpointUnauthenticated_thenIsUnauthorized() throws Exception {
        // For unauthenticated requests, Spring Security redirects to a login page by default (302)
        // or returns 401 if configured for stateless auth. Let's check for 401.
        mockMvc.perform(get("/admin"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void whenCreateUserAsAdmin_thenSucceed() throws Exception {
        User newUser = new User(null, "testuser","password123", "USER");
        User savedUser = new User(1L, "testuser","some_encoded_password", "USER");

        // We only need to mock the repository methods that are called.
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        mockMvc.perform(post("/users")
                        .with(csrf()) // Include CSRF token for POST requests in tests
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void whenCreateUserAsUser_thenIsForbidden() throws Exception {
        User newUser = new User(null, "testuser","password123", "USER");

        mockMvc.perform(post("/users")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isForbidden());
    }
}