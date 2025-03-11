package com.agregator.Agregator.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Entity
public class ServiceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int typeId;

    @Column(nullable = false, length = 2)
    private String typeCode;

    @Column(nullable = false, length = 30)
    private String typeName;

    // геттеры и сеттеры
}
