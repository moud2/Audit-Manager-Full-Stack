package com.insight.backend.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryTest {
    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category(1, "TestCategory");
    }

    @Test
    public void testGetCategoryId() {
        assertEquals(1, category.getCategoryId());
    }

    @Test
    public void testSetCategoryId() {
        category.setCategoryId(2);
        assertEquals(2, category.getCategoryId());
    }

    @Test
    public void testGetCategoryName() {
        assertEquals("TestCategory", category.getCategoryName());
    }

    @Test
    public void testSetCategoryName() {
        category.setCategoryName("NewCategory");
        assertEquals("NewCategory", category.getCategoryName());
    }
}