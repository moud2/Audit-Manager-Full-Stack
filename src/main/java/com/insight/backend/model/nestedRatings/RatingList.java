package com.insight.backend.model.nestedRatings;

import java.util.List;

import com.insight.backend.model.Rating;

/**
 * Represents a list of ratings.
 */
public class RatingList {
    private List<Rating> ratings;

    /**
     * Constructs a new RatingList with the specified list of ratings.
     *
     * @param ratings the list of ratings to initialize the RatingList with
     */
    public RatingList(List<Rating> ratings) {
        setRatings(ratings);
    }

    /**
     * Returns the list of ratings.
     *
     * @return the list of ratings
     */
    public List<Rating> getRatings() {
        return this.ratings;
    }

    /**
     * Sets the list of ratings.
     *
     * @param ratings the list of ratings to set
     */
    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
