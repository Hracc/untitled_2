package com.agregator.Agregator.Repositories;

import com.agregator.Agregator.Entity.AggregatorSpecialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AggregatorSpecialistRepository extends JpaRepository<AggregatorSpecialist, Integer> {
}
