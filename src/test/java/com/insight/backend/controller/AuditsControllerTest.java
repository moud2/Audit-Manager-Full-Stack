package com.insight.backend.controller;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.insight.backend.model.Audit;
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

@WebMvcTest(AuditsController.class)
@ExtendWith(SpringExtension.class)
public class AuditsControllerTest {

    /* Mocked Audit and Service objects for testing */
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindAuditService findAuditService;

    private Audit audit1;
    private Audit audit2;

    /* Audit objects as testdata, save in database */
    @BeforeEach
    public void setup() {
        audit1 = new Audit();
        audit2 = new Audit();
        audit1.setName("TestAudit1");
        audit2.setName("TestAudit2");
        audit1.setId((long) 1);
        audit2.setId((long) 2);
    }

    @Test
    public void testGetAllAudits() throws Exception {
        // Mock the service to return the Audits
        List<Audit> audits = Arrays.asList(audit1, audit2);
        when(findAuditService.findAllAudits()).thenReturn(audits);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/v1/audits"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("TestAudit1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("TestAudit2"));
    }
}