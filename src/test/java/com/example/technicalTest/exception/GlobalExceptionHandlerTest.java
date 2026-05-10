package com.example.technicaltest.exception;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@DisplayName("GlobalExceptionHandler Tests")
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    @DisplayName("handleException - Should return 500 status with error message")
    void testHandleException_ReturnsInternalServerError() {
        // Arrange
        String errorMessage = "Something went wrong";
        Exception exception = new Exception(errorMessage);

        // Act
        ResponseEntity<?> response = exceptionHandler.handleException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("handleException - Should include exception message in response body")
    void testHandleException_IncludesMessage() {
        // Arrange
        String errorMessage = "Test error message";
        Exception exception = new Exception(errorMessage);

        // Act
        ResponseEntity<?> response = exceptionHandler.handleException(exception);

        // Assert
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertTrue(body.containsKey("message"));
        assertEquals(errorMessage, body.get("message"));
    }

    @Test
    @DisplayName("handleException - Should handle null exception message")
    void testHandleException_NullMessage() {
        // Arrange
        Exception exception = new Exception("test error");

        // Act
        ResponseEntity<?> response = exceptionHandler.handleException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @DisplayName("handleException - Should handle RuntimeException")
    void testHandleException_RuntimeException() {
        // Arrange
        String errorMessage = "Runtime error";
        RuntimeException exception = new RuntimeException(errorMessage);

        // Act
        ResponseEntity<?> response = exceptionHandler.handleException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertEquals(errorMessage, body.get("message"));
    }

    @Test
    @DisplayName("handleException - Should handle custom Exception")
    void testHandleException_CustomException() {
        // Arrange
        String errorMessage = "Pet not found";
        NotFoundException exception = new NotFoundException(errorMessage);

        // Act
        ResponseEntity<?> response = exceptionHandler.handleException(exception);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertEquals(errorMessage, body.get("message"));
    }

    @Test
    @DisplayName("handleException - Should handle exception with special characters in message")
    void testHandleException_SpecialCharacters() {
        // Arrange
        String errorMessage = "Error: {\"code\": \"ERR_001\"}";
        Exception exception = new Exception(errorMessage);

        // Act
        ResponseEntity<?> response = exceptionHandler.handleException(exception);

        // Assert
        assertNotNull(response);
        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertEquals(errorMessage, body.get("message"));
    }
}
