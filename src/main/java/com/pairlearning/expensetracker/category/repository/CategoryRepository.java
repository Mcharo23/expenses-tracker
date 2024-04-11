package com.pairlearning.expensetracker.category.repository;

import com.pairlearning.expensetracker.category.dto.CategoryDto;
import com.pairlearning.expensetracker.category.entity.Category;
import com.pairlearning.expensetracker.category.exception.BadRequestException;
import com.pairlearning.expensetracker.category.exception.CategoryNotFoundException;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll(Integer userId);
    Category findById(Integer userId, Integer categoryId) throws CategoryNotFoundException;
    Integer create(Integer userId, Category category) throws BadRequestException;
    void update(Integer userId, Integer categoryID, Category category) throws BadRequestException;
    void removeById(Integer userId, Integer categoryId);
}
