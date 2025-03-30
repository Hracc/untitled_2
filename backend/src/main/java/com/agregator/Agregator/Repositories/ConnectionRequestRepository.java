package com.agregator.Agregator.Repositories;

import com.agregator.Agregator.Entity.ConnectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, Integer> {
    List<ConnectionRequest> findByOrganization_OrganizationId(int organizationId);
    List<ConnectionRequest> findByStatus(String status);
}
