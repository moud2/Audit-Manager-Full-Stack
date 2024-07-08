package com.insight.backend.controller;

import java.util.Optional;

import com.insight.backend.model.Audit;
import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.model.Rating;
import com.insight.backend.service.rating.FindRatingService;
import com.insight.backend.service.rating.SaveRatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RatingController.class)
@ExtendWith(SpringExtension.class)
public class RatingControllerTest {

    /* Mocked Controller and Service object for testing */
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindRatingService findCategoryService;
    @MockBean
    private SaveRatingService saveRatingService;

    private Rating rating1;
    private Rating rating2;
    private Question question1;
    private Question question2;
    private Category category1;
    private Audit audit1;

    /* Category objects as testdata, save in database */
    @BeforeEach
    public void setup() {

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

    @Test
    public void testPatchExistingRating() throws Exception {
        // Mock the service to return the Rating
        when(findCategoryService.findRatingById(1L)).thenReturn(Optional.of(rating1));
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
}