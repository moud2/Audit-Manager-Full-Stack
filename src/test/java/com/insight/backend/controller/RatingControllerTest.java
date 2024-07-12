package com.insight.backend.controller;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.insight.backend.mapper.RatingMapper;
import com.insight.backend.service.audit.FindAuditService;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext
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

    // TODO: test only working periodically

    /*@Test
    public void testGetRatingsAuditFound() throws Exception {
        Audit audit = new Audit();

        Question question = new Question();
        question.setName("test");

        Rating rating1 = new Rating(false, "Comment1", 3, audit, question);
        Rating rating2 = new Rating(true, "Comment2", 5, audit, question);

        audit.setRatings(Set.of(rating1, rating2));
        when(findAuditService.findAuditById(1L)).thenReturn(Optional.of(audit));

        RatingDTO ratingDTO1 = new RatingDTO();
        ratingDTO1.setId(rating1.getId());
        ratingDTO1.setnA(rating1.getNa());
        ratingDTO1.setComment(rating1.getComment());
        ratingDTO1.setPoints(rating1.getPoints());
        ratingDTO1.setQuestion(rating1.getQuestion().getName());
        ratingDTO1.setCategory(rating1.getQuestion().getCategory());

        RatingDTO ratingDTO2 = new RatingDTO();
        ratingDTO2.setId(rating2.getId());
        ratingDTO2.setnA(rating2.getNa());
        ratingDTO2.setComment(rating2.getComment());
        ratingDTO2.setPoints(rating2.getPoints());
        ratingDTO2.setQuestion(rating2.getQuestion().getName());
        ratingDTO2.setCategory(rating2.getQuestion().getCategory());

        when(ratingMapper.convertToRatingDTOs(Arrays.asList(rating1, rating2)))
            .thenReturn(Arrays.asList(ratingDTO1, ratingDTO2));

        String expectedJson = "["
                + "{\"id\":null,\"category\":null,\"question\":\"test\",\"points\":3,\"comment\":\"Comment1\",\"nA\":false},"
                + "{\"id\":null,\"category\":null,\"question\":\"test\",\"points\":5,\"comment\":\"Comment2\",\"nA\":true}"
                + "]";

        String actualJson = mockMvc.perform(get("/api/v1/audits/1/ratings"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()
            .getResponse()
            .getContentAsString();

        System.out.println("Expected JSON: " + expectedJson);
        System.out.println("Actual JSON: " + actualJson);

        if (!expectedJson.equals(actualJson)) {
            System.err.println("Expected JSON: " + expectedJson + " but got: " + actualJson);
            throw new AssertionError("Expected JSON: " + expectedJson + " but got: " + actualJson);
        }

        verify(findAuditService, times(1)).findAuditById(1L);
        verify(ratingMapper, times(1)).convertToRatingDTOs(anyList());
    }*/

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