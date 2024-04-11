package com.pairlearning.expensetracker.category.repository.impl;

import com.pairlearning.expensetracker.category.dto.CategoryDto;
import com.pairlearning.expensetracker.category.entity.Category;
import com.pairlearning.expensetracker.category.exception.BadRequestException;
import com.pairlearning.expensetracker.category.exception.CategoryNotFoundException;
import com.pairlearning.expensetracker.category.repository.CategoryRepository;
import com.pairlearning.expensetracker.user.entity.User;
import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    private static final Logger logger = LoggerFactory.getLogger(CategoryRepositoryImpl.class);

    private static final String SQL_FIND_ALL = "SELECT categories.category_id, categories.title, categories.description, COALESCE(SUM(et_transactions.amount), 0) AS total_expense FROM categories LEFT OUTER JOIN et_transactions ON categories.category_id = et_transactions.category_id WHERE categories.user_id = ? GROUP BY categories.category_id";

    private static final String SQL_FIND_BY_ID = "SELECT C.category_id, C.title, C.description, " +
            "COALESCE(SUM(T.amount), 0) AS total_expense " +
            "FROM et_transactions T " +
            "RIGHT OUTER JOIN categories C " +
            "ON C.category_id = T.category_id " +
            "WHERE C.user_id = ? AND C.category_id = ? GROUP BY C.category_id";


    private static final String SQL_CREATE = "INSERT INTO categories (category_id, user_id, title, description) VALUES(NEXTVAL('et_categories_seq'), ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE categories SET title = ?, description = ? WHERE user_id = ? AND category_id = ?";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> findAll(Integer userId) {
        return jdbcTemplate.query(SQL_FIND_ALL, new Object[]{userId}, categoryRowMapper);
    }

    @Override
    public Category findById(Integer userId, Integer categoryId) throws CategoryNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId, categoryId}, categoryRowMapper);
        } catch (Exception e) {
            throw new CategoryNotFoundException("Category not found!.");
        }
    }

    @Override
    public Integer create(Integer userId, Category category) throws BadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setString(2, category.getTitle());
                ps.setString(3, category.getDescription());
                return ps;
            }, keyHolder);

            return (Integer) keyHolder.getKeys().get("CATEGORY_ID");
        } catch (Exception e) {
            logger.error("Error occurred while creating category", e);
            throw new BadRequestException("Invalid request: " + e.getMessage());
        }
    }



    @Override
    public void update(Integer userId, Integer categoryID, Category category) throws BadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE, new Object[]{category.getTitle(), category.getDescription(), userId, categoryID});
        } catch (Exception e) {
            throw new BadRequestException("Invalid request: " + e.getMessage());
        }
    }

    @Override
    public void removeById(Integer userId, Integer categoryId) {

    }

    private final RowMapper<Category> categoryRowMapper = ((rs, rowNum) -> {
        return new Category(
                rs.getInt("category_id"),
                null,
                rs.getString("title"),
                rs.getString("description"),
                rs.getDouble("total_expense")
        );
    });
}
