package com.agregator.Agregator.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
public class AggregatorSpecialistConnectorRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aggregatorSpecialistConnectorRequestId;

    @ManyToOne
    @JoinColumn(name = "aggregator_specialists_id", nullable = false)
    private AggregatorSpecialist aggregatorSpecialist;

    @ManyToOne
    @JoinColumn(name = "connection_request_id", nullable = false)
    private ConnectionRequest connectionRequest;
}
