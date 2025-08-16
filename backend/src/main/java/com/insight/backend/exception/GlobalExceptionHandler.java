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
     * Handles exceptions of type AuditNotFoundException, RatingNotFoundException, AuditDeletedException, CategoryNotFoundException, and QuestionNotFoundException.
     * Returns a ResponseEntity with a status of 404 (NOT FOUND) and a JSON body containing the error details.
     */
    @ExceptionHandler({AuditNotFoundException.class, RatingNotFoundException.class, AuditDeletedException.class, CategoryNotFoundException.class, QuestionNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(RuntimeException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles exceptions of type NonExistentAuditCategoryException, DuplicateCategoryNameException, AuditDeletionException, and QuestionAlreadyExistsException.
     * Returns a ResponseEntity with a status of 400 (BAD REQUEST) and a JSON body containing the error details.
     */
    @ExceptionHandler({NonExistentAuditCategoryException.class, DuplicateCategoryNameException.class, AuditDeletionException.class, QuestionAlreadyExistsException.class,
            DuplicateCategoryIdException.class, CategoryAlreadyDeletedException.class, InvalidQuestionException.class})
    public ResponseEntity<Object> handleBadRequestException(RuntimeException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles exceptions of type CategoryDeletionException and IllegalArgumentException.
     * Returns a ResponseEntity with a status of 500 (INTERNAL SERVER ERROR) and a JSON body containing the error details.
     */
    @ExceptionHandler({CategoryDeletionException.class, IllegalArgumentException.class, DatabaseSeedingException.class, InvalidJsonPatchException.class, PdfGenerationException.class,
            CsvReadException.class})
    public ResponseEntity<Object> handleInternalServerException(RuntimeException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}