package com.trustconnect.service;

import com.trustconnect.entity.Trust;
import com.trustconnect.repository.TrustRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrustService {

    private final TrustRepository trustRepository;

    public TrustService(TrustRepository trustRepository) {
        this.trustRepository = trustRepository;
    }

    public Trust addTrust(Trust trust) {
        return trustRepository.save(trust);
    }

    public List<Trust> getAllTrusts() {
        return trustRepository.findAll();
    }

    public Trust getTrustById(Long id) {
        return trustRepository.findById(id).orElse(null);
    }

    public void deleteTrust(Long id) {
        trustRepository.deleteById(id);
    }
}