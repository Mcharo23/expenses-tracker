package com.pairlearning.expensetracker.category.dto;

import com.pairlearning.expensetracker.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryDto {

    private int categoryId;

    private User user;

    private String title;

    private String description;

    private Double totalExpenses;


}
