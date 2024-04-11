package com.pairlearning.expensetracker.transaction.repository.impl;

import com.pairlearning.expensetracker.category.entity.Category;
import com.pairlearning.expensetracker.category.repository.impl.CategoryRepositoryImpl;
import com.pairlearning.expensetracker.transaction.dto.TransactionDto;
import com.pairlearning.expensetracker.transaction.entity.Transaction;
import com.pairlearning.expensetracker.transaction.exception.BadRequestException;
import com.pairlearning.expensetracker.transaction.exception.ResourceNotFoundException;
import com.pairlearning.expensetracker.transaction.repository.TransactionRepository;
import com.pairlearning.expensetracker.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {
    private static final Logger logger = LoggerFactory.getLogger(TransactionRepository.class);

    private static final String SQL_FIND_ALL = "SELECT * FROM et_transactions " +
            "WHERE category_id = ? AND user_id = ?";

    private static final String SQL_FIND_BY_ID = "SELECT * FROM et_transactions " +
            "WHERE transaction_id = ? AND category_id = ? AND user_id = ?";


    private static final String SQL_CREATE = "INSERT INTO et_transactions (transaction_id, category_id, user_id, amount, note, transaction_date) " +
            "VALUES (NEXTVAL('et_transactions_seq'), ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE = "UPDATE et_transactions SET amount = ?, note = ?, transaction_date = ? " +
            "WHERE transaction_id = ? AND category_id = ? AND user_id = ?";

    private static final String SQL_DELETE = "DELETE FROM et_transactions " +
            "WHERE transaction_id = ? AND category_id = ? AND user_id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Transaction> findAll(Integer userId, Integer categoryId) {
        return jdbcTemplate.query(SQL_FIND_ALL, new Object[]{categoryId, userId}, transactionRowMapper);
    }

    @Override
    public Transaction findById(Integer userId, Integer categoryId, Integer transactionId) throws ResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{transactionId, categoryId, userId}, transactionRowMapper);
        } catch (Exception e) {
            logger.error("Error occurred while creating transaction", e);
            throw new ResourceNotFoundException("Transaction not found!. " + e.getMessage());
        }
    }

    @Override
    public Integer create(Integer userid, Integer categoryId, Transaction transaction) throws BadRequestException {

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, categoryId);
            ps.setInt(2, userid);
            ps.setDouble(3, transaction.getAmount());
            ps.setString(4, transaction.getNote());
            ps.setLong(5, transaction.getTransactionDate());
            return ps;
            }, keyHolder);

        return (Integer) keyHolder.getKeys().get("TRANSACTION_ID");
        } catch (Exception e) {
            logger.error("Error occurred while creating transaction", e);
            throw new com.pairlearning.expensetracker.category.exception.BadRequestException("Invalid request: " + e.getMessage());
        }
    }

    @Override
    public boolean update(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction) throws BadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE, new Object[]{transaction.getAmount(), transaction.getNote(), transaction.getTransactionId(), transactionId, categoryId, userId});
            return true;
        } catch (Exception e) {
            throw new BadRequestException("Invalid request");
        }

    }

    @Override
    public boolean removeById(Integer userId, Integer categoryId, Integer transactionId) throws ResourceNotFoundException {
        int count = jdbcTemplate.update(SQL_DELETE, new Object[]{transactionId, categoryId, userId});
        if (count == 0)
            throw new ResourceNotFoundException("Transaction not found");

        return true;
    }

    private final RowMapper<Transaction> transactionRowMapper = ((rs, rowNum) -> {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(rs.getInt("transaction_id"));

        Category category = new Category();
        category.setCategoryId(rs.getInt("category_id"));
        transaction.setCategory(category);

        User user = new User();
        user.setUserId(rs.getLong("user_id"));
        transaction.setUser(user);

        transaction.setAmount(rs.getDouble("amount"));
        transaction.setNote(rs.getString("note"));
        transaction.setTransactionDate(rs.getLong("transaction_date"));

        return transaction;
    });
}
