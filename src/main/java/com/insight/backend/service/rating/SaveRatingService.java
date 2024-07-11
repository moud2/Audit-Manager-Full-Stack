package com.insight.backend.service.rating;

import java.util.*;

import com.insight.backend.repository.RatingRepository;
import com.insight.backend.model.Rating;

import org.springframework.stereotype.Service;

/**
 * SaveRatingService is a service that is responsible for storing rating objects in the database.
 */
@Service
public class SaveRatingService {

    /** 
     * The RatingRepository to use the JpaRepository methods.
     */
    final RatingRepository ratingRepository;

    /**
     * Constructs a new RatingRepository with the specified RatingRepository.
     * 
     * @param ratingRepository the repository to save ratings
     */
    public SaveRatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    /**
     * Stores a single rating object in the database.
     * 
     * @param rating the rating objekt to be saved.
     * @return the saved rating object, or null if the input question is null
     */
    public Rating saveRating(Rating rating) {
        if (rating == null) return null;
        return ratingRepository.saveAndFlush(rating);
    }

    /**
    * Stores multiple rating objects in the database.
    * 
    * @param ratingList the list of rating objects to be saved.
    * @return the saved list of rating objects, or null if the input question is null
    */
    public List<Rating> saveAllRatings(List<Rating> ratingList) {
        if (ratingList == null) return null;
        return ratingRepository.saveAllAndFlush(ratingList);
    }
}