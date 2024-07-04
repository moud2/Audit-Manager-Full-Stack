package com.insight.backend.service.category;

import com.insight.backend.repository.CategoryRepository;
import com.insight.backend.model.Category;
import org.springframework.stereotype.Service;

@Service
public class SaveCategoryService {
    private final CategoryRepository categoryRepository;

    public SaveCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Saves a given category to the db
     *
     * @param category the category to be saved
     * @return the saved category, or null if the input category is null
     */
    public Category saveCategory(Category category) {
        return categoryRepository.saveAndFlush(category);
    }
}
