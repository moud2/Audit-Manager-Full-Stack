package com.insight.backend.service;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.insight.backend.model.Rating;
import com.insight.backend.repository.RatingRepository;
import com.insight.backend.service.rating.SaveRatingService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * test class for testing SaveRatingService
 */
@ExtendWith(MockitoExtension.class)
public class SaveRatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private SaveRatingService saveRatingService;

    private Rating rating1;
    private Rating rating2;
    private Rating rating3;
    private List<Rating> ratingList = new ArrayList<>();

    /**
     * Set up test data before each test method execution.
     */
    @BeforeEach
    void setUp() {
        rating1 = new Rating();
        rating1.setId((long)1);
        rating1.setComment("This is the first comment");
        rating1.setPoints(5);

        rating2 = new Rating();
        rating2.setId((long)2);
        rating2.setComment("This is the second comment");
        rating2.setPoints(4);

        rating3 = new Rating();
        rating3.setId((long)3);
        rating3.setComment("This is the third comment");
        rating3.setPoints(3);

        ratingList.add(rating1);
        ratingList.add(rating2);
        ratingList.add(rating3);
    }

    /**
     * Test saving a single rating.
     * @deprecated
     */
    @Test
    void testSaveRating() {
        when(ratingRepository.saveAndFlush(rating1)).thenReturn(rating1);

        Rating savedRating = saveRatingService.saveRating(rating1);

        verify(ratingRepository, times(1)).saveAndFlush(rating1);
        
        assertNotNull(savedRating);
        assertEquals(rating1, savedRating);
    }

    /**
     * Test saving a single empty rating.
     * @deprecated
     */
    @Test
    public void testSaveNullRating() {
        assertNull(saveRatingService.saveRating(null));
    }

    /**
     * Tests saving a list of ratings.
     */
    @Test
    void testSaveAllRatings() {
        when(ratingRepository.saveAllAndFlush(ratingList)).thenReturn(ratingList);

        List<Rating> savedRatings = saveRatingService.saveAllRatings(ratingList);

        verify(ratingRepository, times(1)).saveAllAndFlush(ratingList);
        
        assertNotNull(savedRatings);
        assertEquals(ratingList, savedRatings);
    }

    /**
     * Test saving an empty list of ratings.
     */
    @Test
    public void testSaveNullRatings() {
        assertNull(saveRatingService.saveAllRatings(null));
    }
}