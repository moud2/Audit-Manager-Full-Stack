package com.insight.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.insight.backend.model.Rating;
import com.insight.backend.repository.RatingRepository;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    public List<Rating> getAll() {
        return ratingRepository.findAll();
    }

    public Optional<Rating> get(Long id) {
        return ratingRepository.findById(id);
    }

    public Rating create(Rating rating) {
        return ratingRepository.save(rating);
    }

    public void delete(Long id) {
        ratingRepository.deleteById(id);
    }
}