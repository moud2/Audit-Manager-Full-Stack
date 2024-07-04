package com.insight.backend.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.insight.backend.model.Rating;
import com.insight.backend.repository.RatingRepository;
import com.insight.backend.service.rating.FindRatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Test class for FindRatingService.
 */
@ExtendWith(MockitoExtension.class)
public class FindRatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private FindRatingService findRatingService;

    private Rating rating1;
    private Rating rating2;

    /**
     * Set up method to initialize test data.
     */
    @BeforeEach
    void setUp() {
        rating1 = new Rating();
        rating1.setId(1L);
        rating1.setComment("This is the first comment");
        rating1.setPoints(5);

        rating2 = new Rating();
        rating2.setId(2L);
        rating2.setComment("This is the second comment");
        rating2.setPoints(4);
    }

    /**
     * Test case for finding a Rating by ID when the category is found.
     */
    @Test
    void testFindRatingsById_found() {
        when(ratingRepository.findById(1L)).thenReturn(Optional.of(rating1));
        Optional<Rating> foundRating = findRatingService.findRatingById(1L);
        assertEquals(rating1, foundRating.get());
    }

    /**
     * Test case for finding a rating by ID when the category is not found.
     */
    @Test
    void testFindRatingById_notFound() {
        when(ratingRepository.findById(anyLong())).thenReturn(Optional.empty());
        Optional<Rating> foundRating = findRatingService.findRatingById(1L);
        assertTrue(foundRating.isEmpty());
    }

    /**
     * Test case for finding all ratings.
     */
    @Test
    void testFindAllCRatings() {
        List<Rating> ratings = Arrays.asList(rating1, rating2);
        when(ratingRepository.findAll()).thenReturn(ratings);
        List<Rating> foundRatings = findRatingService.findAllRatings();
        assertEquals(ratings, foundRatings);
    }
}
