package com.insight.backend.exception;

public class DatabaseSeedingException extends RuntimeException {
    public DatabaseSeedingException(String message) {
        super("Error seeding database: " + message);
    }
}
