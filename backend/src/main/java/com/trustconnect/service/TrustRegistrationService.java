package com.trustconnect.service;

import com.trustconnect.dto.TrustRegistrationRequest;
import com.trustconnect.entity.MemberRole;
import com.trustconnect.entity.Trust;
import com.trustconnect.entity.TrustMember;
import com.trustconnect.repository.TrustMemberRepository;
import com.trustconnect.repository.TrustRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class TrustRegistrationService {

    private final TrustRepository trustRepository;
    private final TrustMemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public TrustRegistrationService(
            TrustRepository trustRepository,
            TrustMemberRepository memberRepository,
            PasswordEncoder passwordEncoder) {
        this.trustRepository = trustRepository;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerTrust(TrustRegistrationRequest request) {
        String email = request.email().trim().toLowerCase(Locale.ROOT);
        if (memberRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered");
        }

        Trust trust = new Trust(
                request.trustName().trim(),
                request.registrationNumber(),
                request.address(),
                request.phone(),
                request.trustEmail());
        trustRepository.save(trust);

        TrustMember admin = new TrustMember();
        admin.setFullName(request.fullName().trim());
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(request.password()));
        admin.setRole(MemberRole.ADMIN);
        admin.setTrust(trust);
        memberRepository.save(admin);
    }
}