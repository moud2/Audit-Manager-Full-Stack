package com.insight.backend.model;

import java.util.HashMap;

/**
 * Represents a temporary class for testing purposes, 
 * which may be deleted if unnecessary at a later stage.
 */
public class RatingAssigned {
    private HashMap<Integer, RatingList> ratingsAssigned = new HashMap<>();

    /**
     * Sets the map of ratings assigned to audits.
     *
     * @param ratingsAssigned the map to set, where the key is the audit ID and the value is the list of ratings
     */
    public void setRatingsAssigned(HashMap<Integer, RatingList> ratingsAssigned) {
        this.ratingsAssigned = ratingsAssigned;
    }

    /**
     * Returns the map of ratings assigned to audits.
     *
     * @return the map of ratings assigned, where the key is the audit ID and the value is the list of ratings
     */
    public HashMap<Integer, RatingList> getRatingsAssigned() {
        return this.ratingsAssigned;
    }
}