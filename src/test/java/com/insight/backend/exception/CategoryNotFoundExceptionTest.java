package com.insight.backend.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CategoryNotFoundExceptionTest {

    @Test
    public void testCategoryNotFoundExceptionMessage() {
        // Arrange
        Long categoryID = 99L;

        // Act
        CategoryNotFoundException exception = new CategoryNotFoundException(categoryID);

        // Assert
        assertEquals("Category with the id '99' not found", exception.getMessage());
    }
}
