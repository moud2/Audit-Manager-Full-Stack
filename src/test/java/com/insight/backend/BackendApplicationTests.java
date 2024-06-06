package com.insight.backend;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import static org.hamcrest.Matchers.containsString;


import com.google.gson.Gson;
import com.insight.backend.controller.PatchController;
import com.insight.backend.model.Rating;

@WebMvcTest(PatchController.class)
class BackendApplicationTests {

	@Autowired
    private MockMvc mockMvc;

    private Gson gson;
    private Rating ratingToUpdate;

	@BeforeEach
	public void setUp() {
		gson = new Gson(); // Initialize Gson
		ratingToUpdate = new Rating();
		ratingToUpdate.setId(1);
		ratingToUpdate.setName("UpdatedName");
		ratingToUpdate.setComment("Updated comment");
		ratingToUpdate.setPoints(4);
	}
	


	@Test
	public void RatingUpdateTest()throws Exception{
		String jsonContent = gson.toJson(ratingToUpdate);
		
		 mockMvc.perform(patch("/api/v1/ratings/1")
		.contentType(MediaType.APPLICATION_JSON)
		.content(jsonContent))  // set body request to jsonContent
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("Operation Succesful")));
		
		
		mockMvc.perform(get("/api/v1/ratings"))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("UpdatedName")))
		.andExpect(content().string(containsString("Updated comment")));
		
		

	}

	@Test
    public void testUpdateRatingNotFound() throws Exception {
        String jsonContent = gson.toJson(ratingToUpdate);

        mockMvc.perform(patch("/api/v1/ratings/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Rating not found")));
    }
}
