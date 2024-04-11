package com.pairlearning.expensetracker.category.entity;

import com.pairlearning.expensetracker.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "et_categories_seq")
    @SequenceGenerator(name = "et_categories_seq", sequenceName = "et_categories_seq", allocationSize = 1)
    @Column(name = "category_id")
    private int categoryId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "total_expense", nullable = true)
    private Double totalExpense;
}
