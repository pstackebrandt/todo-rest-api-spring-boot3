package com.example.todo;

import java.util.List;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

// curl http://localhost:8080/todos
@RestController
@RequestMapping("/todos")
public class TodoListApiController {
    private List<Todo> todos = new ArrayList<>();

    /* 
     * Get all todos
     *  curl http://localhost:8080/todos
     */
    @Operation(summary = "Get all todos", description = "Returns a list of all todos")

    @ApiResponse(responseCode = "200", description = "Successful retrieval of todos",
                 content = @Content(mediaType = "application/json",
                 schema = @Schema(implementation = Todo.class)))
    @GetMapping
    public List<Todo> getTodos() {
        return todos;
    }

    /* 
     * Get a specific todo
     *  curl http://localhost:8080/todos/0
     */
    @Operation(summary = "Get a specific todo", description = "Returns a todo by its index")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of todo",
                 content = @Content(mediaType = "application/json",
                 schema = @Schema(implementation = String.class)))
    @GetMapping("/{index}")
    public String getTodo(@PathVariable int index) {
        return todos
                .get(index)
                .getTodo();
    }

    /*
     * Add a new todo
     *  curl -X POST -H "Content-Type: application/json" -d '{"todo":"Buy a good book about AI usage"}' http://localhost:8080/todos
     *  curl -X POST -H "Content-Type: application/json" -d '{"todo":"Get child from school"}' http://localhost:8080/todos
     *  curl -X POST -H "Content-Type: application/json" -d '{"todo":"Get to bed early"}' http://localhost:8080/todos
     */
    @Operation(summary = "Add a new todo", description = "Adds a new todo to the list")
    @ApiResponse(responseCode = "201", description = "Todo successfully added")
    @PostMapping
    public void addTodo(@RequestBody(description = "Todo to add", required = true,
                                     content = @Content(schema = @Schema(implementation = Todo.class)))
                        Todo todo) {
        todos.add(todo);
    }

    /*
     * Delete a todo
     *  curl -X DELETE http://localhost:8080/todos/0
     */
    @Operation(summary = "Delete a todo", description = "Deletes a todo by its index")
    @ApiResponse(responseCode = "204", description = "Todo successfully deleted")
    @DeleteMapping("/{index}")
    public void deleteTodo(@PathVariable int index) {
        todos.remove(index);
    }
}