package com.insight.backend.exception;

public class InvalidQuestionException extends RuntimeException {
    public InvalidQuestionException() {
        super("Question cannot be null");
    }
}
