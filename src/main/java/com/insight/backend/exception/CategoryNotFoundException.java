package com.insight.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a requested category is not found.
 * This exception results in an HTTP status code of 404 (NOT FOUND).
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Category")
public class CategoryNotFoundException extends RuntimeException {
        public CategoryNotFoundException(Long id) {
            super("Category with the id '" + id + "' not found");
        }
}

