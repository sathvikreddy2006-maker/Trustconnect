package com.trustconnect.service;

import com.trustconnect.entity.Expense;
import com.trustconnect.entity.Trust;
import com.trustconnect.repository.ExpenseRepository;
import com.trustconnect.repository.TrustRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final TrustRepository trustRepository;

    public ExpenseService(ExpenseRepository expenseRepository,
                          TrustRepository trustRepository) {
        this.expenseRepository = expenseRepository;
        this.trustRepository = trustRepository;
    }

    public Expense addExpense(Long trustId, Expense expense) {

        Trust trust = trustRepository.findById(trustId)
                .orElseThrow(() -> new RuntimeException("Trust not found"));

        expense.setTrust(trust);

        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public List<Expense> getExpensesByTrust(Long trustId) {
        return expenseRepository.findByTrustId(trustId);
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }
}