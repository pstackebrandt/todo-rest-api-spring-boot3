package com.example.todo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.config.ProjectInfo;

/**
 * Provides base information about the Todo List API when calling the root endpoint.
 * This endpoint is useful for:
 * - Offering a welcome message.
 * - Sharing API details such as name, version, and description.
 * - Highlighting important endpoints such as /todos and the Swagger documentation.
 */
@RestController
public class RootController {

    private final ProjectInfo projectInfo;

    public RootController(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getBaseInformation() {
        Map<String, Object> info = new HashMap<>();
        info.put("apiName", projectInfo.getName());
        info.put("version", projectInfo.getVersion());
        info.put("description", projectInfo.getDescription());

        // Providing links to the main endpoints.
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("todos", "/todos");
        endpoints.put("swaggerDocumentation", "/swagger-ui/index.html");
        info.put("endpoints", endpoints);

        info.put("message", "Welcome to the " + projectInfo.getName() + "! Use the provided endpoints to interact with the service.");
        return info;
    }
}
