package com.example.todo;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.model.Todo;
import com.example.todo.service.TodoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

// curl http://localhost:8080/todos
@RestController
@RequestMapping("/todos")
public class TodoListApiController {

    private final TodoService todoService;

    public TodoListApiController(TodoService todoService) {
        this.todoService = todoService;
    }

    /*
     * Get all todos
     * curl http://localhost:8080/todos
     */
    @Operation(summary = "Get all todos", description = "Returns a list of all todos")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of todos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Todo.class)))
    @GetMapping
    public List<Todo> getTodos() {
        return todoService.getAllTodos();
    }

    /*
     * Get a specific todo
     * curl http://localhost:8080/todos/0
     */
    @Operation(summary = "Get a specific todo", description = "Returns a todo by its index")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of todo", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    @GetMapping("/{index}")
    public ResponseEntity<String> getTodo(@PathVariable int index) {
        try {
            return ResponseEntity.ok(todoService.getTodo(index));
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Todo with index " + index + " not found");
        }
    }

    /*
     * Add a new todo
     * curl -X POST -H "Content-Type: application/json" -d '{"todo":"Buy a good book about AI usage"}' http://localhost:8080/todos
     * curl -X POST -H "Content-Type: application/json" -d '{"todo":"Get child from school"}' http://localhost:8080/todos
     * curl -X POST -H "Content-Type: application/json" -d '{"todo":"Get to bed early"}' http://localhost:8080/todos
     */
    @Operation(summary = "Add a new todo", description = "Adds a new todo to the list")
    @ApiResponse(responseCode = "201", description = "Todo successfully added")
    @ApiResponse(responseCode = "400", description = "Invalid todo provided")
    @PostMapping
    public ResponseEntity<String> addTodo(
            @RequestBody // Spring annotation for binding
            @io.swagger.v3.oas.annotations.parameters.RequestBody( // Don't shorten to @RequestBody!
                    description = "Todo to add",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = Todo.class))) Todo todo) {
        try {
            todoService.addTodo(todo);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Todo successfully added");
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    /*
     * Delete a todo
     * curl -X DELETE http://localhost:8080/todos/0
     */
    @Operation(summary = "Delete a todo", description = "Deletes a todo by its index")
    @ApiResponse(responseCode = "204", description = "Todo successfully deleted")
    @DeleteMapping("/{index}")
    public void deleteTodo(@PathVariable int index) {
        todoService.deleteTodo(index);
    }
}
