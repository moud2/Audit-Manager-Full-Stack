package com.insight.backend;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


public class PostAuditController {
    @PostMapping("/api/v1/audits/new")
    public ResponseEntity<Map<String, String>> postWithRequestBody(@RequestBody AuditRequest request) {
       
        //check for missing objects 
        if (request.getName() == null || request.getCategories() == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "missing input objects");
            return ResponseEntity.status(HttpStatus.valueOf(400)).body(errorResponse);

        }

        //create response mesaage
        Map<String, String> response_map = new HashMap<>();
        response_map.put("id", "0");
        response_map.put("name", request.getName());

        //write data to server or create audit or something



        return ResponseEntity.status(HttpStatus.CREATED).body(response_map);

    }
}
    

