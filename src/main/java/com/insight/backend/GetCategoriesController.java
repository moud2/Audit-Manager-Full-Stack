package com.insight.backend;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class GetCategoriesController {

    @GetMapping("/api/hw")
    public ResponseEntity<List<Category>> getCategory() {
        List<Category> response = new ArrayList<Category>();
        response.add(new Category(0, "a"));
        response.add(new Category(1, "b"));
        response.add(new Category(2, "c"));
        return ResponseEntity.ok(response);
    }
}
