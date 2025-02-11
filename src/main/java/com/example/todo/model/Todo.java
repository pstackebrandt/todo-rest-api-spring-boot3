package com.example.todo.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * Represents a single todo item.
 */
@Schema(description = "Represents a single todo item")
public final class Todo {
    private static final Logger logger = LoggerFactory.getLogger(Todo.class);

    @NotBlank(message = "Todo description cannot be null or empty")
    @Schema(description = "The description of the todo item (required)",
            example = "Buy a linux book")
    private String todo;

    public Todo() {
    } // needed for JSON deserialization

    public Todo(String todo) {
        setTodo(todo);
    }

    public String getTodo() {
        return todo;
    }

    public final void setTodo(String todo) {
        if (isEmptyOrNull(todo)) {
            logger.error("Todo description cannot be null or empty");
            throw new IllegalArgumentException("Todo description cannot be null or empty");
        }
        this.todo = todo;
    }

    private static boolean isEmptyOrNull(String str) {
        return str == null || str.trim().isEmpty();
    }

}
