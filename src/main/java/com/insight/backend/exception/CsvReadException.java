package com.insight.backend.exception;

public class CsvReadException extends RuntimeException {
    public CsvReadException(String message) {
        super("Error reading CSV file: " + message);
    }
}
