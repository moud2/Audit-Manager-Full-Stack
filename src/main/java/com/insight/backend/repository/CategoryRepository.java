package com.insight.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insight.backend.model.Category;

/**
 * Repository interface for accessing Category data.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Retrieves all non-deleted categories.
     *
     * @return a list of categories with a null deletion timestamp.
     */
    List<Category> findByDeletedAtIsNull();
}
