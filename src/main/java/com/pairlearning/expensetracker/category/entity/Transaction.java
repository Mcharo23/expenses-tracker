package com.pairlearning.expensetracker.category.entity;

import com.pairlearning.expensetracker.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "et_transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "et_transactions_seq")
    @SequenceGenerator(name = "et_transactions_seq", sequenceName = "et_transactions_seq", allocationSize = 1, initialValue = 1000)
    @Column(name = "transaction_id")
    private int transactionId;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "note", nullable = false, length = 50)
    private String note;

    @Column(name = "transaction_date")
    private Long transactionDate;

}
