package com.insight.backend.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.insight.backend.dto.RatingDTO;
import com.insight.backend.model.Rating;

@Component
public class RatingMapper {

    /**
     * Converts a list of Rating objects to a list of RatingDTO objects.
     *
     * @param ratings the list of Rating objects to be converted
     * @return a list of RatingDTO objects
     */
    public List<RatingDTO> convertToRatingDTOs(List<Rating> ratings) {
        return ratings.stream().map(this::convertToRatingDTO).collect(Collectors.toList());
    }

    /**
     * Converts a single Rating object to a RatingDTO object.
     *
     * @param rating the Rating object to be converted
     * @return the converted RatingDTO object
     */
    private RatingDTO convertToRatingDTO(Rating rating) {
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setId(rating.getId());
        ratingDTO.setnA(rating.getNa());
        ratingDTO.setComment(rating.getComment());
        ratingDTO.setPoints(rating.getPoints());
        ratingDTO.setQuestion(rating.getQuestion().getName());
        ratingDTO.setCategory(rating.getQuestion().getCategory());
        return ratingDTO;
    }
}