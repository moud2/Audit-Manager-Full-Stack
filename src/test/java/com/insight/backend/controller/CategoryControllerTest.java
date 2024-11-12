package com.insight.backend.controller;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.insight.backend.model.Category;
import com.insight.backend.service.category.FindCategoryService;

/**
 * Test class for testing CategoryController.
 */
@WebMvcTest(CategoryController.class)
@ExtendWith(SpringExtension.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindCategoryService findCategoryService;

    private Category category1;
    private Category category2;

    /**
     * Set up test data before each test method execution.
     */
    @BeforeEach
    public void setup() {
        category1 = new Category();
        category1.setId(1L);
        category1.setName("TestCategory1");
        category1.setDeletedAt(null); // Non-deleted category

        category2 = new Category();
        category2.setId(2L);
        category2.setName("TestCategory2");
        category2.setDeletedAt(null); // Non-deleted category
    }

    /**
     * Tests getting all available non-deleted categories.
     * 
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void testGetAllCategories() throws Exception {
        // Mock the service to return the non-deleted categories
        List<Category> categories = Arrays.asList(category1, category2);
        when(findCategoryService.findAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2))) // 2 non-deleted categories
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("TestCategory1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("TestCategory2"));
    }
}
