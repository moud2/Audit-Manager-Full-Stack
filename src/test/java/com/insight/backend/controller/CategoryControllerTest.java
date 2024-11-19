package com.insight.backend.controller;

import java.time.LocalDateTime;
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
    private Category category3; // Deleted category

    /**
     * Set up test data before each test method execution.
     */
    @BeforeEach
    public void setup() {
        // Reset mocks to avoid interference between tests
        Mockito.reset(createCategoryService, findCategoryService);

        category1 = new Category();
        category1.setId(1L);
        category1.setName("Category1");
        category1.setDeletedAt(null); // Not deleted

        category2 = new Category();
        category2.setId(2L);
        category2.setName("Category2");
        category2.setDeletedAt(null); // Not deleted

        category3 = new Category();
        category3.setId(3L);
        category3.setName("Category3");
        category3.setDeletedAt(LocalDateTime.now()); // Deleted category
    }

    /**
     * Tests getting all available categories (not deleted).
     * 
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void testGetAllCategories() throws Exception {
        List<Category> categories = Arrays.asList(category1, category2);
        when(findCategoryService.findAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2))) // 2 non-deleted categories
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Category1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Category2"));
    }

    /**
     * Tests getting all categories including deleted ones.
     * 
     * @throws Exception if an error occurs during the request
     */
    @Test
    public void testGetCategoriesIncludingDeleted() throws Exception {
        List<Category> categories = Arrays.asList(category1, category2, category3);
        when(findCategoryService.findAllCategoriesIncludingDeleted()).thenReturn(categories);

        mockMvc.perform(get("/api/v1/categories?includeDeleted=true"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3))) // 3 categories, including deleted one
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Category1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Category2"))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].name").value("Category3"));
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
                .andExpect(status().isCreated())
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
