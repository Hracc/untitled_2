package com.agregator.Agregator.Repositories;

import com.agregator.Agregator.Entity.AggregatorSpecialist;
import com.agregator.Agregator.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AggregatorSpecialistRepository extends JpaRepository<AggregatorSpecialist, Integer> {
    Optional<AggregatorSpecialist> findByaggregatorSpecialistsEmail(String email);
}
