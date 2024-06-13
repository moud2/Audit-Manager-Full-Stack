package com.insight.backend.controller;

import com.insight.backend.model.Rating;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RatingController {

    private List<Rating> ratings;

    /**
     * Initializes the RatingController with a list of sample ratings.
     */
    public RatingController() {
        ratings = new ArrayList<>();

        Rating rating1 = new Rating();
        rating1.setId(1);
        rating1.setName("Mahamoud");
        rating1.setComment("This is the first comment");
        rating1.setPoints(5);

        Rating rating2 = new Rating();
        rating2.setId(2);
        rating2.setName("Ahmed");
        rating2.setComment("This is the second comment");
        rating2.setPoints(4);

        Rating rating3 = new Rating();
        rating3.setId(3);
        rating3.setName("John");
        rating3.setComment("This is the third comment");
        rating3.setPoints(3);

        ratings.add(rating1);
        ratings.add(rating2);
        ratings.add(rating3);
    }

    /**
     * Updates an existing rating based on the provided id and JSON request body.
     *
     * @param id            the id of the rating to update
     * @param updatedRating the updated rating object from the JSON request body
     * @return a ResponseEntity indicating the result of the update operation
     */
    @PatchMapping("/api/v1/ratings/{id}")
    public ResponseEntity<String> updateRating(@PathVariable("id") int id, @RequestBody Rating updatedRating) {
        for (Rating rating : ratings) {
            if (rating.getId() == id) {
                if (updatedRating.getComment() != null) {
                    rating.setComment(updatedRating.getComment());
                }
                if (updatedRating.getPoints() <= 5) {
                    rating.setPoints(updatedRating.getPoints());
                }
                if (updatedRating.getName() != null) {
                    rating.setName(updatedRating.getName());
                }
                if (updatedRating.getNA() != null) {
                    rating.setNA(updatedRating.getNA());
                }
                return ResponseEntity.ok("Successful operation");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("RatingID not found");
    }
}
