package com.trustconnect.service;

import com.trustconnect.entity.Donor;
import com.trustconnect.entity.Trust;
import com.trustconnect.repository.DonorRepository;
import com.trustconnect.repository.TrustRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonorService {

    private final DonorRepository donorRepository;
    private final TrustRepository trustRepository;

    public DonorService(DonorRepository donorRepository,
                        TrustRepository trustRepository) {
        this.donorRepository = donorRepository;
        this.trustRepository = trustRepository;
    }

    public Donor addDonor(Long trustId, Donor donor) {

        Trust trust = trustRepository.findById(trustId)
                .orElseThrow(() -> new RuntimeException("Trust not found"));

        donor.setTrust(trust);

        return donorRepository.save(donor);
    }

    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    public List<Donor> getDonorsByTrust(Long trustId) {
        return donorRepository.findByTrustId(trustId);
    }

    public Donor getDonorById(Long id) {
        return donorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donor not found"));
    }

    public void deleteDonor(Long id) {
        donorRepository.deleteById(id);
    }
}