package com.trustconnect.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "donors")
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phone;

    private String email;

    private String address;

    @ManyToOne
    @JoinColumn(name = "trust_id")
    private Trust trust;

    public Donor() {
    }

    public Donor(String name, String phone, String email,
                 String address, Trust trust) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.trust = trust;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Trust getTrust() {
        return trust;
    }

    public void setTrust(Trust trust) {
        this.trust = trust;
    }
}