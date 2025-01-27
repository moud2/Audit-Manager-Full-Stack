package com.insight.backend.exception;

/**
 * Exception thrown when a requested audit is not found.
 * This exception results in an HTTP status code of 404 (NOT FOUND).
 */
public class AuditNotFoundException extends RuntimeException {
    public AuditNotFoundException(String message) {
        super(message);
    }
}
