package com.codechallenge.backend;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
public class HelloWorldController {

    @GetMapping("/api/hw")
    public ResponseEntity<Map<String, String>> get() {
        Map<String, String> response = new HashMap<>();
        response.put("topic", "erster Test");
        response.put("message", "hello world!");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/query")
    public ResponseEntity<Map<String, String>> getWithQueryParameter(@RequestParam(name = "name", required = false, defaultValue = "world") String name) {
        Map<String, String> response = new HashMap<>();
        response.put("topic", "uebung");
        response.put("message", "hello " + name + "!");
        return ResponseEntity.ok(response);
    }

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

    @GetMapping("/api/calc/sum")
    public ResponseEntity<Map<String, Integer>> getSum(@RequestParam("num1") int num1,
                                                       @RequestParam("num2") int num2) {
        Map<String, Integer> response = new HashMap<>();
        int sum = num1 + num2;
        response.put("sum", sum);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/calc/quot")
    public ResponseEntity<Map<String, Number>> getQuot(@RequestParam("num1") Float num1,
                                                       @RequestParam("num2") Float num2) {
        Map<String, Number> response = new HashMap<>();
        Float quot = num1 / num2;
        response.put("quot", quot);
        return ResponseEntity.ok(response);
    }
}
