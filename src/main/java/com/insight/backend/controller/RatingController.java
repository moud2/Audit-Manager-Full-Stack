package com.insight.backend.controller;

import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.insight.backend.model.Rating;
import com.insight.backend.model.nestedRatings.RatingList;
import com.insight.backend.exception.RatingNotFoundException;

import com.insight.backend.service.Rating.FindRatingService;
import com.insight.backend.service.Rating.SaveRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RatingController {

    private final ObjectMapper objectMapper;
    private final FindRatingService findRatingService;
    private final SaveRatingService saveRatingService;

    /**
     * Initializes the RatingController with a list of sample ratings.
     */
    @Autowired
    public RatingController(ObjectMapper objectMapper, FindRatingService findRatingService, SaveRatingService saveRatingService) {
        this.objectMapper = objectMapper;
        this.findRatingService = findRatingService;
        this.saveRatingService = saveRatingService;
//        ratings = new ArrayList<>();
//
//        Rating rating1 = new Rating();
//        rating1.setId((long) 1);
//        //rating1.setName("Mahamoud");
//        rating1.setComment("This is the first comment");
//        rating1.setPoints(5);
//
//        Rating rating2 = new Rating();
//        rating2.setId((long) 2);
//        //rating2.setName("Ahmed");
//        rating2.setComment("This is the second comment");
//        rating2.setPoints(4);
//
//        Rating rating3 = new Rating();
//        rating3.setId((long) 3);
//        //rating3.setName("John");
//        rating3.setComment("This is the third comment");
//        rating3.setPoints(3);
//
//        ratings.add(rating1);
//        ratings.add(rating2);
//        ratings.add(rating3);
    }

    /**
     * Handles PATCH requests for updating a rating.
     *
     *
     * @param id the ID of the rating to update
     * @param patch the JSON patch containing the changes to apply
     * @return a ResponseEntity containing the updated rating in JSON format or an error message if the rating ID does not exist
     */
    @PatchMapping("/api/v1/ratings/{id}")
    public ResponseEntity<Rating> updateRating(@PathVariable("id") long id, @RequestBody JsonPatch patch){
        try {
            Rating entity = findRatingService.findRatingById(id).orElseThrow(RatingNotFoundException::new);
            JsonNode entityJsonNode = objectMapper.convertValue(entity, JsonNode.class);
            JsonNode patchedEntityJsonNode = patch.apply(entityJsonNode);
            Rating patchedEntity = objectMapper.treeToValue(patchedEntityJsonNode, Rating.class);
            Rating updatedEntity = saveRatingService.saveRating(patchedEntity);
            return ResponseEntity.ok(updatedEntity);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


/**
 * Controller for handling rating-related requests.
 */

    /**
     * Handles GET requests for retrieving ratings for a specific audit.
     *
     * @param auditId the ID of the audit
     * @return a ResponseEntity containing the ratings in JSON format or an error message if the audit ID does not exist
     */
    @GetMapping("/api/v1/audits/{auditId}/ratings")
    public ResponseEntity<List<Map<String, Object>>> get(@PathVariable("auditId") Integer auditId) {

        // Generate Test-Categories
        /*Category category1 = new Category("categorytest1", null);
        Category category2 = new Category("categorytest2", null);
        Category category3 = new Category("categorytest3", null);*/

        // Generate Test-Ratings
        Rating rating1 = new Rating("Bob", false, "KOmmentar", 0, null, null);
        Rating rating2 = new Rating("Ben", false, "Kommentar2", 3, null, null);
        Rating rating3 = new Rating("Boris", false, "Kommentar3", 1, null, null);
        Rating rating4 = new Rating("Berthold", false, "Kommentar4", 4, null, null);

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

        Map<String, Object> category = new HashMap<>();
        category.put("id", 0);
        category.put("name", "8021x");

        // Erstellen des Items
        Map<String, Object> item = new HashMap<>();
        item.put("id", 0);
        item.put("category", category);
        item.put("question", "Email Encryption");
        item.put("points", 0);
        item.put("comment", "nicht vorhanden");
        item.put("na", false);

        Map<String, Object> item2 = new HashMap<>();
        item2.put("id", 1);
        item2.put("category", category);
        item2.put("question", "whats life all about?");
        item2.put("points", 3);
        item2.put("comment", "test test test");
        item2.put("na", true);

        // Hinzufügen des Items zur Liste
        List<Map<String, Object>> items = new ArrayList<>();
        items.add(item);
        items.add(item2);

        return ResponseEntity.ok(items);


        // // Error Handling 404 - Non-existing Audit
        // if (ratingsAssigned.containsKey(auditId)) {
        //     return ResponseEntity.ok(ratingsAssigned.get(auditId));
        // } else {
        //     return ResponseEntity.notFound().build();
        // }
    }

}