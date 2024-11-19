package com.insight.backend.service.category;

import java.util.List;
import java.util.Optional;

import com.insight.backend.model.Category;
import com.insight.backend.repository.CategoryRepository;

import org.springframework.stereotype.Service;

/**
 * Service-class to find categories in the database.
 */
@Service
public class FindCategoryService {

    private final CategoryRepository categoryRepository;
    
    /**
     * Constructor to inject the CategoryRepository.
     *
     * @param categoryRepository the repository for accessing category data.
     */
    public FindCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Finds a category based on the given id.
     *
     * @param id the id of category to be found.
     * @return an Optional object that includes the category if found, or empty if not found.
     */
    public Optional <Category> findCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    /**
     * Finds all categories in the database.
     *
     * @return a list of all categories.
     */
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Finds a category based on the given name.
     *
     * @param name the name of category to be found.
     * @return an Optional object that includes the category if found, or empty if not found.
     */
    public Optional<Category> findCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }
}