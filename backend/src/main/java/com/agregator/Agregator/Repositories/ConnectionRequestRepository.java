package com.agregator.Agregator.Repositories;

import com.agregator.Agregator.Entity.ConnectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, Integer> {
}
