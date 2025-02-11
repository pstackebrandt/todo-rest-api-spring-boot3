package com.example.todo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides base information about the Todo List API when calling the root
 * endpoint.
 *
 * This endpoint is useful for: - Offering a welcome message. - Sharing API
 * details such as name, version, and description. - Highlighting important
 * endpoints such as /todos and the Swagger documentation.
 */
@RestController
public class RootController {

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getBaseInformation() {
        Map<String, Object> info = new HashMap<>();
        info.put("apiName", "Todo List API");
        info.put("version", "0.7.0");
        info.put("description", "A simple Todo List API built with Spring Boot 3 demonstrating REST API and OpenAPI documentation.");

        // Providing links to the main endpoints.
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("todos", "/todos");
        endpoints.put("swaggerDocumentation", "/swagger-ui/index.html");
        info.put("endpoints", endpoints);

        info.put("message", "Welcome to the Todo List API! Use the provided endpoints to interact with the service.");
        return info;
    }
}
