package com.pairlearning.expensetracker.category.repository;

import com.pairlearning.expensetracker.category.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
