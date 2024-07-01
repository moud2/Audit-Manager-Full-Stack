import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.insight.backend.model.Category;
import com.insight.backend.repository.CategoryRepository;
import com.insight.backend.service.Category.FindCategoryService;


/**
 * Test class for FindCategoryService.
 */
@ExtendWith(MockitoExtension.class)
public class FindCategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private FindCategoryService findCategoryService;

    private Category category1;

    /**
     * Set up method to initialize test data.
     */
    @BeforeEach
    void setUp() {
        category1 = new Category();
        category1.setId(1L);
        category1.setName("Category1");
    }

    /**
     * Test case for finding a category by ID when the category is found.
     */
    @Test
    void testFindCategoryById_found() {
        // Arrange: Set up mock behavior for categoryRepository
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
        Category foundCategory = findCategoryService.findCategoryById(1L);
        assertEquals(category1, foundCategory);
    }

    /**
     * Test case for finding a category by ID when the category is not found.
     */
    @Test
    void testFindCategoryById_notFound() {
        // Arrange: Set up mock behavior for categoryRepository
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        Category foundCategory = findCategoryService.findCategoryById(1L);
        assertNull(foundCategory);
    }
}
