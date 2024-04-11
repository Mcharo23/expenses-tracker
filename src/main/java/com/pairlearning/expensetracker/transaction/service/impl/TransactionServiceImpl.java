package com.pairlearning.expensetracker.transaction.service.impl;

import com.pairlearning.expensetracker.transaction.dto.TransactionDto;
import com.pairlearning.expensetracker.transaction.entity.Transaction;
import com.pairlearning.expensetracker.transaction.exception.BadRequestException;
import com.pairlearning.expensetracker.transaction.exception.ResourceNotFoundException;
import com.pairlearning.expensetracker.transaction.mapper.TransactionMapper;
import com.pairlearning.expensetracker.transaction.repository.TransactionRepository;
import com.pairlearning.expensetracker.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<TransactionDto> fetchAllTransaction(Integer userId, Integer categoryId) {
        List<Transaction> transactionDtoList = transactionRepository.findAll(userId, categoryId);
        return transactionDtoList.stream().map(TransactionMapper::mapToTransactionDto).collect(Collectors.toList());
    }

    @Override
    public TransactionDto fetchTransactionById(Integer userId, Integer categoryId, Integer transactionId) throws ResourceNotFoundException {
        Transaction transaction = transactionRepository.findById(userId, categoryId, transactionId);
        return TransactionMapper.mapToTransactionDto(transaction);
    }

    @Override
    public TransactionDto addTransaction(Integer userId, Integer categoryId, TransactionDto transactionDto) throws BadRequestException {
        int transactionId = transactionRepository.create(userId, categoryId, TransactionMapper.mapToTransaction((transactionDto)));
        return TransactionMapper.mapToTransactionDto(transactionRepository.findById(userId, categoryId, transactionId));
    }

    @Override
    public boolean updateTransaction(Integer userId, Integer categoryId, Integer transactionId, TransactionDto transactionDto) throws BadRequestException {

        return transactionRepository.update(userId, categoryId, transactionId, TransactionMapper.mapToTransaction(transactionDto));
    }

    @Override
    public boolean removeTransaction(Integer userId, Integer categoryId, Integer transactionId) throws ResourceNotFoundException {
        return transactionRepository.removeById(userId, categoryId, transactionId);
    }
}
