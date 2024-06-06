package com.insight.backend.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import com.insight.backend.model.Category;
import com.insight.backend.model.Rating;
import com.insight.backend.model.RatingList;

@RestController
public class GetRatingController {

    @GetMapping("/api/v1/audits/{auditId}/ratings")
    public ResponseEntity<String> get(@PathVariable("auditId") Integer auditId) {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();

        //Test-Kategorien generieren
        Category category1 = new Category(0, "categorytest1");
        Category category2 = new Category(0, "categorytest2");
        Category category3 = new Category(0, "categorytest3");
        
        //Rating 1 und 2 zum Abschicken erstellen
        Rating rating1 = new Rating(1, category1, "Bob", 0 , "Kommentar1", true);
        Rating rating2 = new Rating(2, category2, "Ben", 1, "Kommentar2", false);
        Rating rating3 = new Rating(3, category3, "Boris", 2, "Kommentar3", false);
        Rating rating4 = new Rating(4, category1, "Berthold", 3, "Kommentar4", false);

        //Liste aus Ratings erstellen
        List<Rating> ratings1 = new ArrayList<Rating>();
        ratings1.add(rating1);
        ratings1.add(rating2);
        RatingList ratingList1 = new RatingList(ratings1);
        List<Rating> ratings2 = new ArrayList<Rating>();
        ratings2.add(rating3);
        ratings2.add(rating4);
        RatingList ratingList2 = new RatingList(ratings2);

        //Ratings in per ID abrufbarer Map -> AuditID-Simulation
        HashMap<Integer, RatingList> ratingsAssigned = new HashMap<Integer, RatingList>();
        ratingsAssigned.put(1, ratingList1);
        ratingsAssigned.put(2, ratingList2);
        
        //Error Handling 404 - Nicht existierendes Audit
        if (ratingsAssigned.containsKey(auditId)){
            RatingList ratingListSend = ratingsAssigned.get(auditId);
            return ResponseEntity.ok(gson.toJson(ratingListSend));
        } else {
            return ResponseEntity.status(404).body("auditID not existing");
        }
    }
}