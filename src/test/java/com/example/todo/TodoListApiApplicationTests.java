package com.example.todo;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.example.todo.model.Todo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodoListApiApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void initialState_ShouldContainThreeTodos() {
        Todo[] todos = restTemplate.getForObject("http://localhost:" + port + "/todos", Todo[].class);

        assertThat(todos).hasSize(3);
        assertThat(todos[0].getTodo()).isEqualTo("Buy groceries");
        assertThat(todos[1].getTodo()).isEqualTo("Read Spring Boot documentation");
        assertThat(todos[2].getTodo()).isEqualTo("Practice coding");
    }

}
