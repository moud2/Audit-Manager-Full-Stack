package com.insight.backend.controller;

import com.insight.backend.service.rating.CsvGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Controller for handling export-related operations.
 * Provides an endpoint for exporting the database as a CSV file.
 */
@RestController
public class ExportController {

    private final CsvGeneratorService csvGenerator;

    /**
     * Constructs an ExportController with the specified CsvGeneratorService.
     *
     * @param csvGenerator the service used to generate CSV exports
     */
    @Autowired
    public ExportController(CsvGeneratorService csvGenerator) {
        this.csvGenerator = csvGenerator;
    }

    /**
     * Exports the database as a CSV file.
     *
     * <p>The generated CSV contains all categories and their associated questions,
     * excluding deleted entries and avoiding duplicates.</p>
     *
     * @return a {@link ResponseEntity} containing the CSV data as an attachment
     * @throws IOException if an error occurs during CSV generation
     */
    @GetMapping(path = "/api/v1/database/export", produces = "text/csv")
    public ResponseEntity<InputStreamResource> databaseExport() throws IOException {
        try {
            ByteArrayInputStream bis = csvGenerator.createCsv();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=DatabaseExport.csv");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(new InputStreamResource(bis));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}