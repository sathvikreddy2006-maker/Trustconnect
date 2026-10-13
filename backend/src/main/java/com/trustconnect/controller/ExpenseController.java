package com.trustconnect.controller;

import com.trustconnect.entity.Expense;
import com.trustconnect.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(
	    origins = "http://localhost:5173",
	    allowCredentials = "true"
	)
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping("/trust/{trustId}")
    public Expense addExpense(
            @PathVariable Long trustId,
            @RequestBody Expense expense) {

        return expenseService.addExpense(trustId, expense);
    }

    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/trust/{trustId}")
    public List<Expense> getExpensesByTrust(
            @PathVariable Long trustId) {

        return expenseService.getExpensesByTrust(trustId);
    }

    @GetMapping("/{id}")
    public Expense getExpenseById(
            @PathVariable Long id) {

        return expenseService.getExpenseById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteExpense(
            @PathVariable Long id) {

        expenseService.deleteExpense(id);

        return "Expense deleted successfully";
    }
}