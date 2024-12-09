package com.insight.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.insight.backend.dto.CategoryResponseDTO;
import com.insight.backend.dto.NewCategoryDTO;
import com.insight.backend.model.Category;
import com.insight.backend.service.category.CreateCategoryService;
import com.insight.backend.service.category.DeleteCategoryService;
import com.insight.backend.service.category.FindCategoryService;

import jakarta.validation.Valid;

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
    private final DeleteCategoryService deleteCategoryService;
    /**
     * Constructs a new CategoryController with the specified FindCategoryService.
     * 
     * @param findCategoryService the service to find categories
     * @param createCategoryService the service to create categories
     * @param deleteCategoryService the service to soft delete categories 
     */
    @Autowired
    public CategoryController(FindCategoryService findCategoryService, CreateCategoryService createCategoryService, DeleteCategoryService deleteCategoryService) {
        this.findCategoryService = findCategoryService;
        this.createCategoryService = createCategoryService;
        this.deleteCategoryService = deleteCategoryService;
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

    /**
     * DELETE /categories/{categoryID}
     * Soft deletes a category by its ID.
     *
     * @param id the ID of the category to be deleted
     * @return ResponseEntity with HTTP 204 (No Content) if successful, or HTTP 404 (Not Found) if the category is not found.
     */
    @DeleteMapping("/categories/{categoryID}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        // Find the category by ID
        Category category = findCategoryService.findCategoryById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with ID " + id + " not found"));        
        // Call the delete service
        deleteCategoryService.softDeleteCategory(category);

        // Return HTTP 204 (No Content) if successful
        return ResponseEntity.noContent().build();
    }

}