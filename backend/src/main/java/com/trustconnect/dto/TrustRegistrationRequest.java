package com.trustconnect.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TrustRegistrationRequest(
        @NotBlank @Size(max = 255) String fullName,
        @NotBlank @Email @Size(max = 255) String email,
        @NotBlank @Size(min = 6, max = 72) String password,
        @NotBlank @Size(max = 255) String trustName,
        @Size(max = 255) String registrationNumber,
        @Size(max = 1000) String address,
        @Size(max = 100) String phone,
        @Email @Size(max = 255) String trustEmail) {
}