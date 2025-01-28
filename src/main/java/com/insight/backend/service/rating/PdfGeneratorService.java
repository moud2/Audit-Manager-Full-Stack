package com.insight.backend.service.rating;

import com.insight.backend.exception.AuditNotFoundException;
import com.insight.backend.exception.PdfGenerationException;
import com.insight.backend.model.Audit;
import com.insight.backend.model.Rating;
import com.insight.backend.repository.AuditRepository;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PdfGeneratorService {

    @Autowired
    private AuditRepository auditRepository;

    public ByteArrayInputStream createPdf(long auditId) {
        Audit audit = auditRepository.findById(auditId)
                .orElseThrow(() -> new AuditNotFoundException(auditId));

        Set<Rating> ratings = new HashSet<>(audit.getRatings());

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            // Add Header
            Font headerFont = new Font(Font.HELVETICA, 14, Font.BOLD);
            document.add(new Paragraph("Audit Report", headerFont));
            document.add(new Paragraph("Audit ID: " + audit.getId(), new Font(Font.HELVETICA, 10)));
            document.add(new Paragraph("Audit Title: " + audit.getName(), new Font(Font.HELVETICA, 10)));
            document.add(new Paragraph("Customer: " + audit.getCustomer(), new Font(Font.HELVETICA, 10)));
            document.add(new Paragraph("\n"));

            // Group Ratings by Category
            Map<String, List<Rating>> groupedRatings = ratings.stream()
                    .collect(Collectors.groupingBy(r -> r.getQuestion().getCategory().getName()));

            // Iterate over each category
            for (Map.Entry<String, List<Rating>> entry : groupedRatings.entrySet()) {
                // Add Category Title with Background
                String categoryName = entry.getKey();
                Font categoryFont = new Font(Font.HELVETICA, 12, Font.BOLD, Color.WHITE);
                PdfPTable categoryTable = new PdfPTable(1);
                PdfPCell categoryCell = new PdfPCell(new Phrase(categoryName, categoryFont));
                categoryCell.setBackgroundColor(new Color(196, 23, 31));
                categoryCell.setPadding(5);
                categoryCell.setBorder(Rectangle.NO_BORDER);
                categoryTable.addCell(categoryCell);
                document.add(categoryTable);

                // Add Questions for the Category
                for (Rating rating : entry.getValue()) {
                    PdfPTable questionTable = new PdfPTable(1);
                    questionTable.setWidthPercentage(100);
                    questionTable.setSpacingBefore(5);
                    PdfPCell questionCell = new PdfPCell();
                    questionCell.setPadding(10);
                    questionCell.setBorderColor(new Color(200, 200, 200));
                    questionCell.setBorderWidth(0.5f);
                    questionCell.setBorder(Rectangle.BOX);
                    questionCell.setUseBorderPadding(true);

                    // Question
                    Paragraph question = new Paragraph("Question: " +
                            (rating.getQuestion() != null ? rating.getQuestion().getName() : "Unknown question"),
                            new Font(Font.HELVETICA, 10, Font.BOLD));
                    questionCell.addElement(question);

                    // Answer
                    Paragraph answer = new Paragraph("Comment: " +
                            (rating.getComment() != null ? rating.getComment() : "No comment"),
                            new Font(Font.HELVETICA, 9));
                    questionCell.addElement(answer);

                    // Checkbox for Points
                    PdfPTable pointsTable = new PdfPTable(6);
                    pointsTable.setSpacingBefore(5);
                    pointsTable.setHorizontalAlignment(Element.ALIGN_CENTER);

                    String[] pointLabels = {"N/A", "1", "2", "3", "4", "5"};
                    for (int i = 0; i < pointLabels.length; i++) {
                        PdfPCell pointCell = new PdfPCell(new Phrase(pointLabels[i], new Font(Font.HELVETICA, 9)));
                        pointCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        pointCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        pointCell.setBorder(Rectangle.BOX);

                        // Highlight the selected point
                        if ((rating.getPoints() != null && i == rating.getPoints()) || (i == 0 && rating.getNa())) {
                            pointCell.setBackgroundColor(new Color(224, 224, 224));
                        }

                        pointsTable.addCell(pointCell);
                    }
                    questionCell.addElement(pointsTable);

                    questionTable.addCell(questionCell);
                    document.add(questionTable);
                }

                document.add(new Paragraph("\n"));
            }

        } catch (DocumentException e) {
            throw new PdfGenerationException(e.getMessage());
        } finally {
            document.close();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
