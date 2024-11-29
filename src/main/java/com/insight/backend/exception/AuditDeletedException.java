package com.insight.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a requested audit is marked as deleted.
 * This exception results in an HTTP status code of 410 (GONE).
 */
@ResponseStatus(value = HttpStatus.GONE)
public class AuditDeletedException extends RuntimeException {
    public AuditDeletedException(Long auditId) {
        super("Audit with id " + auditId + " has been deleted");
  }
}
