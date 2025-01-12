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

@RestController
public class ExportController {

    private final CsvGeneratorService csvGenerator;

    @Autowired
    public ExportController(CsvGeneratorService csvGenerator) {
        this.csvGenerator = csvGenerator;
    }


    @GetMapping(path = "/api/v1/database/export", produces = "text/csv")
    public ResponseEntity<InputStreamResource> databaseExport() throws IOException {
        try {
            // Generate CSV
            ByteArrayInputStream bis = csvGenerator.createCsv();

            // Set HTTP headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=DatabaseExport.csv");

            // Return the CSV as a response
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(new InputStreamResource(bis));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
