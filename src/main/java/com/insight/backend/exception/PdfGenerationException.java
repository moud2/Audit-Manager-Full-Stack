package com.insight.backend.exception;

public class PdfGenerationException extends RuntimeException {
    public PdfGenerationException(String message) {
        super("Error while generating PDF: " + message);
    }
}
