package com.insight.backend.service.Category;

import java.util.List;

import com.insight.backend.repository.CategoryRepository;
import com.insight.backend.model.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class FindCategoryService {

    
    private final CategoryRepository categoryRepository;

    public FindCategoryService(CategoryRepository c) {
        this.categoryRepository = c;
    }

    /*
     * returns all categories
     * @return list of all categories
     */
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }
}