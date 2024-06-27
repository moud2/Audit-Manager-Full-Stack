package com.insight.backend.service.Rating;

import com.insight.backend.model.Rating;
import com.insight.backend.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindRatingService {
    RatingRepository ratingRepository;

    public FindRatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Optional<Rating> findRatingById(Long id) {
        return ratingRepository.findById(id);
    }
}
