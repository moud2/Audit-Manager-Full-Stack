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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.insight.backend.model.Category;


@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTests {

    @Autowired
	private MockMvc mockMvc;
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
	public void testGetCategroies() throws Exception {
		List<Category> response = new ArrayList<>();
        response.add(new Category(0, "a"));
        response.add(new Category(1, "b"));
        response.add(new Category(2, "c"));

		mockMvc.perform(get("/api/v1/categories")).andExpect(status().isOk()).andExpect(content().string(gson.toJson(response)));

	}
}
