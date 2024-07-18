package com.insight.backend.controller;

import java.util.List;

import com.insight.backend.model.Category;
import com.insight.backend.service.category.FindCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CategoryController is a REST controller that handles HTTP requests related to categories.
 */
@RestController
public class CategoryController {
    /** 
     * The FindCategoryService to use the service methods.
     */
    private final FindCategoryService findCategoryService;

    /**
     * Constructs a new CategoryController with the specified FindCategoryService.
     * 
     * @param findCategoryService the service to find categories
     */
    @Autowired
    public CategoryController(FindCategoryService findCategoryService) {
        this.findCategoryService = findCategoryService; 
    }

    /**
     * GET requests for retrieving all categories.
     * 
     * @return a ResponseEntity containing a list of Category objects
     */
    @GetMapping("/api/v1/categories")
    public ResponseEntity<List<Category>> getCategory() {
        List<Category> response = findCategoryService.findAllCategories();
        
        return ResponseEntity.ok(response);
    }
}
