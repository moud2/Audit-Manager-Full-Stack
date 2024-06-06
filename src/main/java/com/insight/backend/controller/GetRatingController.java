package com.insight.backend.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
//import org.springframework.http.HttpStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import com.insight.backend.model.Rating;
import com.insight.backend.model.RatingList;

@RestController
public class GetRatingController {

    @GetMapping("/api/v1/audits/{ratingId}/ratings")
    public ResponseEntity<String> get(@PathVariable("ratingId") Integer ratingId) {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        //Rating 1 und 2 zum Abschicken erstellen
        Rating rating1 = new Rating(1, "Bob", "Kommentar1", 0, true);
        Rating rating2 = new Rating(2, "Ben", "Kommentar2", 1, false);
        Rating rating3 = new Rating(2, "Boris", "Kommentar3", 2, false);
        Rating rating4 = new Rating(2, "Berthold", "Kommentar4", 3, false);

        //Liste aus Ratings machen
        List<Rating> ratings1 = new ArrayList<Rating>();
        ratings1.add(rating1);
        ratings1.add(rating2);
        RatingList ratingList1 = new RatingList(ratings1);
        List<Rating> ratings2 = new ArrayList<Rating>();
        ratings2.add(rating3);
        ratings2.add(rating4);
        RatingList ratingList2 = new RatingList(ratings2);

        HashMap<Integer, RatingList> ratingsAssigned = new HashMap<Integer, RatingList>();
        ratingsAssigned.put(1, ratingList1);
        ratingsAssigned.put(2, ratingList2);
        RatingList ratingListSend = ratingsAssigned.get(ratingId);
        //return ResponseEntity.ok(gson.toJson(ratingList));
        return ResponseEntity.ok(gson.toJson(ratingListSend));
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