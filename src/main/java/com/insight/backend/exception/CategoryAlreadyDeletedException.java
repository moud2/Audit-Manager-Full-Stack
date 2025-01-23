package com.insight.backend.exception;

public class CategoryAlreadyDeletedException extends RuntimeException {
    public CategoryAlreadyDeletedException() {
        super("Category is already deleted or does not exist.");
    }
}
