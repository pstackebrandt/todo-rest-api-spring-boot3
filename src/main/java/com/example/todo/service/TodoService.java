package com.example.todo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.todo.model.Todo;

/**
 * This class is a service that manages the list of todos. It contains the
 * business logic for the todo list. It provides methods to add, get, and delete
 * todos.
 */
@Service
public class TodoService {

    public static final int MIN_TODO_LENGTH = 5;
    private final List<Todo> todos;

    public TodoService(List<Todo> initialTodos) {
        this.todos = new ArrayList<>(initialTodos);
    }

    public List<Todo> getAllTodos() {
        return todos;
    }

    public String getTodo(int index) {
        validateIndex(index);
        return todos.get(index).getTodo();
    }

    public void addTodo(Todo todo) {
        if (todo.getTodo() == null || todo.getTodo().trim().isEmpty()) {
            throw new IllegalArgumentException("A todo description is required and must not be empty.");
        } else if (todo.getTodo().length() < MIN_TODO_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("Todo description must be at least %d characters long.", MIN_TODO_LENGTH));
        }
        todos.add(todo);
    }

    public void deleteTodo(int index) {
        validateIndex(index);
        todos.remove(index);
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= todos.size()) {
            throw new IndexOutOfBoundsException("Todo with index " + index + " not found");
        }
    }
}
