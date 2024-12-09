package com.insight.backend.exception;

/**
 * Exception thrown when a requested audit is marked as deleted.
 * This exception results in an HTTP status code of 404 (Not Found).
 */
public class AuditDeletedException extends RuntimeException {
    public AuditDeletedException(Long auditId) {
        super("Audit with id " + auditId + " has been deleted");
  }
}
