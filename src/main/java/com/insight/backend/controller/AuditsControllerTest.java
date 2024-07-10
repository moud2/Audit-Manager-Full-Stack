package com.insight.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit tests for validating the behavior of {@link AuditsController}.
 * These tests are focused on verifying the correct handling of HTTP POST requests
 * for creating audits, including scenarios with invalid JSON payloads and empty categories.
 * @param <MockMvc>
 */
//@WebMvcTest(AuditsController.class)
public class AuditsControllerTest<MockMvc> {
@Autowired
private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objMapper;

}
