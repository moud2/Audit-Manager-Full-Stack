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
    @ExceptionHandler({AuditNotFoundException.class, RatingNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(RuntimeException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles exceptions of type NonExistentAuditCategoryException.
     * Returns a ResponseEntity with a status of 400 (BAD REQUEST) and a JSON body containing the error details.
     */
    @ExceptionHandler({NonExistentAuditCategoryException.class, DuplicateCategoryNameException.class})
    public ResponseEntity<Object> handleBadRequestException(RuntimeException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exceptions of type AuditDeletedException.
     * Returns a ResponseEntity with a status of 410 (GONE) and a JSON body containing the error details.
     */
    @ExceptionHandler(AuditDeletedException.class)
    public ResponseEntity<Object> handleAuditDeletedException(AuditDeletedException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.GONE.value());
        body.put("error", "Gone");
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.GONE);
    }
}