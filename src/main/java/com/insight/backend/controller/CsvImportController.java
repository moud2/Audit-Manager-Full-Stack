package com.insight.backend.controller;

import com.insight.backend.service.CsvImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/database")
public class CsvImportController {

    private final CsvImportService csvImportService;

    public CsvImportController(CsvImportService csvImportService) {
        this.csvImportService = csvImportService;
    }

    /**
     * Imports categories and questions from a CSV file.
     *
     * @param file the CSV file to import
     * @return a {@link ResponseEntity} indicating the result of the operation
     */
    @PostMapping("/import")
    public ResponseEntity<String> importDatabase(@RequestParam("file") MultipartFile file) {
        try {
            csvImportService.importCsv(file);
            return ResponseEntity.ok("Daten erfolgreich importiert.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Fehler beim Import: " + e.getMessage());
        }
    }
}