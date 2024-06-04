package com.insight.backend.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.insight.backend.model.Rating;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloWorldController {

    @GetMapping("/api/hw")
    public ResponseEntity<Map<String, String>> get() {
        Map<String, String> response = new HashMap<>();
        //Rating zum abschicken erstellen
        Rating rating = new Rating();
        rating.setId(1);
        rating.setName("Bob");
        rating.setComment("Kommentar");
        rating.setPoints(3);
        rating.setNA(false);
        response.put("topic", "erster Test");
        response.put("message", "hello world!");
        return ResponseEntity.ok(response);
    }

    // Query-Parameter
    // Beispiel: http://localhost:8080/api/query?name=garfield
    @GetMapping("/api/query")
    public ResponseEntity<Map<String, String>> getWithQueryParameter(@RequestParam(name = "name", required = false, defaultValue = "world") String name) {
        Map<String, String> response = new HashMap<>();
        response.put("topic", "uebung");
        response.put("message", "hello " + name + "!");
        return ResponseEntity.ok(response);
    }

    // Request-Body Beispiel (Post)
    @PostMapping("/api/hw")
    public ResponseEntity<Map<String, String>> postWithRequestBody(@RequestBody Map<String, String> request) {
        String name = request.getOrDefault("name", "world");
        Map<String, String> response = new HashMap<>();
        response.put("topic", "uebung");
        response.put("message", "hello " + name + "!");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
