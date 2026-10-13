package com.trustconnect.repository;

import com.trustconnect.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByTrustId(Long trustId);

    List<Expense> findByTrustIdAndExpenseDateBetween(
            Long trustId,
            LocalDate startDate,
            LocalDate endDate
    );

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM Expense e WHERE e.trust.id = :trustId")
    BigDecimal getTotalExpenses(Long trustId);
}