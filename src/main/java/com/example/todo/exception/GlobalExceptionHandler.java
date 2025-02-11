package com.example.todo.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * GlobalExceptionHandler intercepts exceptions thrown by controller endpoints
 * and converts them into a structured JSON format.
 *
 * This handler ensures that even error responses adhere to the project's
 * requirement of returning JSON rather than plain text, which provides
 * consistency for API clients.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<Map<String, Object>> buildErrorResponse(String error, String message, HttpStatus status) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", error);
        errorResponse.put("message", message); // returning unfiltered error messages may be dangerous
        errorResponse.put("status", status.value());
        return new ResponseEntity<>(errorResponse, status);
    }

    /**
     * @param ex the IndexOutOfBoundsException that occurred
     * @return a ResponseEntity containing a JSON error response
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<Map<String, Object>> handleIndexOutOfBounds(IndexOutOfBoundsException ex) {
        logger.error("Global Exception Handler - handleIndexOutOfBounds() : Not Found (Index out of bounds): {}", ex.getMessage());
        return buildErrorResponse("Not Found", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * @param ex the IllegalArgumentException that occurred
     * @return a ResponseEntity containing a JSON error response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        logger.error("Bad Request (Illegal argument exception): {}", ex.getMessage());

        var errorResponse = buildErrorResponse("Bad Request", ex.getMessage(), HttpStatus.BAD_REQUEST);

        // Assuming the request body is available in the request attributes
        if (request.getAttribute("requestBody") != null) {
            Map<String, Object> body = errorResponse.getBody();
            if (body != null) {
                String requestBody = (String) request.getAttribute("requestBody");
                body.put("requestBody", requestBody);
            }

            return errorResponse;
        }

        return errorResponse;
    }

    /**
     * Handles type mismatches for path variables or request parameters.
     * 
     * This ensures that if a parameter cannot be converted (for example,
     * "three" to int), a clear Bad Request response is returned instead of an
     * Internal Server Error.
     *
     * @param ex the MethodArgumentTypeMismatchException that occurred
     * @return a ResponseEntity containing a JSON error response
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex
    ) {
        logger.error("Global Exception Handler - handleMethodArgumentTypeMismatch() : Bad Request (Method argument type mismatch): {}", ex.getMessage());

        Class<?> requiredType = ex.getRequiredType();
        String expectedType = (requiredType != null) ? requiredType.getSimpleName() : "unknown";

        String message = String.format(
                "Invalid value '%s' for parameter '%s'. Expected type is '%s'.",
                ex.getValue(), ex.getName(), expectedType);

        return buildErrorResponse("Bad Request", message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles validation exceptions (using MethodArgumentNotValidException).
     *
     * TODO: The handler for validation exceptions (using
     * MethodArgumentNotValidException) returns a fixed message (“Todo
     * description cannot be null or empty”). Recommendation: Consider iterating
     * through the binding errors so that, for example, if the error is due to
     * the too-short string, you provide the original message (e.g., “Todo
     * description must be at least 5 characters long”). This change would
     * better reflect the constraint declared in both the DTO and the model.
     *
     * @param ex the MethodArgumentNotValidException that occurred
     * @return a ResponseEntity containing a JSON error response
     */
    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            org.springframework.web.bind.MethodArgumentNotValidException ex
    ) {
        logger.error("Bad Request (Validation error): {}", ex.getMessage());
        return buildErrorResponse("Bad Request", "Todo description cannot be null or empty", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadable(
            org.springframework.http.converter.HttpMessageNotReadableException ex
    ) {
        logger.error("Bad Request (Invalid JSON): {}", ex.getMessage());
        return buildErrorResponse("Bad Request", "Invalid request format: Todo description cannot be null or empty", HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles any other exceptions that are not specifically caught by other
     * handlers, providing a fallback error response in JSON format with a 500
     * status.
     *
     * @param ex the exception that occurred
     * @return a ResponseEntity containing a JSON error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex
    ) {
        logger.error("Internal Server Error (General exception): {}", ex.getMessage());
        return buildErrorResponse("Internal Server Error", "An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
