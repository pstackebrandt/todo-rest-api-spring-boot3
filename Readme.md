# TODO list rest api with Spring Boot 3 from book "Spring Boot 3" by dpunkt

## Purpose

This is a simple training project.  

- Create a simple rest api with Spring Boot 3
- Publish it in a docker container locally
- Publish it with Render

## Publishing and Hosting

I want to publish with Render for free.
Thats why I want to start mit a simple but rather small jdk image.
Render will get the content from my github repository.

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
