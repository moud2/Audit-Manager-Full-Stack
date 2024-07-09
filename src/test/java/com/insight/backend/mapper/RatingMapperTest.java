package com.insight.backend.mapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.insight.backend.dto.RatingDTO;
import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.model.Rating;

public class RatingMapperTest {

    private RatingMapper ratingMapper;

    @BeforeEach
    public void setUp() {
        ratingMapper = new RatingMapper();
    }

    /**
     * Tests the convertToRatingDTOs method.
     * Verifies that a list of Rating objects is correctly converted to a list of RatingDTO objects.
     */
    @Test
    public void testConvertToRatingDTOs() {
        Rating rating1 = new Rating();
        Question question1 = new Question();
        Category category1 = new Category();
        category1.setName("Category1");
        question1.setName("Question1");
        question1.setCategory(category1);
        rating1.setId(1L);
        rating1.setNa(true);
        rating1.setComment("Comment1");
        rating1.setPoints(1);
        rating1.setQuestion(question1);

        Rating rating2 = new Rating();
        Question question2 = new Question();
        Category category2 = new Category();
        category2.setName("Category2");
        question2.setName("Question2");
        question2.setCategory(category2);
        rating2.setId(2L);
        rating2.setNa(false);
        rating2.setComment("Comment2");
        rating2.setPoints(2);
        rating2.setQuestion(question2);

        List<Rating> ratings = Arrays.asList(rating1, rating2);

        List<RatingDTO> ratingDTOs = ratingMapper.convertToRatingDTOs(ratings);

        assertNotNull(ratingDTOs);
        assertEquals(2, ratingDTOs.size());

        RatingDTO ratingDTO1 = ratingDTOs.get(0);
        assertEquals(1L, ratingDTO1.getId());
        assertEquals(true, ratingDTO1.getnA());
        assertEquals("Comment1", ratingDTO1.getComment());
        assertEquals(1, ratingDTO1.getPoints());
        assertEquals("Question1", ratingDTO1.getQuestion());
        assertEquals("Category1", ratingDTO1.getCategory().getName());

        RatingDTO ratingDTO2 = ratingDTOs.get(1);
        assertEquals(2L, ratingDTO2.getId());
        assertEquals(false, ratingDTO2.getnA());
        assertEquals("Comment2", ratingDTO2.getComment());
        assertEquals(2, ratingDTO2.getPoints());
        assertEquals("Question2", ratingDTO2.getQuestion());
        assertEquals("Category2", ratingDTO2.getCategory().getName());
    }
}