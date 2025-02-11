package com.example.todo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.todo.config.ProjectInfo;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    private final ProjectInfo projectInfo;

    public OpenApiConfig(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(projectInfo.getName())
                        .version(projectInfo.getVersion())
                        .description(projectInfo.getDescription()));
    }
}
