package com.trustconnect.repository;

import com.trustconnect.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonorRepository extends JpaRepository<Donor, Long> {

    List<Donor> findByTrustId(Long trustId);
}