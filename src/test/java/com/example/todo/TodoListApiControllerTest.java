package com.example.todo;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.todo.exception.GlobalExceptionHandler;
import com.example.todo.model.Todo;
import com.example.todo.service.TodoService;

@WebMvcTest(TodoListApiController.class)
@ContextConfiguration(classes = TodoListApiControllerTest.TestConfig.class)
class TodoListApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Configuration
    @Import(GlobalExceptionHandler.class) // Import the GlobalExceptionHandler as part of the test context.
    static class TestConfig {

        @Bean
        public TodoService todoService() {
            return new TodoService(Arrays.asList(
                    new Todo("Test todo 1"),
                    new Todo("Test todo 2")
            // We decided to use a specific number of todos for testing.
            // This is independent of the number of todos in the initial state of the published API. The initial state of the API may change independently.
            ));
        }

        @Bean
        public TodoListApiController todoListApiController(TodoService todoService) {
            return new TodoListApiController(todoService);
        }
    }

    @Test
    void getTodos_ShouldReturnTodosList() throws Exception {
        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].todo").value("Test todo 1"))
                .andExpect(jsonPath("$[1].todo").value("Test todo 2"));
    }

    @Test
    void getTodo_WithValidIndex_ShouldReturnTodo() throws Exception {
        mockMvc.perform(get("/todos/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.todo").value("Test todo 1"));
    }

    @Test
    void getTodo_WithInvalidIndex_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/todos/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void addTodo_WithValidTodo_ShouldAddTodoAndReturn201() throws Exception {

        // Get the number of todos before adding a new one
        mockMvc.perform(get("/todos"))
                .andExpect(jsonPath("$.length()")
                        .value(2));

        // Add a new todo
        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"todo\": \"Test todo 3\"}"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/todos"))
                .andExpect(jsonPath("$.length()")
                        .value(3))
                .andExpect(jsonPath("$[2].todo")
                        .value("Test todo 3"));
    }

    @Test
    void addTodo_WithEmptyTodo_ShouldReturn400() throws Exception {
        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"todo\": \"\"}"))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(status().isBadRequest());
    }

    @Test
    void addTodo_WithNullTodo_ShouldReturn400() throws Exception {
        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"todo\": null}"))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(status().isBadRequest());
    }

    @Test
    void addTodo_WithTodoShorterThanMinLength_ShouldReturn400() throws Exception {
        mockMvc.perform(
                post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"todo\": \"hi\"}"))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(status().isBadRequest());
    }
}
