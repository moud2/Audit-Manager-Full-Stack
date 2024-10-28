package com.insight.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND)
public class AuditNotFoundException extends RuntimeException {
    public AuditNotFoundException(Long auditId) {
        super("Audit with id " + auditId + " not found");
    }
}