package com.trustconnect.repository;

import com.trustconnect.entity.Trust;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrustRepository extends JpaRepository<Trust, Long> {
}