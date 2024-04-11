package com.pairlearning.expensetracker.category.mapper;

import com.pairlearning.expensetracker.category.dto.CategoryDto;
import com.pairlearning.expensetracker.category.entity.Category;

public class CategoryMapper {
    public static CategoryDto mapToCategoryDto(Category category) {
        return new CategoryDto(
                category.getCategoryId(),
                category.getUser(),
                category.getTitle(),
                category.getDescription(),
                category.getTotalExpense()
        );
    }

    public static Category mapToCategory(CategoryDto categoryDto) {
        return new Category(
                categoryDto.getCategoryId(),
                categoryDto.getUser(),
                categoryDto.getTitle(),
                categoryDto.getDescription(),
                categoryDto.getTotalExpenses()
        );
    }
}
