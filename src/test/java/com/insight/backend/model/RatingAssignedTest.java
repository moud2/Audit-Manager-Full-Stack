package com.insight.backend.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

public class RatingAssignedTest {
    private RatingAssigned ratingAssigned;
    private HashMap<Integer, RatingList> testMap;

    @BeforeEach
    public void setUp() {
        ratingAssigned = new RatingAssigned();
        testMap = new HashMap<>();
        ratingAssigned.setRatingsAssigned(testMap);
    }

    @Test
    public void testSetRatingsAssigned() {
        HashMap<Integer, RatingList> newMap = new HashMap<>();
        ratingAssigned.setRatingsAssigned(newMap);
        assertEquals(newMap, ratingAssigned.getRatingsAssigned());
    }

    @Test
    public void testGetRatingsAssigned() {
        assertEquals(testMap, ratingAssigned.getRatingsAssigned());
    }
}