package com.insight.backend.exception;

/**
 * Exception thrown when an error occurs while generating a PDF.
 */
public class PdfGenerationException extends RuntimeException {
    public PdfGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
