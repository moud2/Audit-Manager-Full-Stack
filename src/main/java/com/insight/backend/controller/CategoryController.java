package com.insight.backend.controller;


import com.insight.backend.model.Category;
import com.insight.backend.service.RatingService;
import com.insight.backend.service.CategoryService;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /*GET request for categories with placeholder data */
    @GetMapping("/api/v1/categories")
    public ResponseEntity<List<Category>> getCategory() {
        List<Category> response = categoryService.getAllCategories();
        return ResponseEntity.ok(response);
    }

}
