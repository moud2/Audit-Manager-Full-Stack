package com.insight.backend.controller;

import java.util.List;

import jakarta.validation.Valid;

import com.insight.backend.dto.CategoryResponseDTO;
import com.insight.backend.dto.NewCategoryDTO;
import com.insight.backend.model.Category;
import com.insight.backend.service.category.CreateCategoryService;
import com.insight.backend.service.category.FindCategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private final CreateCategoryService createCategoryService;

    /**
     * Constructs a new CategoryController with the specified FindCategoryService.
     * 
     * @param findCategoryService the service to find categories
     */
    @Autowired
    public CategoryController(FindCategoryService findCategoryService, CreateCategoryService createCategoryService) {
        this.findCategoryService = findCategoryService;
        this.createCategoryService = createCategoryService;
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

    /**
     * POST requests for creating a new category.
     *
     * @param newCategoryDTO the new category to be created
     * @return a ResponseEntity containing the created category
     */
    @PostMapping("/api/v1/categories/new")
    public ResponseEntity<Object> createCategory(@Valid @RequestBody NewCategoryDTO newCategoryDTO) {
        CategoryResponseDTO responseDTO = createCategoryService.createCategory(newCategoryDTO.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}