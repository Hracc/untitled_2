package com.agregator.Agregator.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "address_type_id", nullable = false)
    private AddressType addressType;

    @Column(nullable = false, length = 50)
    private String subjectName;

    @Column(nullable = false, length = 50)
    private String cityName;

    @Column(nullable = false, length = 50)
    private String streetName;

    @Column(nullable = false, length = 10)
    private String houseNumber;

    @Column(length = 250)
    private String addInfo;

    // геттеры и сеттеры
}