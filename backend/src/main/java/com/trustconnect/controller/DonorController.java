package com.trustconnect.controller;

import com.trustconnect.entity.Donor;
import com.trustconnect.service.DonorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donors")
@CrossOrigin(
	    origins = "http://localhost:5173",
	    allowCredentials = "true"
	)
public class DonorController {

    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @PostMapping("/trust/{trustId}")
    public Donor addDonor(
            @PathVariable Long trustId,
            @RequestBody Donor donor) {

        return donorService.addDonor(trustId, donor);
    }

    @GetMapping
    public List<Donor> getAllDonors() {
        return donorService.getAllDonors();
    }

    @GetMapping("/trust/{trustId}")
    public List<Donor> getDonorsByTrust(
            @PathVariable Long trustId) {

        return donorService.getDonorsByTrust(trustId);
    }

    @GetMapping("/{id}")
    public Donor getDonorById(
            @PathVariable Long id) {

        return donorService.getDonorById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteDonor(
            @PathVariable Long id) {

        donorService.deleteDonor(id);

        return "Donor deleted successfully";
    }
}