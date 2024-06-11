package com.insight.backend.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.insight.backend.model.Category;
import com.insight.backend.model.Rating;
import com.insight.backend.model.RatingList;

/**
 * Controller for handling rating-related requests.
 */
@RestController
public class GetRatingController {

    /**
     * Handles GET requests for retrieving ratings for a specific audit.
     *
     * @param auditId the ID of the audit
     * @return a ResponseEntity containing the ratings in JSON format or an error message if the audit ID does not exist
     */
    @GetMapping("/api/v1/audits/{auditId}/ratings")
    public ResponseEntity<RatingList> get(@PathVariable("auditId") Integer auditId) {

        // Generate Test-Categories
        Category category1 = new Category(0, "categorytest1");
        Category category2 = new Category(0, "categorytest2");
        Category category3 = new Category(0, "categorytest3");

        // Generate Test-Ratings
        Rating rating1 = new Rating(1, category1, "Bob", 0, "Kommentar1", true);
        Rating rating2 = new Rating(2, category2, "Ben", 1, "Kommentar2", false);
        Rating rating3 = new Rating(3, category3, "Boris", 2, "Kommentar3", false);
        Rating rating4 = new Rating(4, category1, "Berthold", 3, "Kommentar4", false);

        // Generate Test-Lists containing Test-Ratings
        List<Rating> ratings1 = new ArrayList<>();
        ratings1.add(rating1);
        ratings1.add(rating2);
        RatingList ratingList1 = new RatingList(ratings1);

        List<Rating> ratings2 = new ArrayList<>();
        ratings2.add(rating3);
        ratings2.add(rating4);
        RatingList ratingList2 = new RatingList(ratings2);

        // Contain Test-Lists within HashMap as Audit-Simulation (Key-ID = AuditID)
        HashMap<Integer, RatingList> ratingsAssigned = new HashMap<>();
        ratingsAssigned.put(1, ratingList1);
        ratingsAssigned.put(2, ratingList2);

        // Error Handling 404 - Non-existing Audit
        if (ratingsAssigned.containsKey(auditId)) {
            return ResponseEntity.ok(ratingsAssigned.get(auditId));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}