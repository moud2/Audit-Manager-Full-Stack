package com.insight.backend.controller;

import com.insight.backend.service.DatabaseSeederService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatabaseSeederController {
    DatabaseSeederService databaseSeederService;

    @Autowired
    public DatabaseSeederController(DatabaseSeederService databaseSeederService) {
        this.databaseSeederService = databaseSeederService;
    }

    @GetMapping("/api/v1/database/seed")
    public ResponseEntity<String> seedDatabase() {
        boolean result = databaseSeederService.seedDatabaseFromFiles();
        if(result) {
            return ResponseEntity.ok("Database seeded successfully");
        }else
        {
            return ResponseEntity.ok("Database seeding failed");
        }
    }
}
