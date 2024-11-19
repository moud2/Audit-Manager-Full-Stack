package com.insight.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a category with the same name already exists.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicateCategoryNameException extends RuntimeException {
    public DuplicateCategoryNameException(String name) {
        super("Category with the name '" + name + "' already exists");
    }
}
