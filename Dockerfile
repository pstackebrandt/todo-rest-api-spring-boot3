# -----------------------
# 1) Builder stage
# -----------------------
    # earlier used: gradle:7.6.1-jdk17-alpine - Builds failed
    FROM gradle:8.11.1-jdk21-corretto AS builder
    WORKDIR /app
    
    # Copy all files to the container
    COPY . .
    
    # Build the project
    RUN gradle build --no-daemon --stacktrace
    
    # -----------------------
    # 2) Run stage
    # -----------------------
    #current latest: amazoncorretto:21.0.6-alpine
    FROM amazoncorretto:21.0.6-alpine
    WORKDIR /app
    
    # Copy the built JAR file from the builder stage
    COPY --from=builder /app/build/libs/todo-list-api-0.6.0.jar app.jar
    
    # Expose the port that Spring Boot runs on
    EXPOSE 8080
    
    # Run the Spring Boot app
    ENTRYPOINT ["java", "-jar", "app.jar"]