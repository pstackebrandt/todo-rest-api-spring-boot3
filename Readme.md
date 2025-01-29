# TODO list rest api with Spring Boot 3 from book "Spring Boot 3" by dpunkt

## Purpose

This is a simple training project orientend on the book "Spring Boot 3" by dpunkt.

- see pp. 8-18 (Spring Boot app setup)
- see chapter 9 (REST API)
  
## Creation of the project

created with Spring Initializr
chosen: Gradle-Kotlin, Java 17, Spring Boot 3, Spring Web

## How to run

```powershell
todo-list-api> ./gradlew bootRun
You may update build.gradle.kts to change parameters.
```

```powershell
todo-list-api\build\libs> java -jar .\todo-list-api-0.6.0.jar
Update the jar file name to the actual one.
```

```vs code
Run class TodoListApiApplication via gui.
```

## How to test

```powershell
./gradlew test
```

## How to build

```powershell
 ./gradlew build
```
