package com.insight.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a requested rating is not found.
 * This exception results in an HTTP status code of 404 (NOT FOUND).
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such Rating")
public class RatingNotFoundException extends RuntimeException {
    public RatingNotFoundException(Long ratingId) {
        super("Rating with id " + ratingId + " not found");
    }
}
