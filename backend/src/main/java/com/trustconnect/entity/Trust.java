package com.trustconnect.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "trusts")
public class Trust {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trustName;

    private String registrationNumber;

    private String address;

    private String phone;

    private String email;

    public Trust() {
    }

    public Trust(String trustName, String registrationNumber,
                 String address, String phone, String email) {
        this.trustName = trustName;
        this.registrationNumber = registrationNumber;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getTrustName() {
        return trustName;
    }

    public void setTrustName(String trustName) {
        this.trustName = trustName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}