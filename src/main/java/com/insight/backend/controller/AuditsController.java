package com.insight.backend.controller;

import java.util.*;

import com.insight.backend.model.Audit;
import com.insight.backend.model.newAudit.AuditRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuditsController {

    @GetMapping("api/v1/audits")
    public ResponseEntity<List<Audit>> getAudits() {
        List<Audit> auditList = new ArrayList<>();

        // TODO: Temporary code for basic functionality | remove and reimplement properly later
        Audit audit1 = new Audit("Security Assessment; Kunde: Google LTD", Set.of());
        Audit audit2 = new Audit("Pentest; Kunde: Amazon LTD", Set.of());
        audit1.setId((long) 1);
        audit2.setId((long) 2);
        auditList.add(audit1);
        auditList.add(audit2);

        return ResponseEntity.ok(auditList);
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