package com.insight.backend.controller;

import com.insight.backend.exception.DatabaseSeedingException;
import com.insight.backend.service.DatabaseSeederService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatabaseSeederController {
    DatabaseSeederService databaseSeederService;

    public DatabaseSeederController(DatabaseSeederService databaseSeederService) {
        this.databaseSeederService = databaseSeederService;
    }

    /**
     * Seeds the database with data from the seed files.
     *
     * @return a ResponseEntity containing a string confirming the seeding
     */
    @GetMapping("/api/v1/database/seed")
    public ResponseEntity<String> seedDatabase() {
        try {
            databaseSeederService.seedDatabaseFromFiles();
        } catch (Exception e) {
            throw new DatabaseSeedingException(e.getMessage());
        }
        return ResponseEntity.ok("Database seeded successfully");
    }
}
