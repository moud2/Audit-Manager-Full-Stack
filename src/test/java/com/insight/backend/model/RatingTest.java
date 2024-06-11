package com.insight.backend.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RatingTest {
    private Rating rating;
    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category(1, "TestCategory");
        rating = new Rating(1, category, "TestQuestion", 5, "TestComment", true);
    }

    @Test
    public void testGetRatingId() {
        assertEquals(1, rating.getRatingId());
    }

    @Test
    public void testSetRatingId() {
        rating.setRatingId(2);
        assertEquals(2, rating.getRatingId());
    }

    @Test
    public void testGetCategory() {
        assertEquals(category, rating.getCategory());
    }

    @Test
    public void testSetCategory() {
        Category newCategory = new Category(2, "NewCategory");
        rating.setCategory(newCategory);
        assertEquals(newCategory, rating.getCategory());
    }

    @Test
    public void testGetQuestion() {
        assertEquals("TestQuestion", rating.getQuestion());
    }

    @Test
    public void testSetQuestion() {
        rating.setQuestion("NewQuestion");
        assertEquals("NewQuestion", rating.getQuestion());
    }

    @Test
    public void testGetComment() {
        assertEquals("TestComment", rating.getComment());
    }

    @Test
    public void testSetComment() {
        rating.setComment("NewComment");
        assertEquals("NewComment", rating.getComment());
    }

    @Test
    public void testGetPoints() {
        assertEquals(5, rating.getPoints());
    }

    @Test
    public void testSetPoints() {
        rating.setPoints(10);
        assertEquals(10, rating.getPoints());
    }

    @Test
    public void testGetNA() {
        assertTrue(rating.getNA());
    }

    @Test
    public void testSetNA() {
        rating.setNA(false);
        assertFalse(rating.getNA());
    }
}