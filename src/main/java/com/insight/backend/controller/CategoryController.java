package com.insight.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.insight.backend.model.Category;
import com.insight.backend.service.category.FindCategoryService;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
public class CategoryController {
    /* FindCategoryService to use the service methods */
    private final FindCategoryService findCategoryService;

    /* Constructor with FindCategoryService */
    @Autowired
    public CategoryController(FindCategoryService findCategoryService) {
        this.findCategoryService = findCategoryService; 
    }

    /* GET request for categories from Service class */
    @GetMapping("/api/v1/categories")
    public ResponseEntity<List<Category>> getCategory() {
        List<Category> response = findCategoryService.findAllCategories();
        
        return ResponseEntity.ok(response);
    }
}
