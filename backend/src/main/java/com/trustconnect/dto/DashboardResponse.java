package com.trustconnect.dto;

import java.math.BigDecimal;

public class DashboardResponse {

    private BigDecimal totalPromised;
    private BigDecimal totalReceived;
    private BigDecimal totalDue;
    private BigDecimal totalExpenses;
    private BigDecimal balance;

    public DashboardResponse() {
    }

    public DashboardResponse(BigDecimal totalPromised,
                             BigDecimal totalReceived,
                             BigDecimal totalDue,
                             BigDecimal totalExpenses,
                             BigDecimal balance) {
        this.totalPromised = totalPromised;
        this.totalReceived = totalReceived;
        this.totalDue = totalDue;
        this.totalExpenses = totalExpenses;
        this.balance = balance;
    }

    public BigDecimal getTotalPromised() {
        return totalPromised;
    }

    public BigDecimal getTotalReceived() {
        return totalReceived;
    }

    public BigDecimal getTotalDue() {
        return totalDue;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}