package com.example.todo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.dto.TodoRequest;
import com.example.todo.model.Todo;
import com.example.todo.service.TodoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

// curl http://localhost:8080/todos
@RestController
@RequestMapping("/todos")
public class TodoListApiController {

    private static final Logger logger = LoggerFactory.getLogger(TodoListApiController.class);
    private final TodoService todoService;

    public TodoListApiController(TodoService todoService) {
        this.todoService = todoService;
    }

    /*
     * Get all todos
     * curl http://localhost:8080/todos
     */
    @Operation(summary = "Get all todos", description = "Returns a list of all todos")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of todos",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Todo.class)))
    @GetMapping
    public List<Todo> getTodos() {
        logger.info("GET /todos - Retrieving all todos");
        return todoService.getAllTodos();
    }

    /*
     * Get a specific todo
     * curl http://localhost:8080/todos/0
     */
    @Operation(summary = "Get a specific todo", description = "Returns a todo by its index")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of todo",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Todo.class)))
    @GetMapping("/{index}")
    public ResponseEntity<Todo> getTodo(@PathVariable int index) {
        logger.info("GET /todos/{} - Retrieving todo by index", index);
        return ResponseEntity.ok(todoService.getTodo(index));
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
    public ResponseEntity<Map<String, Object>> addTodo(
            @RequestBody // binding the request body to the todo object
            @Valid // validate the todo object
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Todo to add",
                    required = true,
                    content = @Content(schema = @Schema(implementation = TodoRequest.class))) TodoRequest todoRequest) {
        logger.info("POST /todos - Adding new todo: {}", todoRequest);

        Todo todo = new Todo(todoRequest.getTodo());
        todoService.addTodo(todo);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("url", "/todos/" + todo.hashCode());
        responseBody.put("task", todo.getTodo());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseBody);
    }

    /*
     * Delete a todo
     * curl -X DELETE http://localhost:8080/todos/0
     */
    @Operation(summary = "Delete a todo", description = "Deletes a todo by its index")
    @ApiResponse(responseCode = "204", description = "Todo successfully deleted")
    @ApiResponse(responseCode = "404", description = "Todo not found")
    @ApiResponse(responseCode = "400", description = "Invalid index provided")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{index}")
    public void deleteTodo(@PathVariable int index) {
        logger.info("DELETE /todos/{} - Deleting todo by index", index);
        todoService.deleteTodo(index);
    }

}
