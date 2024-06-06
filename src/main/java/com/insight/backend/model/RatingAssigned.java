package com.insight.backend.model;

import java.util.HashMap;

/* Temporary Class for testing purposes, may be deleted if unnecessary at a later stage. */
public class RatingAssigned {
    private HashMap<Integer, RatingList> ratingsAssigned = new HashMap<>();

    public void setRatingsAssigned(HashMap<Integer, RatingList> ratingsAssigned) {
        this.ratingsAssigned = ratingsAssigned;
    }

    public HashMap<Integer, RatingList> getRatingsAssigned() {
        return this.ratingsAssigned;
    }
}
