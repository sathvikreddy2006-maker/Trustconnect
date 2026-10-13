package com.trustconnect.service;

import com.trustconnect.dto.DashboardResponse;
import com.trustconnect.repository.DonationRepository;
import com.trustconnect.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DashboardService {

    private final DonationRepository donationRepository;
    private final ExpenseRepository expenseRepository;

    public DashboardService(DonationRepository donationRepository,
                            ExpenseRepository expenseRepository) {
        this.donationRepository = donationRepository;
        this.expenseRepository = expenseRepository;
    }

    public DashboardResponse getDashboard(Long trustId) {

        BigDecimal totalPromised =
                donationRepository.getTotalPromised(trustId);

        BigDecimal totalReceived =
                donationRepository.getTotalReceived(trustId);

        BigDecimal totalDue =
                donationRepository.getTotalDue(trustId);

        BigDecimal totalExpenses =
                expenseRepository.getTotalExpenses(trustId);

        BigDecimal balance =
                totalReceived.subtract(totalExpenses);

        return new DashboardResponse(
                totalPromised,
                totalReceived,
                totalDue,
                totalExpenses,
                balance
        );
    }
}