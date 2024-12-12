package com.insight.backend.exception;

/**
 * Exception thrown when a category with the same name already exists.
 */
public class DuplicateCategoryNameException extends RuntimeException {
    public DuplicateCategoryNameException(String name) {
        super("Category with the name '" + name + "' already exists");
    }
}
