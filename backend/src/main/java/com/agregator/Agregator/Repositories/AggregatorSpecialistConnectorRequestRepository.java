package com.agregator.Agregator.Repositories;

import com.agregator.Agregator.Entity.AggregatorSpecialist;
import com.agregator.Agregator.Entity.AggregatorSpecialistConnectorRequest;
import com.agregator.Agregator.Entity.ConnectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AggregatorSpecialistConnectorRequestRepository extends JpaRepository<AggregatorSpecialistConnectorRequest, Integer> {
    boolean existsByAggregatorSpecialistAndConnectionRequest(AggregatorSpecialist aggregatorSpecialist, ConnectionRequest connectionRequest);
}
