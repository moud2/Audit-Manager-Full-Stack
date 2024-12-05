package com.insight.backend.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;
import com.insight.backend.model.Category;
import com.insight.backend.repository.CategoryRepository;
import com.insight.backend.specifications.CategorySpecifications;

@Service
public class FindCategoryService {

    private final CategoryRepository categoryRepository;

    public FindCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    /**
     * Finds a category based on the given id.
     *
     * @param id the id of category to be found.
     * @return an Optional object that includes the category if found, or empty if not found.
     */
    public Optional <Category> findCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    /**
     * Retrieves all non-deleted categories using Specification.
     *
     * @return a list of categories that have not been marked as deleted.
     */
    public List<Category> findAllCategories() {
        // Use Specification to filter out deleted categories (deletedAt is null)
        Specification<Category> spec = CategorySpecifications.isNotDeleted();
        return categoryRepository.findAll(spec);
    }

    /**
     * Finds a category based on the given name.
     *
     * @param name the name of category to be found.
     * @return an Optional object that includes the category if found, or empty if not found.
     */
    public Optional<Category> findCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }
}
