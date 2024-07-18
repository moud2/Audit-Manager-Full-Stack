package com.insight.backend.service;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.io.Reader;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.service.category.SaveCategoryService;
import com.insight.backend.service.question.SaveQuestionService;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for DatabaseSeederService.
 */
@ExtendWith(MockitoExtension.class)
public class DatabaseSeederServiceTest {

    private Method readAllLinesFromCsv;

    @Mock
    private SaveCategoryService saveCategoryService;

    @Mock
    private SaveQuestionService saveQuestionService;

    @InjectMocks
    private DatabaseSeederService databaseSeederService;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() throws Exception {
        readAllLinesFromCsv = DatabaseSeederService.class.getDeclaredMethod("readAllLinesFromCsv", String.class);
        readAllLinesFromCsv.setAccessible(true);
    }

    /**
     * Tests the seedDatabaseFromFiles method.
     */
    @Test
    public void testSeedDatabaseFromFiles() throws Exception {
        // Arrange
        String categoryPath = "fixtures/dummy-categories.csv";
        String questionPath = "fixtures/dummy-questions.csv";


        // Mocking readAllLinesFromCsv method using reflection
        List<String[]> categories = (List<String[]>) readAllLinesFromCsv.invoke(databaseSeederService, categoryPath);
        List<String[]> questions = (List<String[]>) readAllLinesFromCsv.invoke(databaseSeederService, questionPath);

        // Mocking save methods
        when(saveCategoryService.saveCategory(any(Category.class))).thenAnswer(invocation -> {
            Category category = invocation.getArgument(0);
            category.setId((long) (Math.random() * 1000));
            return category;
        });

        when(saveQuestionService.saveQuestion(any(Question.class))).thenAnswer(invocation -> {
            Question question = invocation.getArgument(0);
            question.setId((long) (Math.random() * 1000));
            return question;
        });

        // Act
        databaseSeederService.seedDatabaseFromFiles();

        // Assert
        verify(saveCategoryService, times(categories.size())).saveCategory(any(Category.class));
        verify(saveQuestionService, times(questions.size())).saveQuestion(any(Question.class));
    }

    /**
     * Tests the readAllLinesFromCsv method.
     */
    @Test
    public void testReadAllLinesFromCsv() throws Exception {
        // Arrange
        //Path csvPath = Path.of(ClassLoader.getSystemResource("fixtures/dummy-categories.csv").toURI());
        String csvPath = "fixtures/dummy-categories.csv";

        // Act
        List<String[]> lines = (List<String[]>) readAllLinesFromCsv.invoke(databaseSeederService, csvPath);

        // Get the expected number of lines using the private helper method
        int expectedNumberOfLines = getExpectedNumberOfLines(csvPath);

        // Assert
        assertNotNull(lines);
        assertFalse(lines.isEmpty());
        assertEquals(expectedNumberOfLines, lines.size());
        assertArrayEquals(new String[]{"1", "Server Administration"}, lines.getFirst());
    }

    /**
     * Private helper method to determine the expected number of lines in the CSV file.
     */
    private int getExpectedNumberOfLines(String filePath) throws Exception {  
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(filePath)) {
            Reader reader = new BufferedReader(new InputStreamReader(in));

            try (CSVReader csvReader = new CSVReader(reader)) {
                return csvReader.readAll().size();
            }
        }
    }

}