package com.agregator.Agregator.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Entity
@Getter
@Setter
public class ServiceRequestDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int serviceRequestDetailId;

    @ManyToOne
    @JoinColumn(name = "service_request_id", nullable = false)
    private ServiceRequest serviceRequest;

    @ManyToOne
    @JoinColumn(name = "service_detail_id", nullable = false)
    private ServiceDetail serviceDetail;

    // геттеры и сеттеры
}