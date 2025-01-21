package com.insight.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a duplicate question is detected.
 * This exception results in an HTTP status code of 400 (BAD REQUEST).
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Question already exists")
public class QuestionAlreadyExistsException extends RuntimeException {
    public QuestionAlreadyExistsException(String question_already_exists) {
        super("The question already exists.");
    }
}
