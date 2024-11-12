package com.insight.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insight.backend.model.Category;
import com.insight.backend.service.category.FindCategoryService;

/**
 * CategoryController handles HTTP requests related to categories.
 */
@RestController
public class CategoryController {
    
    private final FindCategoryService findCategoryService;

    /**
     * Constructs a new CategoryController with the specified FindCategoryService.
     * 
     * @param findCategoryService the service to find categories.
     */
    @Autowired
    public CategoryController(FindCategoryService findCategoryService) {
        this.findCategoryService = findCategoryService; 
    }

    /**
     * GET request for retrieving all categories.
     * Optionally includes deleted categories if the 'includeDeleted' parameter is set to true.
     * 
     * @param includeDeleted flag to include deleted categories.
     * @return a ResponseEntity containing a list of Category objects.
     */
    @GetMapping("/api/v1/categories")
    public ResponseEntity<List<Category>> getCategories(
            @RequestParam(value = "includeDeleted", defaultValue = "false") boolean includeDeleted) {
        
        List<Category> response;
        if (includeDeleted) {
            response = findCategoryService.findAllCategoriesIncludingDeleted(); // Includes deleted categories
        } else {
            response = findCategoryService.findAllCategories(); // Only non-deleted categories
        }
        return ResponseEntity.ok(response);
    }

    /**
     * GET request for retrieving a category by its ID.
     * 
     * @param id the ID of the category to retrieve.
     * @return a ResponseEntity containing the found Category object, or 404 if not found.
     */
    @GetMapping("/api/v1/categories/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return findCategoryService.findCategoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
