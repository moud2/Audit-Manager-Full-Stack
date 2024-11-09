package com.insight.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.insight.backend.model.Category;

/**
 * Repository interface for accessing Category data.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> ,JpaSpecificationExecutor<Category>  {

    /**
     * Retrieves all non-deleted categories.
     *
     * @return a list of categories with a null deletion timestamp.
     */
    List<Category> findByDeletedAtIsNull();

    /**
     * Retrieves all categories, including deleted ones.
     * This is inherited from JpaRepository.
     *
     * @return a list of all categories, regardless of the deletion timestamp.
     */
    @Override
    List<Category> findAll();
}
