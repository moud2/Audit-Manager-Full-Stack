package com.insight.backend.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.insight.backend.model.Category;
import com.insight.backend.repository.CategoryRepository;

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
        return categoryRepository.findByDeletedAtIsNull();
    }

    /**
     * @return
     */
    public List<Category> findAllCategoriesIncludingDeleted(){
        return categoryRepository.findAll();
    }

    /**
     * @param includeDeleted
     * @return
     */
    public List<Category> findCategoriesByDeletedStatus(boolean includeDeleted){
        if(includeDeleted = true){
            return categoryRepository.findAll();
        }else{
            return categoryRepository.findByDeletedAtIsNull();
        }

    }
}
