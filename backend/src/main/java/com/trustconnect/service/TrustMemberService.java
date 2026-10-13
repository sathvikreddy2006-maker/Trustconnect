package com.trustconnect.service;

import com.trustconnect.entity.MemberRole;

import com.trustconnect.entity.Trust;
import com.trustconnect.entity.TrustMember;
import com.trustconnect.repository.TrustMemberRepository;
import com.trustconnect.repository.TrustRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrustMemberService {

    private final TrustMemberRepository memberRepository;
    private final TrustRepository trustRepository;

    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public TrustMemberService(
            TrustMemberRepository memberRepository,
            TrustRepository trustRepository) {

        this.memberRepository = memberRepository;
        this.trustRepository = trustRepository;
    }
    

public TrustMember registerMember(
        Long trustId,
        TrustMember member) {

    if (member.getEmail() == null || member.getEmail().isBlank()) {
        throw new RuntimeException("Email is required");
    }

    if (member.getPassword() == null || member.getPassword().isBlank()) {
        throw new RuntimeException("Password is required");
    }

    if (memberRepository.existsByEmail(member.getEmail())) {
        throw new RuntimeException("Email already registered");
    }

    Trust trust = trustRepository.findById(trustId)
            .orElseThrow(() -> new RuntimeException("Trust not found"));

    member.setTrust(trust);
    member.setPassword(passwordEncoder.encode(member.getPassword()));

    // Public registrations must never create administrators.
    member.setRole(MemberRole.MEMBER);

    return memberRepository.save(member);
}

public List<TrustMember> getMembers(Long trustId) {
    return memberRepository.findByTrustId(trustId);
}


public TrustMember getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Member not found"));
    }
}