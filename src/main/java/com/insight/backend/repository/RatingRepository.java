package com.insight.backend.repository;

import com.insight.backend.model.Rating;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
