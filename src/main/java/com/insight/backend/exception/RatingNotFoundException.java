package com.insight.backend.exception;

/**
 * Exception thrown when a requested rating is not found.
 * This exception results in an HTTP status code of 404 (NOT FOUND).
 */
public class RatingNotFoundException extends RuntimeException {
    public RatingNotFoundException(Long ratingId) {
        super("Rating with id " + ratingId + " not found");
    }
}
