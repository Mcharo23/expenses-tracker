package com.pairlearning.expensetracker.transaction.mapper;

import com.pairlearning.expensetracker.transaction.dto.TransactionDto;
import com.pairlearning.expensetracker.transaction.entity.Transaction;

public class TransactionMapper {
    public static TransactionDto mapToTransactionDto(Transaction transaction) {
        return new TransactionDto(
                transaction.getTransactionId(),
                transaction.getCategory(),
                transaction.getUser(),
                transaction.getAmount(),
                transaction.getNote(),
                transaction.getTransactionDate()
        );
    }

    public static Transaction mapToTransaction(TransactionDto transactionDto) {
        return new Transaction(
                transactionDto.getTransactionId(),
                transactionDto.getCategory(),
                transactionDto.getUser(),
                transactionDto.getAmount(),
                transactionDto.getNote(),
                transactionDto.getTransactionDate()
        );
    }
}
