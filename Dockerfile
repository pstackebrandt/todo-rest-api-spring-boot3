# -----------------------
# 1) Builder Stage
# -----------------------
# Previously, using "gradle:7.6.1-jdk17-alpine" caused build failures due to Java 17 incompatibility.
# We now utilize "gradle:8.11.1-jdk21-corretto" to ensure a successful build with Java 21.
FROM gradle:8.11.1-jdk21-corretto AS builder
WORKDIR /app

# Copy all project files into the container.
COPY . .

# Build the project without the daemon and output detailed stack traces.
RUN gradle build --no-daemon --stacktrace

# -----------------------
# 2) Runtime Stage
# -----------------------
# Using "amazoncorretto:21.0.6-alpine" as the runtime base image for optimal performance with Java 21.
FROM amazoncorretto:21.0.6-alpine
WORKDIR /app

# Accept the version argument to locate the built JAR file.
ARG VERSION=0.7.0
# Copy the built JAR file from the builder stage into the runtime image.
COPY --from=builder /app/build/libs/todo-list-api-${VERSION}.jar app.jar

# Expose the port used by the Spring Boot application.
EXPOSE 8080

# Run the Spring Boot application.
ENTRYPOINT ["java", "-jar", "app.jar"]