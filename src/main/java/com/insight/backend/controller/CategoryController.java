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
        response.add(new Category("a", Set.of()));
        response.add(new Category("b", Set.of()));
        response.add(new Category("c", Set.of()));
        return ResponseEntity.ok(response);
    }
}
