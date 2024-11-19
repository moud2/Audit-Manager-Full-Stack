package com.insight.backend.service.category;

import com.insight.backend.dto.CategoryResponseDTO;
import com.insight.backend.exception.DuplicateCategoryNameException;
import com.insight.backend.model.Category;

import org.springframework.stereotype.Service;

/**
 * Service-class to create categories in the database.
 */
@Service
public class CreateCategoryService {

    private final SaveCategoryService saveCategoryService;
    private final FindCategoryService findCategoryService;

    /**
     * Constructor to inject the SaveCategoryService and FindCategoryService.
     *
     * @param saveCategoryService the service for saving categories
     * @param findCategoryService the service for finding categories
     */
    public CreateCategoryService(SaveCategoryService saveCategoryService, FindCategoryService findCategoryService) {
        this.saveCategoryService = saveCategoryService;
        this.findCategoryService = findCategoryService;
    }

    /**
     * Creates a new category with the given name.
     * If a category with the same name already exists, a DuplicateCategoryNameException is thrown.
     *
     * @param name the name of the category to be created
     * @return a CategoryResponseDTO object containing the id and name of the created category
     */
    public CategoryResponseDTO createCategory(String name) {
        findCategoryService.findCategoryByName(name).ifPresent(category -> {
            throw new DuplicateCategoryNameException(name);
        });
        Category category = new Category();
        category.setName(name);
        Category savedCategory = saveCategoryService.saveCategory(category);
        return new CategoryResponseDTO(savedCategory.getId(), savedCategory.getName());
    }
}
