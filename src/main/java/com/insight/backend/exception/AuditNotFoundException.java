package com.insight.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a requested audit is not found.
 * This exception results in an HTTP status code of 404 (NOT FOUND).
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class AuditNotFoundException extends RuntimeException {
    public AuditNotFoundException(Long auditId) {
        super("Audit with id " + auditId + " not found");
    }
}