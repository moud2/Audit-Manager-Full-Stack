package com.insight.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insight.backend.model.Category;


@SpringBootTest
@AutoConfigureMockMvc

public class CategoryControllerTests {

    @Autowired
	private MockMvc mockMvc;
	
    @Autowired
    private ObjectMapper objectMapper;

    @Test
	public void testGetCategroies() throws Exception {
		List<Category> response = new ArrayList<>();
        response.add(new Category("a", Set.of()));
        response.add(new Category("b", Set.of()));
        response.add(new Category("c", Set.of()));

		String expectedJson = objectMapper.writeValueAsString(response);

		mockMvc.perform(get("/api/v1/categories")).andExpect(status().isOk()).andExpect(content().json(expectedJson));

	}
}
