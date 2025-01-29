package com.insight.backend.exception;

public class DuplicateCategoryIdException extends RuntimeException {
  public DuplicateCategoryIdException() {
    super("Duplicate Category-IDs are not allowed.");
  }
}
