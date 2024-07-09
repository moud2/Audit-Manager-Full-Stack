package com.insight.backend.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.insight.backend.dto.NewAuditDTO;
import com.insight.backend.service.audit.CreateAuditService;

/**
 * Test class for CreateAuditService.
 */
public class CreateAuditServiceTest {

    private CreateAuditService createAuditService;

    /**
     * Sets up the test environment by initializing the CreateAuditService instance.
     */
    @BeforeEach
    public void setUp() {
        createAuditService = new CreateAuditService();
    }

    /**
     * Tests the createAudit method with valid category IDs.
     */
    @Test
    public void testCreateAuditWithValidCategories() {
        // Create a NewAuditDTO with valid category IDs
        NewAuditDTO newAuditDTO = new NewAuditDTO();
        newAuditDTO.setName("Test Audit");
        newAuditDTO.setCategories(Arrays.asList(1L, 2L, 3L)); // Valid IDs

        // Call createAudit and verify the result
        NewAuditDTO result = createAuditService.createAudit(newAuditDTO);
        assertNotNull(result);
        assertEquals("Test Audit", result.getName());
        assertEquals(Arrays.asList(1L, 2L, 3L), result.getCategories());
    }

    /**
     * Tests the createAudit method with an invalid category ID.
     */
    @Test
    public void testCreateAuditWithInvalidCategory() {
        // Create a NewAuditDTO with an invalid category ID
        NewAuditDTO newAuditDTO = new NewAuditDTO();
        newAuditDTO.setName("Test Audit");
        newAuditDTO.setCategories(Arrays.asList(1L, 99L)); // 99L is an invalid ID

        // Call createAudit and verify the result
        NewAuditDTO result = createAuditService.createAudit(newAuditDTO);
        assertNull(result);
    }

    /**
     * Tests the createAudit method with no category IDs.
     */
    @Test
    public void testCreateAuditWithEmptyCategories() {
        // Create a NewAuditDTO with no category IDs
        NewAuditDTO newAuditDTO = new NewAuditDTO();
        newAuditDTO.setName("Test Audit");
        newAuditDTO.setCategories(Arrays.asList()); // No category IDs

        // Call createAudit and verify the result
        NewAuditDTO result = createAuditService.createAudit(newAuditDTO);
        assertNull(result);
    }
}
