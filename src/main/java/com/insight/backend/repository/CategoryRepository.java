package com.insight.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insight.backend.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}