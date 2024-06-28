package com.insight.backend.service.Category;
import org.springframework.stereotype.Service;

import com.insight.backend.model.Category;
import com.insight.backend.repository.CategoryRepository;

/**
 * Service-class to find categpries in the database.
 */
@Service
public class FindCategoryService {

    private final  CategoryRepository categoryRepository;

    
    /**
     * construktor to injekt the CategoryRepository.
     *
     * @param categoryRepository the repository  for accessing auf category data.
     */
    public FindCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * finds a category based on the given id.
     *
     * @param id the id of category to be found.
     * @return an Optional-Objekt, that includes the category, if finded or empty if not funded.
     */
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }
}
