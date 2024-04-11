package com.pairlearning.expensetracker.transaction.dto;

import com.pairlearning.expensetracker.category.entity.Category;
import com.pairlearning.expensetracker.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionDto {

    private int transactionId;

    private Category category;

    private User user;

    private Double amount;

    private String note;

    private Long transactionDate;
}
