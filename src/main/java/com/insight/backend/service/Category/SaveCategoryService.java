package com.insight.backend.service.Category;

import com.insight.backend.repository.CategoryRepository;
import com.insight.backend.model.Category;
import org.springframework.stereotype.Service;

@Service
public class SaveCategoryService {
    final CategoryRepository categoryRepository;

    public SaveCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
}
