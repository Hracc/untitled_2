package com.agregator.Agregator.Repositories;

import com.agregator.Agregator.Entity.AggregatorSpecialistConnectorRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AggregatorSpecialistConnectorRequestRepository extends JpaRepository<AggregatorSpecialistConnectorRequest, Integer> {
}
