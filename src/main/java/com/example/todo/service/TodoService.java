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

    /**
     * Get a todo by its index
     *
     * @param index
     * @return the todo object
     */
    public Todo getTodo(int index) {
        validateIndex(index);
        return todos.get(index);
    }

    /**
     * Add a todo to the list.
     *
     * Since the Todo object is already validated (via the DTO and in the Todo
     * constructor), this method only performs a null check.
     *
     * @param todo the Todo to add
     */
    public void addTodo(Todo todo) {
        if (todo == null) {
            throw new IllegalArgumentException("Todo cannot be null");
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
