package com.insight.backend.service.Rating;

import com.insight.backend.repository.RatingRepository;
import com.insight.backend.model.Rating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveRatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public Rating saveRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    public List<Rating> saveAllRatings(List<Rating> ratingList) {
        RatingRepository.saveAll(ratingList);
        return ratingList;
    }
}