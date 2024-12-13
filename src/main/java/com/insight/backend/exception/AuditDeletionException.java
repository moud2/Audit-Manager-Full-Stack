package com.insight.backend.exception;

/**
 * Exception thrown when an error occurs during the deletion of an audit.
 * This exception results in an HTTP status code of 400 (BAD REQUEST).
 */
public class AuditDeletionException extends RuntimeException {
    public AuditDeletionException(Long auditId) {
        super("Audit with id " + auditId + " could not be deleted due to an error.");
    }
}

