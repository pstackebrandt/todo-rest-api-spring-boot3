package com.example.todo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents a single todo item")
public class Todo {
    @Schema(description = "The description of the todo item", example = "Buy a linux book")
    private String todo;

    public Todo() {} // needed for JSON deserialization

    public Todo(String todo) {
        this.todo = todo;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }
} 