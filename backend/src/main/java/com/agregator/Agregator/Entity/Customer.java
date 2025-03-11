package com.agregator.Agregator.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

    @Column(nullable = false, length = 50)
    private String customerSurname;

    @Column(nullable = false, length = 50)
    private String customerName;

    @Column(length = 50)
    private String customerPatronymic;

    @Column(nullable = false, length = 20)
    private String customerPhoneNumber;

    @Column(length = 250)
    private String addInfo;

    // геттеры и сеттеры
}
