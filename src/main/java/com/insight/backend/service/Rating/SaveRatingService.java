package com.insight.backend.service.Rating;

import java.util.*;

import com.insight.backend.repository.RatingRepository;
import com.insight.backend.model.Rating;

import org.springframework.stereotype.Service;

/**
 * SaveRatingService is a service, which is responsible for storing rating objects in the database.
 */
@Service
public class SaveRatingService {

    final RatingRepository ratingRepository;

    /**
     * constructor
     * 
     * @param ratingRepository is an instance of RatingRepository used for JpaRepository operations.
     */
    public SaveRatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    /**
     * Stores a single rating object in the database.
     * 
     * @param rating the rating objekt to be saved.
     * @return the saved rating object.
     */
    public Rating saveRating(Rating rating) {
        if (rating == null) return null;
        return ratingRepository.saveAndFlush(rating);
    }

    /**
    * Stores multiple rating objects in the database.
    * 
    * @param ratingList is a list of rating objects to save.
    * @return the saved list of rating objects.
    */
    public List<Rating> saveAllRatings(List<Rating> ratingList) {
        if (ratingList == null) return null;
        return ratingRepository.saveAllAndFlush(ratingList);
    }
}