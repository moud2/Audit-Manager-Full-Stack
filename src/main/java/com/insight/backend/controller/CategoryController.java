package com.insight.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.insight.backend.model.Category;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
public class CategoryController {

    /*GET request for categories with placeholder data */
    @GetMapping("/api/v1/categories")
    public ResponseEntity<List<Category>> getCategory() {
        List<Category> response = new ArrayList<>();
        Category category1 = new Category("a", Set.of());
        Category category2 = new Category("b", Set.of());
        Category category3 = new Category("c", Set.of());
        category1.setId((long) 1);
        category2.setId((long) 2);
        category3.setId((long) 3);
        response.add(category1);
        response.add(category2);
        response.add(category3);
        return ResponseEntity.ok(response);
    }
}
