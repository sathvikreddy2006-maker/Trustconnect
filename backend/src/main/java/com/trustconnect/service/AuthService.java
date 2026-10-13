package com.trustconnect.service;

import com.trustconnect.dto.LoginRequest;
import com.trustconnect.dto.LoginResponse;
import com.trustconnect.entity.TrustMember;
import com.trustconnect.repository.TrustMemberRepository;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final TrustMemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;

    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    public AuthService(
            TrustMemberRepository memberRepository,
            AuthenticationManager authenticationManager) {

        this.memberRepository = memberRepository;
        this.authenticationManager = authenticationManager;
    }

    public LoginResponse login(
            LoginRequest request,
            HttpServletRequest httpRequest) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        SecurityContext context =
                SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        securityContextRepository.saveContext(
                context,
                httpRequest,
                null
        );

        TrustMember member = memberRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return new LoginResponse(
                member.getId(),
                member.getFullName(),
                member.getEmail(),
                member.getRole().name(),
                member.getTrust().getId(),
                member.getTrust().getTrustName()
        );
    }
}