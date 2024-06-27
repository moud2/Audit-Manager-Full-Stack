package com.insight.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.insight.backend.model.Category;
import com.insight.backend.service.Category.FindCategoryService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
public class CategoryController {

    private FindCategoryService findCategoryService = new FindCategoryService();

    /*GET request for categories with placeholder data */
    @GetMapping("/api/v1/categories")
    public ResponseEntity<List<Category>> getCategory() {
        List<Category> response = findCategoryService.findAllCategories();
        return ResponseEntity.ok(response);
    }
}
