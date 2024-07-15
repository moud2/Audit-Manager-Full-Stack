package com.insight.backend.controller;

import java.util.Optional;

import com.insight.backend.mapper.RatingMapper;
import com.insight.backend.model.Audit;
import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.model.Rating;
import com.insight.backend.service.audit.FindAuditService;
import com.insight.backend.service.rating.FindRatingService;
import com.insight.backend.service.rating.SaveRatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RatingController.class)
@ExtendWith(SpringExtension.class)
class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindRatingService findRatingService;
    @MockBean
    private SaveRatingService saveRatingService;
    @MockBean
    private FindAuditService findAuditService;
    @MockBean
    private RatingMapper ratingMapper;

    @BeforeEach
    public void setUp() {
        category1 = new Category();
        category1.setId(1L);
        category1.setName("TestCategory1");
        question1 = new Question();
        question1.setId(1L);
        question1.setName("TestQuestion1");
        question1.setCategory(category1);
        question2 = new Question();
        question2.setId(2L);
        question2.setName("TestQuestion2");
        question2.setCategory(category1);

        audit1 = new Audit();
        audit1.setId(1L);
        audit1.setName("TestAudit1");

        rating1 = new Rating();
        rating1.setId(1L);
        rating1.setComment("This is the first comment");
        rating1.setPoints(5);
        rating1.setQuestion(question1);
        rating1.setAudit(audit1);

        rating2 = new Rating();
        rating2.setId(2L);
        rating2.setComment("This is the second comment");
        rating2.setPoints(4);
        rating2.setQuestion(question2);
        rating2.setAudit(audit1);
    }

    private Rating rating1;
    private Rating rating2;
    private Question question1;
    private Question question2;
    private Category category1;
    private Audit audit1;

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

    @Test
    public void testPatchExistingRating() throws Exception {
        // Mock the service to return the Rating
        when(findRatingService.findRatingById(1L)).thenReturn(Optional.of(rating1));
        Rating patchedRating = new Rating();
        patchedRating.setId(1L);
        patchedRating.setComment("This is not the first comment");
        patchedRating.setPoints(5);
        patchedRating.setQuestion(question1);
        patchedRating.setAudit(audit1);

        when(saveRatingService.saveRating(any())).thenReturn(patchedRating);

        // Perform the Patch Request with java spring boot patch body
        mockMvc.perform(patch("/api/v1/ratings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"op\":\"replace\",\"path\":\"/comment\",\"value\":\"This is not the first comment\"}]"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.comment").value("This is not the first comment"))
                .andExpect(jsonPath("$.points").value(5));

        // save should be called once
        verify(saveRatingService, times(1)).saveRating(any());
    }

    @Test
    public void testPatchNotExistingRating() throws Exception {
        // Mock the service to return the Rating
        when(findRatingService.findRatingById(1L)).thenReturn(Optional.empty());

        // Perform the Patch Request with java spring boot patch body
        mockMvc.perform(patch("/api/v1/ratings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"op\":\"replace\",\"path\":\"/comment\",\"value\":\"This is not the first comment\"}]"))
                .andExpect(status().isNotFound());

        // save should not be called
        verify(saveRatingService, never()).saveRating(any());
    }
}