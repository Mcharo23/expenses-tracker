package com.pairlearning.expensetracker.category.service;

import com.pairlearning.expensetracker.category.dto.CategoryDto;
import com.pairlearning.expensetracker.category.entity.Category;
import com.pairlearning.expensetracker.category.exception.BadRequestException;
import com.pairlearning.expensetracker.category.exception.CategoryNotFoundException;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> fetchAllCategories(Integer userId);
    CategoryDto fetchCategoryById(Integer userId, Integer categoryId) throws CategoryNotFoundException;
    CategoryDto addCategory(Integer userId, CategoryDto categoryDto) throws BadRequestException;
    void updateCategory(Integer userId, Integer categoryID, CategoryDto categoryDto) throws BadRequestException;
    boolean removeCategoryWithAllTransactions(Integer userId, Integer categoryId) throws CategoryNotFoundException;
}
