package com.insight.backend.controller;

import com.insight.backend.service.rating.CsvGeneratorService;
import com.insight.backend.service.rating.PdfGeneratorService;
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

    private final PdfGeneratorService pdfGenerator;
    private final CsvGeneratorService csvGenerator;

    @Autowired
    public ExportController(PdfGeneratorService pdfGenerator, CsvGeneratorService csvGenerator) {
        this.pdfGenerator = pdfGenerator;
        this.csvGenerator = csvGenerator;
    }

    @GetMapping(path = "/api/v1/audits/{auditId}/export", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> auditReport(@PathVariable("auditId") long auditId) throws IOException {
        try {
            // Generate PDF
            ByteArrayInputStream bis = pdfGenerator.createPdf(auditId);

            // Set HTTP headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=Report.pdf");

            // Return the PDF as a response
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
