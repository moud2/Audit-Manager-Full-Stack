package com.insight.backend.service.category;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.insight.backend.model.Category;
import com.insight.backend.repository.CategoryRepository;


@Service
public class DeleteCategoryService {
    private CategoryRepository categoryRepository;

    public DeleteCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * 
     */
    public void softDeleteCategory(Category category){
        if (category == null || category.isDeleted()) {
            throw new IllegalStateException("Category is already deleted or does not exist.");
        }
        boolean isLinkedToAudit = category.getQuestions().stream()
        .flatMap(question -> question.getRating().stream()) 
        .anyMatch(rating -> rating.getAudit() != null && rating.getAudit().getDeletedAt() == null);

        if (isLinkedToAudit) {
            throw new IllegalStateException("Category is linked to existing audits and cannot be deleted.");
        }

        // SoftDelete 
        category.setDeletedAt(LocalDateTime.now());
        categoryRepository.save(category);

    }
    
}

