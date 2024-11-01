package com.insight.backend.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.insight.backend.model.Audit;
import com.insight.backend.service.audit.CreateAuditService;
import com.insight.backend.service.audit.FindAuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * test class for testing AuditsController
 */
@WebMvcTest(AuditsController.class)
@ExtendWith(SpringExtension.class)
public class AuditControllerTestHttpGet {

    /**
     * MockMvc instance for HTTP request mocking
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * MockBean for FindAutitService
     */
    @MockBean
    private FindAuditService findAuditService;

    /**
     * MockBean for CreateAuditService
     */
    @MockBean
    private CreateAuditService createAuditService;

    private Audit audit1;
    private Audit audit2;

    /**
     * Set up test data before each test method execution.
     */
    @BeforeEach
    public void setup() {
        audit1 = new Audit();
        audit2 = new Audit();
        audit1.setName("TestAudit1");
        audit2.setName("TestAudit2");
        audit1.setId(1L);
        audit2.setId(2L);
        audit1.setCustomer("TestCustomer1");
        audit2.setCustomer("TestCustomer2");
    }

    /**
     * Tests getting all available audits.
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void testGetAllAudits() throws Exception {
        // Mock the service to return the Audits
        List<Audit> audits = Arrays.asList(audit1, audit2);
        when(findAuditService.findAllAudits("", "asc", "id")).thenReturn(audits);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/v1/audits"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("TestAudit1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("TestAudit2"))
                .andExpect(jsonPath("$[0].customer").value("TestCustomer1"))
                .andExpect(jsonPath("$[1].customer").value("TestCustomer2"));
    }

    /**
     * Test gettin an empty list of audits.
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void testGetEmpties() throws Exception {
        List<Audit> audits = new ArrayList<>();
        when(findAuditService.findAllAudits()).thenReturn(audits);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/v1/audits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

}