package com.example.todo;

import java.util.List;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.*;

// curl http://localhost:8080/todos
@RestController
@RequestMapping("/todos")
public class TodoListApiController {
    private List<String> todos = new ArrayList<>();

    // curl http://localhost:8080/todos
    @GetMapping
    public List<String> getTodos() {
        return todos;
    }

    // curl http://localhost:8080/todos/0
    @GetMapping("/{index}")
    public String getTodo(@PathVariable int index) {
        return todos.get(index);
    }

    // curl -X POST -H "Content-Type: application/json" -d '{"todo":"Buy milk"}' http://localhost:8080/todos
    // curl -X POST -H "Content-Type: application/json" -d '{"todo":"Get child from school"}' http://localhost:8080/todos
    // curl -X POST -H "Content-Type: application/json" -d '{"todo":"Get to bed early"}' http://localhost:8080/todos
    @PostMapping
    public void addTodo(@RequestBody String todo) {
        todos.add(todo);
    }

    // curl -X DELETE http://localhost:8080/todos/0
    @DeleteMapping("/{index}")
    public void deleteTodo(@PathVariable int index) {
        todos.remove(index);
    }
} 