package com.insight.backend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
public class PostAuditController {

    @PostMapping("/api/v1/audits/new")
    public ResponseEntity<Map<String, String>> postWithRequestBody(@RequestBody AuditRequest request) {
        if (request.getName() == null || request.getCategories() == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "missing input objects");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    
        }

        Map<String, String> response_map = new HashMap<>();
        response_map.put("id", "0");
        response_map.put("name", request.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(response_map);
    }

    
}
