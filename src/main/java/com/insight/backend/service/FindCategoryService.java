package com.insight.backend.service;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.insight.backend.model.Category;
import com.insight.backend.repository.CategoryRepository;

@Service
public class FindCategoryService {

    private  CategoryRepository categoryRepository;

    
    public FindCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Optional<Category> findCategoryById(Long id) {
        return categoryRepository.findById(id);
    }
}
