package com.insight.backend.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.insight.backend.model.Rating;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PatchController {

    private List<Rating> ratings;

    public PatchController() {
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

    @PatchMapping("/api/v1/ratings/{id}")
    public String updateRating(@PathVariable("id") int id, @RequestBody String requestBody) {
        Gson gson = new Gson();
        Rating updatedRating = gson.fromJson(requestBody, Rating.class);

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
                return "Operation Succesful";
            }
        }
        return "Rating not found";
    }

    @GetMapping("/api/v1/ratings")
    public String getRatings() {
        Gson gson = new Gson();
        return gson.toJson(ratings);
    }
}
