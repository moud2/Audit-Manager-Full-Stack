package com.insight.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insight.backend.model.Rating;
import com.insight.backend.repository.RatingRepository;
import com.insight.backend.service.audit.FindAuditService;
import com.insight.backend.service.rating.FindRatingService;
import com.insight.backend.service.rating.PdfGenerator;
import com.insight.backend.service.rating.SaveRatingService;
import com.insight.backend.mapper.RatingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
public class ExportController {

    private final ObjectMapper objectMapper;
    private final FindRatingService findRatingService;
    private final SaveRatingService saveRatingService;
    private final FindAuditService findAuditService;
    private final RatingMapper ratingMapper;

    private static final String FILE_DIRECTORY = "/home/kdik96/backend/src/main/resources/static/export_pdf/";
    private final RatingRepository ratingRepository;
    private final PdfGenerator pdfGenerator;

    /**
     * Constructs a RatingController with the specified services and mapper.
     *
     * @param objectMapper the object mapper for JSON processing
     * @param findRatingService the service to find ratings
     * @param saveRatingService the service to save ratings
     * @param findAuditService the service to find audits
     * @param ratingMapper the mapper to convert Rating to RatingDTO
     */
    @Autowired
    public ExportController(ObjectMapper objectMapper, FindRatingService findRatingService, SaveRatingService saveRatingService, FindAuditService findAuditService, RatingMapper ratingMapper, RatingRepository ratingRepository, PdfGenerator pdfGenerator) {
        this.objectMapper = objectMapper;
        this.findRatingService = findRatingService;
        this.saveRatingService = saveRatingService;
        this.findAuditService = findAuditService;
        this.ratingMapper = ratingMapper;
        this.ratingRepository = ratingRepository;
        this.pdfGenerator = pdfGenerator;
    }

    /**
     * Exports Audit as PDF
     *
     * @param auditId the ID of the audit
     * @return a ResponseEntity containing a link to download PDF
     */
    @GetMapping("/api/v1/audits/{auditId}/export")
    public ResponseEntity<Long> exportAudit(@PathVariable("auditId") long auditId) {
        try {
            // Return the auditId for now as a placeholder
            return ResponseEntity.ok(auditId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Downloads a file from the export_pdf directory
     *
     * @param fileName the name of the file to download
     * @return the file as a response
     */
    @GetMapping("/static/export_pdf/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(FILE_DIRECTORY).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping(path = "/api/v1/ratings/{auditId}/export", produces = MediaType.APPLICATION_PDF_VALUE)
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
            // Temporary error handling
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}