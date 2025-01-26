package com.example.demo;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class HelloController {

    // curl http://localhost:8080/hello/peter
    @GetMapping("/hello/{name}")
    public String hello(
        @PathVariable("name") String name
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTime = LocalDateTime.now().format(formatter);
        return """
               Hello, %s! 
               The current time of Feucht in Frankonia is %s.
               """.formatted(name, currentTime);
    }
}
