package com.trustconnect.repository;

import com.trustconnect.entity.TrustMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface TrustMemberRepository extends JpaRepository<TrustMember, Long> {

    Optional<TrustMember> findByEmail(String email);

    List<TrustMember> findByTrustId(Long trustId);

    boolean existsByEmail(String email);
}