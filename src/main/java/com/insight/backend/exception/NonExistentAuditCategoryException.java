package com.insight.backend.exception;

// This exception should result in a 400 Bad Request HTTP response

/**
 * Exception thrown when a requested category is not found.
 * This exception results in an HTTP status code of 400 (BAD REQUEST).
 */
public class NonExistentAuditCategoryException extends RuntimeException {
    /**
     * Constructs a new NonExistentAuditCategoryException with the specified category.
     *
     * @param category The category id that was not found
     */
    public NonExistentAuditCategoryException(Long category) {
        super("Category with id " + category + " not found");
    }
}