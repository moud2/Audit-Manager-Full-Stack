package com.insight.backend.service;

import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.service.category.FindCategoryService;
import com.insight.backend.service.category.SaveCategoryService;
import com.insight.backend.service.question.SaveQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CsvImportServiceTest {

    @Mock
    private SaveCategoryService saveCategoryService;

    @Mock
    private SaveQuestionService saveQuestionService;

    @Mock
    private FindCategoryService findCategoryService;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private CsvImportService csvImportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testImportCsv_NewCategoryAndQuestion() throws Exception {
        String csvData = "Category 1,Question 1\n";
        when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream(csvData.getBytes()));

        when(findCategoryService.findCategoryByName("Category 1")).thenReturn(Optional.empty());

        Category newCategory = new Category();
        newCategory.setId(1L);
        newCategory.setName("Category 1");
        when(saveCategoryService.saveCategory(any(Category.class))).thenReturn(newCategory);

        csvImportService.importCsv(multipartFile);

        verify(saveCategoryService, times(1)).saveCategory(any(Category.class));
        verify(saveQuestionService, times(1)).saveQuestion(any(Question.class));
    }

    @Test
    void testImportCsv_ExistingCategoryAndNewQuestion() throws Exception {
        String csvData = "Category 1,Question 2\n";
        when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream(csvData.getBytes()));

        Category existingCategory = new Category();
        existingCategory.setId(1L);
        existingCategory.setName("Category 1");
        existingCategory.setQuestions(Set.of(new Question()));

        when(findCategoryService.findCategoryByName("Category 1")).thenReturn(Optional.of(existingCategory));

        csvImportService.importCsv(multipartFile);

        verify(saveCategoryService, never()).saveCategory(any(Category.class));
        verify(saveQuestionService, times(1)).saveQuestion(any(Question.class));
    }

    @Test
    void testImportCsv_ExistingCategoryAndDuplicateQuestion() throws Exception {
        String csvData = "Category 1,Question 1\n";
        when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream(csvData.getBytes()));

        Question existingQuestion = new Question();
        existingQuestion.setName("Question 1");

        Category existingCategory = new Category();
        existingCategory.setId(1L);
        existingCategory.setName("Category 1");
        existingCategory.setQuestions(Set.of(existingQuestion));

        when(findCategoryService.findCategoryByName("Category 1")).thenReturn(Optional.of(existingCategory));

        csvImportService.importCsv(multipartFile);

        verify(saveCategoryService, never()).saveCategory(any(Category.class));
        verify(saveQuestionService, never()).saveQuestion(any(Question.class));
    }

    @Test
    void testImportCsv_InvalidCsvFormat() throws Exception {
        String csvData = "InvalidRow\n";
        when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream(csvData.getBytes()));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> csvImportService.importCsv(multipartFile));

        assertEquals("Ungültige CSV-Zeile: InvalidRow", exception.getMessage());
    }

    @Test
    void testImportCsv_EmptyFile() throws Exception {
        String csvData = "";
        when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream(csvData.getBytes()));

        csvImportService.importCsv(multipartFile);

        verify(saveCategoryService, never()).saveCategory(any(Category.class));
        verify(saveQuestionService, never()).saveQuestion(any(Question.class));
    }
}
