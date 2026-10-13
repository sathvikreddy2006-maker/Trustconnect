package com.trustconnect.controller;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trustconnect.entity.Donation;
import com.trustconnect.entity.DonationStatus;
import com.trustconnect.service.DonationService;

@RestController
@RequestMapping("/api/donations")
@CrossOrigin(
	    origins = "http://localhost:5173",
	    allowCredentials = "true"
	)
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @PostMapping("/donor/{donorId}")
    public Donation addDonation(
            @PathVariable Long donorId,
            @RequestBody Donation donation) {

        return donationService.addDonation(donorId, donation);
    }

    @GetMapping
    public List<Donation> getAllDonations() {
        return donationService.getAllDonations();
    }

    @GetMapping("/donor/{donorId}")
    public List<Donation> getDonationsByDonor(
            @PathVariable Long donorId) {

        return donationService.getDonationsByDonor(donorId);
    }

    @GetMapping("/status/{status}")
    public List<Donation> getDonationsByStatus(
            @PathVariable DonationStatus status) {

        return donationService.getDonationsByStatus(status);
    }

@PutMapping("/{id}/follow-up")
public Donation updateFollowUp(
        @PathVariable Long id,
        @RequestParam Boolean contacted) {

    return donationService.updateFollowUp(id, contacted);
}

}