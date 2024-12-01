package com.insight.backend.service.category;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.insight.backend.model.Category;
import com.insight.backend.repository.CategoryRepository;

/**
 * Service for handling category deletion with a soft delete approach.
 * Marks the category as deleted without removing it from the database.
 */
@Service
public class DeleteCategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Initializes the service with the given CategoryRepository.
     *
     * @param categoryRepository the repository used to manage Category entities.
     */
    public DeleteCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Soft deletes the given category by setting a deletion timestamp.
     * Ensures the category is not linked to active audits before deleting.
     *
     * @param category the category to be soft deleted.
     * @throws IllegalStateException if the category is already deleted or linked to active audits.
     */
    public void softDeleteCategory(Category category) {
        if (category == null || category.isDeleted()) {
            throw new IllegalStateException("Category is already deleted or does not exist.");
        }

        boolean isLinkedToAudit = category.getQuestions().stream()
            .flatMap(question -> question.getRating().stream()) // Extract all ratings from questions
            .anyMatch(rating -> rating.getAudit() != null && rating.getAudit().getDeletedAt() == null); // Check for active audits

        if (isLinkedToAudit) {
            throw new IllegalStateException("Category is linked to existing audits and cannot be deleted.");
        }

        // SoftDelete 
        category.setDeletedAt(LocalDateTime.now());
        categoryRepository.save(category);
    }
}
