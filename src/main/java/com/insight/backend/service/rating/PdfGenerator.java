package com.insight.backend.service.rating;

import com.insight.backend.model.Audit;
import com.insight.backend.model.Rating;
import com.insight.backend.repository.AuditRepository;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfGenerator {

    @Autowired
    private AuditRepository auditRepository;

    public ByteArrayInputStream createPdf(long auditId) {
        // Retrieve Audit
        Audit audit = auditRepository.findById(auditId)
                .orElseThrow(() -> new IllegalArgumentException("Audit not found for ID: " + auditId));

        // Convert Ratings to a List
        List<Rating> ratings = new ArrayList<>(audit.getRatings());

        // Initialize PDF Document
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Add Header
            document.add(new Paragraph("Audit Report"));
            document.add(new Paragraph("Audit ID: " + audit.getId()));
            document.add(new Paragraph("Audit Title: " + audit.getName()));
            document.add(new Paragraph("\nRatings:"));

            // Add Ratings
            for (Rating rating : ratings) {
                document.add(new Paragraph("Rating ID: " + rating.getId()));
                document.add(new Paragraph("  - Question: " + rating.getQuestion().getName()));
                document.add(new Paragraph("  - Comment: " + rating.getComment()));
                document.add(new Paragraph("  - Points: " + rating.getPoints()));
                document.add(new Paragraph("  - Is N/A: " + rating.getNa()));
                document.add(new Paragraph("\n")); // Add spacing between ratings
            }

        } catch (DocumentException e) {
            throw new RuntimeException("Error while generating PDF: " + e.getMessage(), e);
        } finally {
            document.close();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
