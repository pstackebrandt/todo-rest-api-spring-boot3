package com.example.todo;

import java.util.List;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.*;

// curl http://localhost:8080/todos
@RestController
@RequestMapping("/todos")
public class TodoListApiController {
    private List<Todo> todos = new ArrayList<>();

    // curl http://localhost:8080/todos
    @GetMapping
    public List<Todo> getTodos() {
        return todos;
    }

    // curl http://localhost:8080/todos/0
    @GetMapping("/{index}")
    public String getTodo(@PathVariable int index) {
        return todos
                .get(index)
                .getTodo();
    }

    // The following commands don't work with my current setup of vs code. 
    // They should suppress the introduction of line-breaks by auto-formatter.
    // formatter:off
    // prettier-ignore
    /*
    curl -X POST -H "Content-Type: application/json" -d '{"todo":"Buy milk"}' http://localhost:8080/todos
    curl -X POST -H "Content-Type: application/json" -d '{"todo":"Get child from school"}' http://localhost:8080/todos
    curl -X POST -H "Content-Type: application/json" -d '{"todo":"Get to bed early"}' http://localhost:8080/todos
    */
    // formatter:on

    @PostMapping
    public void addTodo(@RequestBody Todo todo) {
        todos.add(todo);
    }

    // curl -X DELETE http://localhost:8080/todos/0
    @DeleteMapping("/{index}")
    public void deleteTodo(@PathVariable int index) {
        todos.remove(index);
    }
}