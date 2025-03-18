package com.agregator.Agregator.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
public class AddressType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressTypeId;

    @Column(nullable = false, length = 30)
    private String addressTypeName;

    @Column(length = 250)
    private String addInfo;

    // геттеры и сеттеры
}