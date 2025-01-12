package com.insight.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when an error occurs during the deletion of a category.
 * This exception results in an HTTP status code of 400 (BAD REQUEST).
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CategoryDeletionException extends RuntimeException {
    public CategoryDeletionException(Long categoryId) {
        super("Category with ID " + categoryId + " could not be deleted");
    }
}
