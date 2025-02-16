<!--
  README.template.md
  ------------------
  This is the template used to generate README.md.
  
  IMPORTANT:
  • Do NOT modify the generated README.md directly.
  • To make updates, change this template file.
  • To regenerate README.md with your changes, run:
       ./gradlew generateReadme
-->

# TODO list rest api with Spring Boot 3 from book "Spring Boot 3" by dpunkt

[![Version](https://img.shields.io/badge/version-${project.version}-blue.svg)](https://github.com/pstackebrandt/todo-rest-api-spring-boot3/releases) [![Issues](https://img.shields.io/github/issues/pstackebrandt/todo-rest-api-spring-boot3.svg)](https://github.com/pstackebrandt/todo-rest-api-spring-boot3/issues)

## Overview

This project is a simple TODO list API designed to practice using Spring Boot and Docker. It leverages REST API and OpenAPI for documentation. The application is hosted for free on Render, which may deactivate the service when idle. Reactivation can take up to 50 seconds.

## Purpose and features

This is a simple training project.  

- Create a simple rest api with Spring Boot 3
- Use Gradle with kotlin DSL for building
- Use OpenAPI for documentation
- Publish it in a docker container locally
- Publish it with Render

## Updating the README file

Do Not Edit README Manually!

The generated README.md is auto-generated from README.template.md via your Gradle tasks.
Instead of manually changing README.md, you should update README.template.md and then run the appropriate Gradle task to regenerate the README.md with your changes.

```powershell
${project.slug}> ./gradlew generateReadme
```

## Updating the Project Version

When you need to update the version, do not change it manually in the generated README or elsewhere in the project. Instead, update the version value in your gradle.properties (or wherever it is configured), and then run the Gradle task (for example, ./gradlew updateVersionNumberUsages) which updates the version in your Dockerfile, badges, and any other files. This way, every reference to the project version remains in sync across your project.

## Online OpenAPI documentation

The OpenAPI documentation is available at:
<${project.url}/swagger-ui/index.html>

## Publishing and Hosting

I deployed the application on Render as a free service. Please note that Render may deactivate the API during periods of inactivity. As a result, the first request made after the API has been idle may take 50 seconds or longer while the service automatically reactivates.

Address of the published API (currently no (error) page at that address):
<${project.url}>

Call that returns the todos list:
<${project.url}/todos>

Render gets the content from the main branch of my github repository.
I used a jdk image of middle size (relative to other spring boot jdk images).

## Creation of the project

The project was created with Spring Initializr using:

- Gradle with Kotlin DSL
- Java 17 (Project was later upgraded to 21)
- Spring Boot 3, Spring Web

> Note: Earlier we encountered build issues with Java 17 which have been resolved by upgrading to Java 21.

## Run project

### Run project with gradle

```powershell
${project.slug}> ./gradlew bootRun
You may update build.gradle.kts to change parameters.
```

### Run (and build) project with jar file

Update the jar file name to the actual one.

```powershell
${project.slug}\build\libs> java -jar .\${project.slug}-${project.version}.jar
```

### Run project with vs code

Run class TodoListApiApplication via gui.

## Build project

```powershell
 ./gradlew build
```

## Run tests

### Run tests with gradle

```powershell
 ./gradlew test
```

### Clean up test results and run tests with gradle

```powershell
 ./gradlew clean test
```

### Run tests with vs code

Run class e.g. TodoListApiApplicationTests via gui.

## Use Docker

### Build and run docker image with version and name, remove container and image on exit

Attention: Use the project.name and project.version from the application.properties file.

```powershell
docker build -t ${project.slug}:${project.version} .
docker run -p 8080:8080 --name ${project.slug}-${project.version} --rm ${project.slug}:${project.version}
```

### Run docker image with version and name, remove container and image on exit

```powershell
docker run -p 8080:8080 --name ${project.slug}-${project.version} --rm ${project.slug}:${project.version}
```

### Run docker image with version and name

```powershell
docker run -p 8080:8080 --name ${project.slug}-${project.version} ${project.slug}:${project.version}
```
