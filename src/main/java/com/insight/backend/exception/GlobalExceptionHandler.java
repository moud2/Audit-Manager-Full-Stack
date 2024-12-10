package com.insight.backend.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * GlobalExceptionHandler is a class that handles exceptions thrown by the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions of type AuditNotFoundException and RatingNotFoundException.
     * Returns a ResponseEntity with a status of 404 (NOT FOUND) and a JSON body containing the error details.
     */
    @ExceptionHandler({AuditNotFoundException.class, RatingNotFoundException.class, AuditDeletedException.class})
    public ResponseEntity<Object> handleNotFoundException(RuntimeException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles exceptions of type NonExistentAuditCategoryException.
     * Returns a ResponseEntity with a status of 400 (BAD REQUEST) and a JSON body containing the error details.
     */
    @ExceptionHandler({NonExistentAuditCategoryException.class, DuplicateCategoryNameException.class, AuditDeletionException.class})
    public ResponseEntity<Object> handleBadRequestException(RuntimeException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    /**
     * Handles ResponseStatusException to provide detailed error messages in the response body.
     * Returns a detailed JSON response containing: timestamp, status, error, and message.
     */
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Object> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    /**
     * Handles IllegalArgumentException with a JSON error response.
     * - Returns HTTP 500 (Internal Server Error) with details: timestamp, status, error, and message.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles CategoryDeletionException with a JSON error response.
     * - Returns HTTP 500 (Internal Server Error) with details: timestamp, status, error, and message.
     */
    @ExceptionHandler(CategoryDeletionException.class)
    public ResponseEntity<Object> handleCategoryDeletionException(CategoryDeletionException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}