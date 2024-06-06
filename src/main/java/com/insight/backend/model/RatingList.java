package com.insight.backend.model;

import java.util.List;

public class RatingList {
    private List<Rating> ratings;

    public RatingList(List<Rating> ratings) {
        setRatings(ratings);
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
