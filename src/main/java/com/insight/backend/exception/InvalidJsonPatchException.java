package com.insight.backend.exception;

public class InvalidJsonPatchException extends RuntimeException {
    public InvalidJsonPatchException(Long ratingId, String message) {
        super("Failed to apply JSON patch to rating with ID " + ratingId + ": " + message);
    }
}
