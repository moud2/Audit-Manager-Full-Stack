package com.insight.backend.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.insight.backend.model.Rating;

@RestController
public class GetRatingController {

    @GetMapping("/api/v1/audits/1/ratings")
    public ResponseEntity<Map<String, String>> get() {
        Map<String, String> response = new HashMap<>();
        //Rating zum Abschicken erstellen
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

    /*@GetMapping("/api/v1/audits/{id}/ratings")
    public ResponseEntity<Map<String, String>> get() {
        Map<String, String> response = new HashMap<>();
        //Rating zum Abschicken erstellen
        Rating rating = new Rating();
        rating.setId(1);
        rating.setName("Bob");
        rating.setComment("Kommentar");
        rating.setPoints(3);
        rating.setNA(false);
        response.put("topic", "erster Test");
        response.put("message", "hello world!");
        return ResponseEntity.ok(response);*/
    
    }