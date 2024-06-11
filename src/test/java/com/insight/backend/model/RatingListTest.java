package com.insight.backend.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class RatingListTest {
    private RatingList ratingList;
    private List<Rating> ratings;

    @BeforeEach
    public void setUp() {
        ratings = new ArrayList<>();
        ratingList = new RatingList(ratings);
    }

    @Test
    public void testGetRatings() {
        assertEquals(ratings, ratingList.getRatings());
    }

    @Test
    public void testSetRatings() {
        List<Rating> newRatings = new ArrayList<>();
        ratingList.setRatings(newRatings);
        assertEquals(newRatings, ratingList.getRatings());
    }
}