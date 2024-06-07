package com.insight.backend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PostAuditController {

    @PostMapping("/api/v1/audits/new")
    public ResponseEntity<Map<String, Object>> postWithRequestBody(@RequestBody AuditRequest request) {
        Integer ID = 0; 
        // Check if both keys are correctly given
        if (request.getName() == null || request.getCategories() == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "missing input objects");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Create an instance of Create_audit and call the get_input method
        Create_audit createAudit = new Create_audit();
        ID = createAudit.get_input(request.getName(), request.getCategories());
        if (ID == 0) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "failed_to_create");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse); 
        } 


        // Create return message and return if everything is correct
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("id", ID); 
        responseMap.put("name", request.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
    }
}
