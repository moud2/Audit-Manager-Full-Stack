package com.insight.backend.model;

import java.util.HashMap;

public class RatingAssigned {
    private HashMap<Integer, RatingList> ratingsAssigned = new HashMap<Integer, RatingList>();

    public void setRatingsAssigned(HashMap<Integer, RatingList> ratingsAssigned) {
        this.ratingsAssigned = ratingsAssigned;
    }

    public HashMap<Integer, RatingList> getRatingsAssigned() {
        return ratingsAssigned;
    }
}
