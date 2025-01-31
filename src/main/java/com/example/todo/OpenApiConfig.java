package com.example.todo;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("To-Do List API")
                        .version("1.0")
                        .description(
                                "This project is a simple TODO list API designed to practice using Spring Boot and Docker. It leverages REST API and OpenAPI for documentation. The application is hosted for free on Render, which may deactivate the service when idle. Reactivation can take up to 50 seconds."));
    }
}