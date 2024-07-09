package com.insight.backend.controller;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyList;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.insight.backend.dto.RatingDTO;
import com.insight.backend.mapper.RatingMapper;
import com.insight.backend.model.Audit;
import com.insight.backend.model.Question;
import com.insight.backend.model.Rating;
import com.insight.backend.service.audit.FindAuditService;

class RatingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FindAuditService findAuditService;

    @Mock
    private RatingMapper ratingMapper;

    @InjectMocks
    private RatingController ratingController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ratingController).build();
    }

    /**
     * Tests the getRatings method when the audit is found.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testGetRatingsAuditFound() throws Exception {
        Audit audit = new Audit();
        Question question = new Question();
        Rating rating1 = new Rating(false, "Comment1", 3, audit, question);
        Rating rating2 = new Rating(true, "Comment2", 5, audit, question);

        audit.setRatings(Set.of(rating1, rating2));
        when(findAuditService.findAuditById(1L)).thenReturn(Optional.of(audit));

        RatingDTO ratingDTO1 = new RatingDTO();
        ratingDTO1.setComment("Comment1");
        ratingDTO1.setPoints(3);

        RatingDTO ratingDTO2 = new RatingDTO();
        ratingDTO2.setComment("Comment2");
        ratingDTO2.setPoints(5);

        when(ratingMapper.convertToRatingDTOs(Arrays.asList(rating1, rating2)))
            .thenReturn(Arrays.asList(ratingDTO1, ratingDTO2));

        mockMvc.perform(get("/api/v1/audits/1/ratings"))
            .andExpect(status().isOk());

        verify(findAuditService, times(1)).findAuditById(1L);
        verify(ratingMapper, times(1)).convertToRatingDTOs(anyList());
    }

    /**
     * Tests the getRatings method when the audit is not found.
     *
     * @throws Exception if an error occurs during the test
     */
    @Test
    public void testGetRatingsAuditNotFound() throws Exception {
        when(findAuditService.findAuditById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/audits/1/ratings"))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("{\"error\": \"audit with id 1 not found\"}"));

        verify(findAuditService, times(1)).findAuditById(1L);
        verify(ratingMapper, times(0)).convertToRatingDTOs(anyList());
    }
}