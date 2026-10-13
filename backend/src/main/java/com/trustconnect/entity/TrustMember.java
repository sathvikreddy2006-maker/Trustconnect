package com.trustconnect.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "trust_members")
public class TrustMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @ManyToOne
    @JoinColumn(name = "trust_id", nullable = false)
    private Trust trust;

    public TrustMember() {
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MemberRole getRole() {
        return role;
    }

    public void setRole(MemberRole role) {
        this.role = role;
    }

    public Trust getTrust() {
        return trust;
    }

    public void setTrust(Trust trust) {
        this.trust = trust;
    }
}