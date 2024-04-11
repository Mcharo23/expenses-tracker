package com.pairlearning.expensetracker.transaction.service;

import com.pairlearning.expensetracker.transaction.dto.TransactionDto;

import java.util.List;

public interface TransactionService {
    List<TransactionDto> fetchAllTransaction(Integer userId, Integer categoryId);
}
