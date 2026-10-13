package com.trustconnect.service;

import com.trustconnect.dto.ReportResponse;
import com.trustconnect.entity.Donation;
import com.trustconnect.entity.DonationStatus;
import com.trustconnect.entity.Expense;
import com.trustconnect.repository.DonationRepository;
import com.trustconnect.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class ReportService {

    private final DonationRepository donationRepository;
    private final ExpenseRepository expenseRepository;

    public ReportService(DonationRepository donationRepository,
                         ExpenseRepository expenseRepository) {
        this.donationRepository = donationRepository;
        this.expenseRepository = expenseRepository;
    }

    public ReportResponse getReport(Long trustId, String period) {

        LocalDate today = LocalDate.now();

        LocalDate startDate;
        LocalDate endDate;

        switch (period.toUpperCase()) {

            case "WEEK":
                startDate = today.with(
                        TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)
                );
                endDate = startDate.plusDays(6);
                break;

            case "MONTH":
                startDate = today.with(
                        TemporalAdjusters.firstDayOfMonth()
                );
                endDate = today.with(
                        TemporalAdjusters.lastDayOfMonth()
                );
                break;

            case "YEAR":
                startDate = today.with(
                        TemporalAdjusters.firstDayOfYear()
                );
                endDate = today.with(
                        TemporalAdjusters.lastDayOfYear()
                );
                break;

            default:
                throw new RuntimeException(
                        "Period must be WEEK, MONTH or YEAR"
                );
        }

        List<Donation> donations =
                donationRepository.findByDonorTrustIdAndDonationDateBetween(
                        trustId,
                        startDate,
                        endDate
                );

        List<Expense> expenses =
                expenseRepository.findByTrustIdAndExpenseDateBetween(
                        trustId,
                        startDate,
                        endDate
                );

        BigDecimal totalPromised = donations.stream()
                .map(Donation::getPromisedAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalReceived = donations.stream()
                .map(Donation::getReceivedAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDue = donations.stream()
                .map(Donation::getDueAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpenses = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal balance = totalReceived.subtract(totalExpenses);

        long paidDonations = donations.stream()
                .filter(d -> d.getStatus() == DonationStatus.PAID)
                .count();

        long partialDonations = donations.stream()
                .filter(d -> d.getStatus() == DonationStatus.PARTIAL)
                .count();

        long dueDonations = donations.stream()
                .filter(d -> d.getStatus() == DonationStatus.DUE)
                .count();

        ReportResponse response = new ReportResponse();

        response.setPeriod(period.toUpperCase());
        response.setTotalPromised(totalPromised);
        response.setTotalReceived(totalReceived);
        response.setTotalDue(totalDue);
        response.setTotalExpenses(totalExpenses);
        response.setBalance(balance);
        response.setPaidDonations(paidDonations);
        response.setPartialDonations(partialDonations);
        response.setDueDonations(dueDonations);

        return response;
    }
}