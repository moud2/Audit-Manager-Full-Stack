package com.insight.backend.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insight.backend.dto.CategoryResponseDTO;
import com.insight.backend.dto.NewCategoryDTO;
import com.insight.backend.exception.DuplicateCategoryNameException;
import com.insight.backend.model.Category;
import com.insight.backend.service.category.CreateCategoryService;
import com.insight.backend.service.category.FindCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * test class for testing CategoryController
 */
@WebMvcTest(CategoryController.class)
@ExtendWith(SpringExtension.class)
public class CategoryControllerTest {

    /**
     * MockMvc instance for HTTP request mocking
     */
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateCategoryService createCategoryService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * MockBean for FindCategoryService
     */
    @MockBean
    private FindCategoryService findCategoryService;

    private Category category1;
    private Category category2;

    /**
     * Set up test data before each test method execution.
     */
    @BeforeEach
    public void setup() {
        // Reset mocks to avoid interference between tests
        Mockito.reset(createCategoryService, findCategoryService);

        category1 = new Category();
        category1.setId(1L);
        category1.setName("TestCategory1");

        category2 = new Category();
        category2.setId(2L);
        category2.setName("TestCategory2");

        // Mock existing category for duplicate test
        when(findCategoryService.findCategoryByName("Existing Category")).thenReturn(java.util.Optional.of(category1));
    }

    /**
     * Tests getting all available categories.
     *
     * @throws Exception if an error occurs during the request
     */
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

    /**
     * Test getting an empty list of categories.
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void testGetEmpties() throws Exception {
        List<Category> categories = new ArrayList<>();
        when(findCategoryService.findAllCategories()).thenReturn(categories);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    /**
     * Test creating a new category successfully.
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void testCreateCategory_Success() throws Exception {
        NewCategoryDTO newCategoryDTO = new NewCategoryDTO();
        newCategoryDTO.setName("New Category");

        CategoryResponseDTO createdCategoryResponse = new CategoryResponseDTO(1L, "New Category");

        when(createCategoryService.createCategory(anyString()))
                .thenReturn(createdCategoryResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCategoryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Category"));
    }

    /**
     * Test creating a new category with a duplicate name.
     *
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void testCreateCategory_DuplicateName() throws Exception {
        NewCategoryDTO duplicateCategoryDTO = new NewCategoryDTO();
        duplicateCategoryDTO.setName("Existing Category");

        when(createCategoryService.createCategory("Existing Category"))
                .thenThrow(new DuplicateCategoryNameException("Existing Category"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicateCategoryDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Category with the name 'Existing Category' already exists"));
    }
}