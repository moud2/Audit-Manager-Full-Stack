package com.insight.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insight.backend.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
