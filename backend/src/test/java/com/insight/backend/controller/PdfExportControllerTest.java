package com.insight.backend.controller;

import java.io.ByteArrayInputStream;

import com.insight.backend.exception.PdfGenerationException;
import com.insight.backend.service.rating.PdfGeneratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PdfExportController.class)
@ExtendWith(SpringExtension.class)
class PdfExportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PdfGeneratorService pdfGeneratorService;

    private ByteArrayInputStream samplePdfStream;

    @BeforeEach
    void setUp() {
        samplePdfStream = new ByteArrayInputStream("Test PDF Content".getBytes());
    }

    @Test
    void testAuditReportSuccess() throws Exception {
        long auditId = 1L;

        // Mock PdfGenerator to return the sample PDF stream
        when(pdfGeneratorService.createPdf(auditId)).thenReturn(samplePdfStream);

        // Perform the GET request
        mockMvc.perform(get("/api/v1/audits/{auditId}/export", auditId)
                        .contentType(MediaType.APPLICATION_PDF))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(header().string("Content-Disposition", "inline; filename=Report.pdf"));

        // Verify that PdfGenerator's method was called once
        verify(pdfGeneratorService, times(1)).createPdf(auditId);
    }

    @Test
    void testAuditReportFailure() throws Exception {
        long auditId = 1L;

        // Mock PdfGenerator to throw an exception
        when(pdfGeneratorService.createPdf(auditId)).thenThrow(new PdfGenerationException("test"));

        // Perform the GET request and validate the response
        mockMvc.perform(get("/api/v1/audits/{auditId}/export", auditId)
                        .contentType(MediaType.APPLICATION_PDF))
                        .andExpect(status().isInternalServerError())
                        .andExpect(jsonPath("$.message").value("Error while generating PDF: test"));

        // Verify that PdfGenerator's method was called once
        verify(pdfGeneratorService, times(1)).createPdf(auditId);
    }
}
