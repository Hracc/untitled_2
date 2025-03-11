package com.agregator.Agregator.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int organizationId;

    @Column(nullable = false, length = 150)
    private String organizationFullName;

    @Column(nullable = false, length = 50)
    private String organizationShortName;

    @Column(nullable = false, length = 10)
    private String inn;

    @Column(nullable = false, length = 9)
    private String kpp;

    @Column(nullable = false, length = 13)
    private String ogrn;

    @Column(nullable = false, length = 50)
    private String responsiblePersonSurname;

    @Column(nullable = false, length = 50)
    private String responsiblePersonName;

    @Column(length = 50)
    private String responsiblePersonPatronymic;

    @Column(nullable = false, length = 50)
    private String responsiblePersonEmail;

    @Column(nullable = false, length = 20)
    private String responsiblePersonPhoneNumber;

    @Column(length = 250)
    private String addInfo;

    // геттеры и сеттеры
}