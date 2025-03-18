package com.agregator.Agregator.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
public class ServiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceDetailId;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private ServiceType serviceType;

    @Column(nullable = false, length = 3)
    private String serviceDetailCode;

    @Column(nullable = false, length = 50)
    private String serviceDetailName;

    @Column(nullable = false)
    private int serviceDetailCost;

    @Column(nullable = false)
    private int serviceDetailDuration;

    @Column(length = 250)
    private String addInfo;

    // геттеры и сеттеры
}
