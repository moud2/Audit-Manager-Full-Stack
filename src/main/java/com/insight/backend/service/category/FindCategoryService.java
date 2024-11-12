package com.insight.backend.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.insight.backend.model.Category;
import com.insight.backend.repository.CategoryRepository;
import com.insight.backend.specifications.CategorySpecifications;

/**
 * Service class for managing and retrieving categories.
 */
@Service
public class FindCategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Constructs a FindCategoryService with the specified CategoryRepository.
     *
     * @param categoryRepository the repository for accessing category data.
     */
    public FindCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id the ID of the category to find.
     * @return an Optional containing the category if found, or empty if not.
     */
    public Optional<Category> findCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    /**
     * Retrieves all non-deleted categories.
     *
     * @return a list of categories that have not been marked as deleted.
     */
    public List<Category> findAllCategories() {
        // Use the Specification for filtering non-deleted categories
        Specification<Category> spec = CategorySpecifications.isNotDeleted();
        return categoryRepository.findAll(spec);
    }

    /**
     * Retrieves all categories, including deleted ones, using the specification.
     * 
     * @return a list of all categories, both deleted and non-deleted.
     */
    public List<Category> findAllCategoriesIncludingDeleted() {
        return categoryRepository.findAll(); // Fetch all categories, including deleted ones
    }

    /**
     * Retrieves categories filtered by whether they are deleted or not based on a flag.
     * 
     * @param includeDeleted boolean flag indicating whether to include deleted categories.
     * @return a list of categories filtered by the deletion status.
     */
    public List<Category> findCategoriesByDeletedStatus(boolean includeDeleted) {
        Specification<Category> spec;
        if (includeDeleted) {
            spec = Specification.where(null); // No filter for deletedAt, so include all categories
        } else {
            spec = CategorySpecifications.isNotDeleted();
        }
        return categoryRepository.findAll(spec);
    }
}
