package com.insight.backend.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.insight.backend.exception.GlobalExceptionHandler;
import com.insight.backend.service.rating.CsvGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling export-related operations.
 * Provides an endpoint for exporting the database as a CSV file.
 */
@RestController
public class CsvExportController {

    private final CsvGeneratorService csvGenerator;

    /**
     * Constructs an ExportController with the specified CsvGeneratorService.
     *
     * @param csvGenerator the service used to generate CSV exports
     */
    @Autowired
    public CsvExportController(CsvGeneratorService csvGenerator) {
        this.csvGenerator = csvGenerator;
    }

    /**
     * Exports the database as a CSV file.
     *
     * <p>The generated CSV contains all categories and their associated questions,
     * excluding deleted entries and avoiding duplicates.</p>
     *
     * @return a {@link ResponseEntity} containing the CSV data as an attachment
     */
    @GetMapping(path = "/api/v1/database/export", produces = "text/csv")
    public ResponseEntity<InputStreamResource> databaseExport() {
        ByteArrayInputStream bis = csvGenerator.createCsv();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=DatabaseExport.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(bis));
    }
}