package com.insight.backend.controller;

import java.util.*;

import com.insight.backend.model.Audit;
import com.insight.backend.model.newAudit.AuditRequest;
import com.insight.backend.service.audit.FindAuditService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuditsController {

    /* */
    private final FindAuditService findAuditService;

    /* */
    @Autowired
    public AuditsController(FindAuditService findAuditService) {
        this.findAuditService = findAuditService; 
    }

    /* */
    @GetMapping("api/v1/audits")
    public ResponseEntity<List<Audit>> getAudits() {
        List<Audit> response = findAuditService.findAllAudits();

        return ResponseEntity.ok(response);
    }

    Integer ID = 1; 

    @PostMapping("/api/v1/audits/new")
    public ResponseEntity<Map<String, Object>> postWithRequestBody(@RequestBody com.insight.backend.model.newAudit.AuditRequest request) {

        // Check if both keys are correctly given
        if (request.getName() == null || request.getCategories() == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "missing input objects");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Create an instance of Create_audit and call the get_input method
        ID++;
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