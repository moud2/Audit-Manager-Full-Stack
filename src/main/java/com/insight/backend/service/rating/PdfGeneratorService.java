package com.insight.backend.service.rating;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.awt.Color;

import com.insight.backend.exception.AuditNotFoundException;
import com.insight.backend.exception.PdfGenerationException;
import com.insight.backend.model.Audit;
import com.insight.backend.model.Rating;
import com.insight.backend.repository.AuditRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PdfGeneratorService {

    @Autowired
    private AuditRepository auditRepository;

    public ByteArrayInputStream createPdf(long auditId) {
        // Retrieve Audit
        Audit audit = auditRepository.findById(auditId)
                .orElseThrow(() -> new AuditNotFoundException(auditId));

        List<Rating> ratings = List.copyOf(audit.getRatings());

        // Initialize PDF Document
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Set font styles
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, Color.BLUE);
            Font headerFont = new Font(Font.HELVETICA, 14, Font.BOLD, Color.BLACK);
            Font normalFont = new Font(Font.HELVETICA, 12, Font.NORMAL, Color.BLACK);

            // Add Title
            Paragraph title = new Paragraph("Audit Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            // Add Audit Info
            document.add(new Paragraph("Audit ID: " + audit.getId(), headerFont));
            document.add(new Paragraph("Audit Title: " + audit.getName(), normalFont));
            document.add(new Paragraph("Customer: " + audit.getCustomer(), normalFont));
            document.add(new Paragraph("\n"));

            // Create Table for Ratings
            PdfPTable table = new PdfPTable(5); // 5 Columns
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Table Headers
            String[] headers = {"Rating ID", "Question", "Comment", "Points", "Is N/A"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(Color.LIGHT_GRAY);
                cell.setPadding(5);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            // Add Ratings to Table
            for (Rating rating : ratings) {
                table.addCell(new Phrase(String.valueOf(rating.getId()), normalFont));
                table.addCell(new Phrase(rating.getQuestion().getName(), normalFont));
                table.addCell(new Phrase(rating.getComment(), normalFont));
                table.addCell(new Phrase(String.valueOf(rating.getPoints()), normalFont));
                table.addCell(new Phrase(String.valueOf(rating.getNa()), normalFont));
            }

            document.add(table);
        } catch (DocumentException e) {
            throw new PdfGenerationException(e.getMessage());
        } finally {
            document.close();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
