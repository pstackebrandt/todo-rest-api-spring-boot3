package com.example.todo;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.example.todo.model.Todo;
import com.example.todo.service.TodoService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoListApiController.class)
@ContextConfiguration(classes = TodoListApiControllerTest.TestConfig.class)
class TodoListApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Configuration
    static class TestConfig {

        @Bean
        public TodoService todoService() {
            return new TodoService(Arrays.asList(
                    new Todo("Test todo 1"),
                    new Todo("Test todo 2")
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
                .andExpect(content().string("Test todo 1"));
    }

    @Test
    void getTodo_WithInvalidIndex_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/todos/999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Todo with index 999 not found"));
    }

    @Test
    void addTodo_WithValidTodo_ShouldAddTodoAndReturn201() throws Exception {
        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"todo\": \"Test todo 3\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Todo successfully added"));

        mockMvc.perform(get("/todos"))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[2].todo").value("Test todo 3"));
    }

    @Test
    void addTodo_WithEmptyTodo_ShouldReturn400() throws Exception {
        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"todo\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("A todo description is required and must not be empty."));
    }

    @Test
    void addTodo_WithNullTodo_ShouldReturn400() throws Exception {
        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"todo\": null}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("A todo description is required and must not be empty."));
    }

    @Test
    void addTodo_WithTodoShorterThanMinLength_ShouldReturn400() throws Exception {
        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"todo\": \"hi\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        String.format("Todo description must be at least %d characters long.",
                                TodoService.MIN_TODO_LENGTH)));
    }
}
