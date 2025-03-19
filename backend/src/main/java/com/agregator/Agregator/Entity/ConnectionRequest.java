package com.agregator.Agregator.Entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Entity
public class ConnectionRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int connectionRequestId;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Column(nullable = false, length = 20)
    private String regNumber;

    @Column(nullable = false)
    private LocalDate dateBegin;

    @Column(nullable = false)
    private LocalDate dateEnd;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(length = 250)
    private String addInfo;
}
