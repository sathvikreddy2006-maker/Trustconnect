package com.trustconnect.dto;

public class LoginResponse {

    private Long id;
    private String fullName;
    private String email;
    private String role;
    private Long trustId;
    private String trustName;

    public LoginResponse() {
    }

    public LoginResponse(
            Long id,
            String fullName,
            String email,
            String role,
            Long trustId,
            String trustName) {

        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.trustId = trustId;
        this.trustName = trustName;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public Long getTrustId() {
        return trustId;
    }

    public String getTrustName() {
        return trustName;
    }
}