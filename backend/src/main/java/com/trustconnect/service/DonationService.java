package com.trustconnect.service;

import com.trustconnect.entity.Donation;
import com.trustconnect.entity.DonationStatus;
import com.trustconnect.entity.Donor;
import com.trustconnect.repository.DonationRepository;
import com.trustconnect.repository.DonorRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final DonorRepository donorRepository;

    public DonationService(DonationRepository donationRepository,
                           DonorRepository donorRepository) {
        this.donationRepository = donationRepository;
        this.donorRepository = donorRepository;
    }

    public Donation addDonation(Long donorId, Donation donation) {

        Donor donor = donorRepository.findById(donorId)
                .orElseThrow(() -> new RuntimeException("Donor not found"));

        if (donation.getPromisedAmount() == null ||
                donation.getReceivedAmount() == null) {
            throw new RuntimeException("Amount cannot be empty");
        }

        if (donation.getReceivedAmount()
                .compareTo(donation.getPromisedAmount()) > 0) {
            throw new RuntimeException(
                    "Received amount cannot be greater than promised amount");
        }

        BigDecimal due = donation.getPromisedAmount()
                .subtract(donation.getReceivedAmount());

        donation.setDueAmount(due);

        if (due.compareTo(BigDecimal.ZERO) == 0) {
            donation.setStatus(DonationStatus.PAID);
        } else if (donation.getReceivedAmount()
                .compareTo(BigDecimal.ZERO) > 0) {
            donation.setStatus(DonationStatus.PARTIAL);
        } else {
            donation.setStatus(DonationStatus.DUE);
        }

        donation.setDonor(donor);

        return donationRepository.save(donation);
    }

    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    public List<Donation> getDonationsByDonor(Long donorId) {
        return donationRepository.findByDonorId(donorId);
    }

    public List<Donation> getDonationsByStatus(DonationStatus status) {
        return donationRepository.findByStatus(status);
    }

    public Donation getDonationById(Long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found"));
    }

public Donation updateFollowUp(Long id, Boolean contacted) {

    Donation donation = donationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Donation not found"));

    if (contacted == null) {
        throw new RuntimeException("Follow-up status cannot be empty");
    }

    donation.setFollowUpContacted(contacted);

    if (contacted) {
        donation.setFollowUpDate(java.time.LocalDate.now());
    } else {
        donation.setFollowUpDate(null);
    }

    return donationRepository.save(donation);
}


    public void deleteDonation(Long id) {
        donationRepository.deleteById(id);
    }
}