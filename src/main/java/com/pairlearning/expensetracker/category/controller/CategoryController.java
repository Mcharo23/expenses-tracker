package com.pairlearning.expensetracker.category.controller;

import com.pairlearning.expensetracker.category.dto.CategoryDto;
import com.pairlearning.expensetracker.category.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @DeleteMapping("{categoryId}")
    public ResponseEntity<Map<String, Boolean>> removeCategoryWithAllTransactions(HttpServletRequest request,
                                                                                  @PathVariable("categoryId") Integer categoryId) {
        boolean success = categoryService.removeCategoryWithAllTransactions(getUserId(request), categoryId);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", success);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/add-category")
    public ResponseEntity<CategoryDto> addCategory(HttpServletRequest request,
                                                   @RequestBody CategoryDto categoryDto) {
        CategoryDto addedCategory = categoryService.addCategory(getUserId(request),categoryDto);

        return new ResponseEntity<>(addedCategory, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> findCategoryById(HttpServletRequest request, @PathVariable("id") int categoryId) {
        return new ResponseEntity<>(categoryService.fetchCategoryById(getUserId(request), categoryId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(HttpServletRequest request) {

        return new ResponseEntity<>(categoryService.fetchAllCategories(getUserId(request)), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Map<String, Boolean>> updateCategory(HttpServletRequest request,
                                                              @PathVariable("id") Integer categoryId,
                                                              @RequestBody CategoryDto categoryDto) {
        categoryService.updateCategory(getUserId(request), categoryId, categoryDto);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    private int getUserId(HttpServletRequest request) {
        return (Integer) request.getAttribute("userId");
    }
}
