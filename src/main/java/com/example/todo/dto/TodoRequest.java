package com.example.todo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TodoRequest {
    @NotBlank(message = "Todo description cannot be null or empty")
    @Size(min = 5, message = "Todo description must be at least 5 characters long")
    private String todo;

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }
}