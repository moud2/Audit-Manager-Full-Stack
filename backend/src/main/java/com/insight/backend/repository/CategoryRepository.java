package com.insight.backend.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.insight.backend.model.Category;

/**
 * Repository interface for accessing Category data.
 */
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    /**
     * Finds a category based on the given name.
     *
     * @param name the name of the category to be found.
     * @return an Optional object that includes the category if found, or empty if not found.
     */
    Optional<Category> findByName(String name);
}