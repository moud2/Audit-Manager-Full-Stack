package com.insight.backend.service.rating;

import java.util.List;
import java.util.Optional;

import com.insight.backend.model.Rating;
import com.insight.backend.repository.RatingRepository;

import org.springframework.stereotype.Service;

@Service
public class FindRatingService {
    RatingRepository ratingRepository;

    public FindRatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Optional<Rating> findRatingById(Long id) {
        return ratingRepository.findById(id);
    }

    public List<Rating> findAllRatings() {
        return ratingRepository.findAll();
    }
}
