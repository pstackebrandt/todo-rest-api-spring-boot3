package com.example.todo.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.todo.model.Todo;
import com.example.todo.service.TodoService;

@Configuration
public class TodoConfig {

    @Bean
    public TodoService todoService() {
        return new TodoService(Arrays.asList(
                new Todo("Buy groceries"),
                new Todo("Read Spring Boot documentation"),
                new Todo("Practice coding")
        ));
    }
}
