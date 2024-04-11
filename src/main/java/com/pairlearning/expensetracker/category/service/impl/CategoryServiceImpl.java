package com.pairlearning.expensetracker.category.service.impl;

import com.pairlearning.expensetracker.category.dto.CategoryDto;
import com.pairlearning.expensetracker.category.entity.Category;
import com.pairlearning.expensetracker.category.exception.BadRequestException;
import com.pairlearning.expensetracker.category.exception.CategoryNotFoundException;
import com.pairlearning.expensetracker.category.mapper.CategoryMapper;
import com.pairlearning.expensetracker.category.repository.CategoryRepository;
import com.pairlearning.expensetracker.category.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public List<CategoryDto> fetchAllCategories(Integer userId) {
        List<Category> categoryDtoList = categoryRepository.findAll(userId);
        return categoryDtoList.stream().map(CategoryMapper::mapToCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto fetchCategoryById(Integer userId, Integer categoryId) throws CategoryNotFoundException {

        return CategoryMapper.mapToCategoryDto(categoryRepository.findById(userId, categoryId));
    }

    @Override
    public CategoryDto addCategory(Integer userId, CategoryDto categoryDto) throws BadRequestException {
        int categoryId = categoryRepository.create(userId, CategoryMapper.mapToCategory(categoryDto));

        return CategoryMapper.mapToCategoryDto(categoryRepository.findById(userId, categoryId));
    }

    @Override
    public void updateCategory(Integer userId, Integer categoryID, CategoryDto categoryDto) throws BadRequestException {
        categoryRepository.update(userId, categoryID, CategoryMapper.mapToCategory(categoryDto));
    }

    @Override
    public boolean removeCategoryWithAllTransactions(Integer userId, Integer categoryId) throws CategoryNotFoundException {
        return categoryRepository.removeById(userId, categoryId);
    }
}
