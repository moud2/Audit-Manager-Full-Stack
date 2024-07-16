package com.insight.backend.service;

import com.insight.backend.model.Category;
import com.insight.backend.model.Question;
import com.insight.backend.service.category.SaveCategoryService;
import com.insight.backend.service.question.SaveQuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DatabaseSeederServiceTest {

    private Method readAllLinesFromCsv;

    @Mock
    private SaveCategoryService saveCategoryService;

    @Mock
    private SaveQuestionService saveQuestionService;

    @InjectMocks
    private DatabaseSeederService databaseSeederService;

    @BeforeEach
    public void setUp() throws Exception {
        readAllLinesFromCsv = DatabaseSeederService.class.getDeclaredMethod("readAllLinesFromCsv", Path.class);
        readAllLinesFromCsv.setAccessible(true);
    }

    @Test
    public void testSeedDatabaseFromFiles() throws Exception {
        // Arrange
        Path categoryPath = Path.of(ClassLoader.getSystemResource("fixtures/dummy-categories.csv").toURI());
        Path questionPath = Path.of(ClassLoader.getSystemResource("fixtures/dummy-questions.csv").toURI());

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


    @Test
    public void testReadAllLinesFromCsv() throws Exception {
        // Arrange
        Path csvPath = Path.of(ClassLoader.getSystemResource("fixtures/dummy-categories.csv").toURI());

        // Act
        @SuppressWarnings("unchecked")
        List<String[]> lines = (List<String[]>) readAllLinesFromCsv.invoke(null, csvPath);

        // Assert
        assertNotNull(lines);
        assertFalse(lines.isEmpty());
        assertEquals(21, lines.size());
        assertArrayEquals(new String[]{"1", "Server Administration"}, lines.getFirst());
    }
}