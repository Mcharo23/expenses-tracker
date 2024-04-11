package com.pairlearning.expensetracker.transaction.repository;

import com.pairlearning.expensetracker.transaction.dto.TransactionDto;
import com.pairlearning.expensetracker.transaction.entity.Transaction;
import com.pairlearning.expensetracker.transaction.exception.BadRequestException;
import com.pairlearning.expensetracker.transaction.exception.ResourceNotFoundException;

import java.util.List;

public interface TransactionRepository {
    List<Transaction> findAll(Integer userId, Integer categoryId);
    Transaction findById(Integer userId, Integer categoryId, Integer transactionId) throws ResourceNotFoundException;
    Integer create(Integer userid, Integer categoryId, Transaction transaction) throws BadRequestException;
    boolean update(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction) throws BadRequestException;
    boolean removeById(Integer userId, Integer categoryId, Integer transactionId) throws ResourceNotFoundException;
}
