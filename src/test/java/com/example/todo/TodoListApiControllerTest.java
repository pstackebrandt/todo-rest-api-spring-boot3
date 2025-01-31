package com.example.todo;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        public TodoListApiController todoListApiController() {
            return new TodoListApiController(Arrays.asList(
                    new Todo("Test todo 1"),
                    new Todo("Test todo 2")
            ));
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
}
