package com.pairlearning.expensetracker.transaction.controller;

import com.pairlearning.expensetracker.transaction.dto.TransactionDto;
import com.pairlearning.expensetracker.transaction.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories/{categoryId}/transactions")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @DeleteMapping("{transactionId}")
    public ResponseEntity<Map<String, Boolean>> deleteTransaction(HttpServletRequest request,
                                                                  @PathVariable("categoryId") Integer categoryId,
                                                                  @PathVariable("transactionId") Integer transactionId) {
        boolean success = transactionService.removeTransaction(getUserId(request), categoryId, transactionId);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", success);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PutMapping("{transactionId}")
    public ResponseEntity<Map<String, Boolean>> updateTransaction(HttpServletRequest request,
                                                                  @PathVariable("categoryId") Integer categoryId,
                                                                  @PathVariable("transactionId") Integer transactionId,
                                                                  @RequestBody TransactionDto transactionDto) {
        transactionDto.setTransactionDate(System.currentTimeMillis());

        boolean success = transactionService.updateTransaction(getUserId(request), categoryId, transactionId, transactionDto);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", success);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDto>> getAllTransactions(HttpServletRequest request,
                                                                   @PathVariable("categoryId") Integer categoryId) {
        List<TransactionDto> transactionDtoList = transactionService.fetchAllTransaction(getUserId(request), categoryId);
        return new ResponseEntity<>(transactionDtoList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TransactionDto> addTransaction(HttpServletRequest request,
                                                         @PathVariable("categoryId") Integer categoryId,
                                                         @RequestBody TransactionDto transactionDto) {
        transactionDto.setTransactionDate(System.currentTimeMillis());
        TransactionDto savedTransaction = transactionService.addTransaction(getUserId(request), categoryId, transactionDto);
        return new ResponseEntity<>(savedTransaction, HttpStatus.CREATED);
    }

    @GetMapping("{transactionId}")
    public ResponseEntity<TransactionDto> getTransactionById(HttpServletRequest request,
                                                             @PathVariable("categoryId") Integer categoryId,
                                                             @PathVariable("transactionId") Integer transactionId) {
        return new ResponseEntity<>(transactionService.fetchTransactionById(getUserId(request), categoryId, transactionId), HttpStatus.OK);
    }

    private int getUserId(HttpServletRequest request) {
        return (Integer) request.getAttribute("userId");
    }
}
