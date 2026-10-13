package com.trustconnect.dto;

import java.math.BigDecimal;

public class ReportResponse {

    private String period;
    private BigDecimal totalPromised;
    private BigDecimal totalReceived;
    private BigDecimal totalDue;
    private BigDecimal totalExpenses;
    private BigDecimal balance;

    private long paidDonations;
    private long partialDonations;
    private long dueDonations;

    public ReportResponse() {
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public BigDecimal getTotalPromised() {
        return totalPromised;
    }

    public void setTotalPromised(BigDecimal totalPromised) {
        this.totalPromised = totalPromised;
    }

    public BigDecimal getTotalReceived() {
        return totalReceived;
    }

    public void setTotalReceived(BigDecimal totalReceived) {
        this.totalReceived = totalReceived;
    }

    public BigDecimal getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(BigDecimal totalDue) {
        this.totalDue = totalDue;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(BigDecimal totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public long getPaidDonations() {
        return paidDonations;
    }

    public void setPaidDonations(long paidDonations) {
        this.paidDonations = paidDonations;
    }

    public long getPartialDonations() {
        return partialDonations;
    }

    public void setPartialDonations(long partialDonations) {
        this.partialDonations = partialDonations;
    }

    public long getDueDonations() {
        return dueDonations;
    }

    public void setDueDonations(long dueDonations) {
        this.dueDonations = dueDonations;
    }
}