package com.insight.backend.service.category;

import com.insight.backend.model.Category;
import com.insight.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FindCategoryService {

    private final CategoryRepository categoryRepository;

    public FindCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Retrieves all non-deleted categories.
     *
     * @return a list of categories that have not been marked as deleted.
     */
    public List<Category> findAllCategories() {
        // Fetch only non-deleted categories (deletedAt is null)
        return categoryRepository.findByDeletedAtIsNull();
    }

    /**
     * Retrieves all categories, including deleted ones.
     *
     * @return a list of categories, including deleted ones.
     */
    public List<Category> findAllCategoriesIncludingDeleted() {
        // Fetch all categories, including those marked as deleted
        return categoryRepository.findAll();
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
}
