package com.insight.backend.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.insight.backend.model.Category;
import com.insight.backend.service.category.FindCategoryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CategoryController.class)
@ExtendWith(SpringExtension.class)
public class CategoryControllerTest {

    /* Mocked Controller and Service object for testing */
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindCategoryService findCategoryService;

    private Category category1;
    private Category category2;

    /* Category objects as testdata, save in database */
    @BeforeEach
    public void setup() {
        category1 = new Category();
        category1.setId(1L);
        category1.setName("TestCategory1");

        category2 = new Category();
        category2.setId(2L);
        category2.setName("TestCategory2");

    }

    @Test
    public void testGetAllCategories() throws Exception {
        // Mock the service to return the Categories
        List<Category> categories = Arrays.asList(category1, category2);
        when(findCategoryService.findAllCategories()).thenReturn(categories);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("TestCategory1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("TestCategory2"));
    }

    @Test
    public void testGetEmpties() throws Exception {
        List<Category> categories = new ArrayList<>();
        when(findCategoryService.findAllCategories()).thenReturn(categories);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
