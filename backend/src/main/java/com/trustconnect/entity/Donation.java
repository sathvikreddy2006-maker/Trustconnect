package com.trustconnect.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal promisedAmount;

    private BigDecimal receivedAmount;

    private BigDecimal dueAmount;

    private LocalDate donationDate;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private DonationStatus status;
    private Boolean followUpContacted = false;

    private LocalDate followUpDate;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Donor donor;

    public Donation() {
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getPromisedAmount() {
        return promisedAmount;
    }

    public void setPromisedAmount(BigDecimal promisedAmount) {
        this.promisedAmount = promisedAmount;
    }

    public BigDecimal getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(BigDecimal receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public BigDecimal getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(BigDecimal dueAmount) {
        this.dueAmount = dueAmount;
    }

    public LocalDate getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(LocalDate donationDate) {
        this.donationDate = donationDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public DonationStatus getStatus() {
        return status;
    }

    public void setStatus(DonationStatus status) {
        this.status = status;
    }

    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }
    public Boolean getFollowUpContacted() {
        return followUpContacted;
    }

    public void setFollowUpContacted(Boolean followUpContacted) {
        this.followUpContacted = followUpContacted;
    }

    public LocalDate getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(LocalDate followUpDate) {
        this.followUpDate = followUpDate;
    }
}