package com.example.user_management_system.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom handler for AccessDeniedException.
 * This class is responsible for creating a custom JSON response when a user
 * is authenticated but does not have the necessary permissions to access a resource.
 *
 * @Component marks this class as a Spring component so it can be injected.
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        // Set the response status to 403 Forbidden
        response.setStatus(HttpStatus.FORBIDDEN.value());
        // Set the content type to application/json
        response.setContentType("application/json");

        // Create the JSON response body
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.FORBIDDEN.value());
        body.put("error", "Access Denied");
        body.put("message", "You do not have the required permissions to access this resource.");
        body.put("path", request.getServletPath());

        // Write the JSON response to the output stream
        response.getOutputStream().println(objectMapper.writeValueAsString(body));
    }
}
