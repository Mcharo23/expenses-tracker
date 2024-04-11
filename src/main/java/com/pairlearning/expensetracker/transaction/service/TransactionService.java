package com.pairlearning.expensetracker.transaction.service;

import com.pairlearning.expensetracker.transaction.dto.TransactionDto;
import com.pairlearning.expensetracker.transaction.exception.BadRequestException;
import com.pairlearning.expensetracker.transaction.exception.ResourceNotFoundException;

import java.util.List;

public interface TransactionService {
    List<TransactionDto> fetchAllTransaction(Integer userId, Integer categoryId);
    TransactionDto fetchTransactionById(Integer userId, Integer categoryId, Integer transactionId) throws ResourceNotFoundException;
    TransactionDto addTransaction(Integer userId, Integer categoryId, TransactionDto transactionDto) throws BadRequestException;
    boolean updateTransaction(Integer userId, Integer categoryId, Integer transactionId, TransactionDto transactionDto) throws BadRequestException;
    boolean removeTransaction(Integer userId, Integer categoryId, Integer transactionId) throws ResourceNotFoundException;
}
