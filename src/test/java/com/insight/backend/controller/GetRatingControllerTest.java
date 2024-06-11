package com.insight.backend.controller;

import com.insight.backend.model.RatingList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.HashMap;

@SpringBootTest
@AutoConfigureMockMvc
public class GetRatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetRatingForExistingAuditId() throws Exception {
        // Mock the data to be returned by the controller
        HashMap<Integer, RatingList> ratingsAssigned = new HashMap<>();
        RatingList ratingList = new RatingList(null); // You can initialize with actual data
        ratingsAssigned.put(1, ratingList); // Assume audit ID 1 exists
        // Simulate a GET request with a valid audit ID
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/audits/1/ratings"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
               // Add more assertions to validate the response content if needed
    }

    @Test
    public void testGetRatingForNonExistingAuditId() throws Exception {
        // Simulate a GET request with an invalid audit ID
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/audits/999/ratings"))
               .andExpect(MockMvcResultMatchers.status().isNotFound())
               .andExpect(MockMvcResultMatchers.content().string("auditID not existing"));
    }
}