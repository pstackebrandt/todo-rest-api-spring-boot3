# TODO list rest api with Spring Boot 3 from book "Spring Boot 3" by dpunkt

## Overview

This project is a simple TODO list API designed to practice using Spring Boot and Docker. It leverages REST API and OpenAPI for documentation. The application is hosted for free on Render, which may deactivate the service when idle. Reactivation can take up to 50 seconds.

## Purpose and features

This is a simple training project.  

- Create a simple rest api with Spring Boot 3
- Use Gradle with kotlin DSL for building
- Use OpenAPI for documentation
- Publish it in a docker container locally
- Publish it with Render

## Online OpenAPI documentation

The OpenAPI documentation is available at:
<https://todo-rest-api-spring-boot3.onrender.com/swagger-ui/index.html>

## Publishing and Hosting

I published it with Render for free. The api will be deactivated automatically by Render and may need 50 seconds (!) or more to answer until it is automa automatically reactivated. 

Address of the published API (currently no (error) page at that address):
https://todo-rest-api-spring-boot3.onrender.com

Call that returns the todos list:
https://todo-rest-api-spring-boot3.onrender.com/todos

Render gets the content from the main branch of my github repository. 
I used a jdk image of middle size (relative to other other spring boot jdk images).

## Creation of the project

created with Spring Initializr
chosen: Gradle-Kotlin, Java 17, Spring Boot 3, Spring Web

This combination did lead to compile errors while creating the Docker image. Reasons seem to be that parts of Spring Boot 3 are not fully compatible with Java 17.

## Run project

### Run project with gradle

```powershell
todo-list-api> ./gradlew bootRun
You may update build.gradle.kts to change parameters.
```

### Run project with jar file

Update the jar file name to the actual one.

```powershell
todo-list-api\build\libs> java -jar .\todo-list-api-0.6.0.jar
```

### Run project with vs code

Run class TodoListApiApplication via gui.

### Run docker image

```powershell
docker run -p 8080:8080 todo-list-api
```

## Build project

```powershell
 ./gradlew build
```

## Build docker image

```powershell
 docker build -t todo-list-api .
 ```
