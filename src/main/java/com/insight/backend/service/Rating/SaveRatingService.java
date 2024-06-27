package com.insight.backend.service.Rating;

import com.insight.backend.model.Rating;
import com.insight.backend.repository.RatingRepository;
import org.springframework.stereotype.Service;

@Service
public class SaveRatingService {

    private RatingRepository ratingRepository;

    public SaveRatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public void saveRating(Rating rating) {
        ratingRepository.saveAndFlush(rating);
    }
}
