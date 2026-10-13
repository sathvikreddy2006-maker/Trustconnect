package com.trustconnect.service;

import com.trustconnect.entity.TrustMember;
import com.trustconnect.repository.TrustMemberRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final TrustMemberRepository memberRepository;

    public CustomUserDetailsService(
            TrustMemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        TrustMember member = memberRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"));

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .authorities(
                        new SimpleGrantedAuthority(
                                "ROLE_" + member.getRole().name()
                        )
                )
                .build();
    }
}