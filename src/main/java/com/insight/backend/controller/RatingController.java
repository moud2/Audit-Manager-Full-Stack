package com.insight.backend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.insight.backend.exception.RatingNotFoundException;
import com.insight.backend.model.Rating;
import com.insight.backend.model.nestedRatings.RatingList;
import com.insight.backend.service.rating.FindRatingService;
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
     * @param id    the ID of the rating to update
     * @param patch the JSON patch containing the changes to apply
     * @return a ResponseEntity containing the updated rating in JSON format or an error message if the rating ID does not exist
     */
    @PatchMapping("/api/v1/ratings/{id}")
    public ResponseEntity<Rating> updateRating(@PathVariable("id") long id, @RequestBody JsonPatch patch) {
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
        Rating rating1 = new Rating(false, "KOmmentar", 0, null, null);
        Rating rating2 = new Rating(false, "Kommentar2", null, null, null);
        Rating rating3 = new Rating(false, "Kommentar3", 1, null, null);
        Rating rating4 = new Rating(false, "Kommentar4", 4, null, null);

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

        Map<String, Object> category1 = new HashMap<>();
        category1.put("id", 1L);
        category1.put("name", "Server Administration");

        Map<String, Object> category2 = new HashMap<>();
        category2.put("id", 2L);
        category2.put("name", "Firewall");

        Map<String, Object> category3 = new HashMap<>();
        category3.put("id", 3L);
        category3.put("name", "Netzwerk");

        Map<String, Object> category4 = new HashMap<>();
        category4.put("id", 4L);
        category4.put("name", "Antivirus");

        Map<String, Object> category5 = new HashMap<>();
        category5.put("id", 5L);
        category5.put("name", "VPN");

        Map<String, Object> category6 = new HashMap<>();
        category6.put("id", 6L);
        category6.put("name", "Monitoring");

        Map<String, Object> category7 = new HashMap<>();
        category7.put("id", 7L);
        category7.put("name", "Email");

        Map<String, Object> category8 = new HashMap<>();
        category8.put("id", 8L);
        category8.put("name", "Secure Browsing");

        Map<String, Object> category9 = new HashMap<>();
        category9.put("id", 9L);
        category9.put("name", "Client");

        Map<String, Object> category10 = new HashMap<>();
        category10.put("id", 10L);
        category10.put("name", "Patch-Management");

        Map<String, Object> category11 = new HashMap<>();
        category11.put("id", 11L);
        category11.put("name", "Schwachstellen-Management");

        Map<String, Object> category12 = new HashMap<>();
        category12.put("id", 12L);
        category12.put("name", "Verschlüsselung");

        Map<String, Object> category13 = new HashMap<>();
        category13.put("id", 13L);
        category13.put("name", "Zertifikate und PKI");

        Map<String, Object> category14 = new HashMap<>();
        category14.put("id", 14L);
        category14.put("name", "Mobile Device Management");

        Map<String, Object> category15 = new HashMap<>();
        category15.put("id", 15L);
        category15.put("name", "Backup");

        Map<String, Object> category16 = new HashMap<>();
        category16.put("id", 16L);
        category16.put("name", "Privilege Access Management (PAM)");

        Map<String, Object> category17 = new HashMap<>();
        category17.put("id", 17L);
        category17.put("name", "Identity, Passwörter und Secure Logon");

        Map<String, Object> category18 = new HashMap<>();
        category18.put("id", 18L);
        category18.put("name", "Nutzung von Clouddiensten");

        Map<String, Object> category19 = new HashMap<>();
        category19.put("id", 19L);
        category19.put("name", "Konzepte und Richtlinien");

        Map<String, Object> category20 = new HashMap<>();
        category20.put("id", 20L);
        category20.put("name", "IAM");

        Map<String, Object> category21 = new HashMap<>();
        category21.put("id", 21L);
        category21.put("name", "Digitale Signatur");


        // Erstellen des Items
        Map<String, Object> item = new HashMap<>();
        item.put("id", 0);
        item.put("category", category1);
        item.put("question", "Werden verschiedene Administrationsrollen mit Rechten nach dem Least-Privilege-Prinzip für unterschiedliche Administrationsaufgaben (z. B. Softwareupdates, Konfiguration, Backup) eingesetzt?");
        item.put("points", null);
        item.put("comment", "nciht vorhanden");
        item.put("na", false);

        Map<String, Object> item2 = new HashMap<>();
        item2.put("id", 1);
        item2.put("category", category1);
        item2.put("question", "Existiert ein geregelter Prozess zum zeitnahen Einspielen von Sicherheitsupdates der Server? Werden kritische Updates unverzüglich eingespielt?");
        item2.put("points", 5);
        item2.put("comment", "Ja, existiert");
        item2.put("na", false);

        Map<String, Object> item3 = new HashMap<>();
        item3.put("id", 2);
        item3.put("category", category5);
        item3.put("question", "Erfolgt der externe Zugriff auf das lokale Firmennetzwerk über getunnelte und durch Verschlüsselung gesicherte VPN-Verbindungen?");
        item3.put("points", null);
        item3.put("comment", "");
        item3.put("na", true);

        Map<String, Object> item4 = new HashMap<>();
        item4.put("id", 3);
        item4.put("category", category5);
        item4.put("question", "Existiert eine fein granulare Berechtigungs- und Kommunikatiosmatrix für die VPN-Appliance?");
        item4.put("points", null);
        item4.put("comment", "");
        item4.put("na", true);

        Map<String, Object> item5 = new HashMap<>();
        item5.put("id", 4);
        item5.put("category", category8);
        item5.put("question", "Wird eine SSL Inspection durchgeführt?");
        item5.put("points", 2);
        item5.put("comment", "ja aber veraltete cipher suites verwendet. Bitte verbessern");
        item5.put("na", false);

        Map<String, Object> item6 = new HashMap<>();
        item6.put("id", 5);
        item6.put("category", category8);
        item6.put("question", "Wird ein Web-Proxy eingesetzt, über den alle HTTP(S)-Verbindungen gehen müssen?");
        item6.put("points", 0);
        item6.put("comment", "Dringendes Verbesserungspotenzial");
        item6.put("na", false);

        Map<String, Object> item7 = new HashMap<>();
        item7.put("id", 6);
        item7.put("category", category8);
        item7.put("question", "Werden HTTP(S)-Verbindungen abseits des Web-Proxies blockiert? (Ausnahmeregeln vermeiden)");
        item7.put("points", null);
        item7.put("comment", "Dringendes Verbesserungspotenzial");
        item7.put("na", true);

        // Hinzufügen des Items zur Liste
        List<Map<String, Object>> items1 = new ArrayList<>();
        items1.add(item);
        items1.add(item2);
        items1.add(item3);
        items1.add(item4);
        items1.add(item5);
        items1.add(item6);
        items1.add(item7);










        Map<String, Object> item8 = new HashMap<>();
        item8.put("id", 7);
        item8.put("category", category10);
        item8.put("question", "Gibt es geregelte Wartungszeiträume für das Einspielen von Patches?");
        item8.put("points", null);
        item8.put("comment", "");
        item8.put("na", true);

        Map<String, Object> item9 = new HashMap<>();
        item9.put("id", 8);
        item9.put("category", category10);
        item9.put("question", "Besteht eine Übersicht über alle in der Infrastruktur installierten Software-Anwendungen?");
        item9.put("points", 3);
        item9.put("comment", "Könnte noch etwas verbessert werden. Wurde letztes mal schon mehr oder weniger ignoriert.");
        item9.put("na", false);

        Map<String, Object> item10 = new HashMap<>();
        item10.put("id", 9);
        item10.put("category", category10);
        item10.put("question", "Ist ein Patch-Management Prozess etabliert?");
        item10.put("points", 2);
        item10.put("comment", "ja aber das kann auch noch ein wenig verbessern");
        item10.put("na", false);

        Map<String, Object> item11 = new HashMap<>();
        item11.put("id", 10);
        item11.put("category", category19);
        item11.put("question", "Gibt es eine Übersicht über alle Richtlinien, Dokumentationen, etc. ?");
        item11.put("points", 5);
        item11.put("comment", "alles top!");
        item11.put("na", false);

        Map<String, Object> item12 = new HashMap<>();
        item12.put("id", 11);
        item12.put("category", category19);
        item12.put("question", "Sind Verantwortlichkeiten in Bezug auf die Informationssicherheit verteilt? Gibt es eine Sicherheitsorganisation?");
        item12.put("points", null);
        item12.put("comment", "Trifft leider nicht zu dieser Punkt");
        item12.put("na", true);



        List<Map<String, Object>> items2 = new ArrayList<>();
        items2.add(item8);
        items2.add(item9);
        items2.add(item10);
        items2.add(item11);
        items2.add(item12);

        if (auditId == 1) {
            return ResponseEntity.ok(items1);
        } else if (auditId == 2) {
            return ResponseEntity.ok(items2);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }



        // // Error Handling 404 - Non-existing Audit
        // if (ratingsAssigned.containsKey(auditId)) {
        //     return ResponseEntity.ok(ratingsAssigned.get(auditId));
        // } else {
        //     return ResponseEntity.notFound().build();
        // }
    }

}