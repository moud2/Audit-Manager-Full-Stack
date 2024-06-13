package com.insight.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insight.backend.repository.RatingRepository;
import com.insight.backend.model.Rating;

import java.util.List;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }
}
