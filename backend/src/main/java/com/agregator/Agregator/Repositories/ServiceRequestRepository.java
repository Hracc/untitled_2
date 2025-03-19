package com.agregator.Agregator.Repositories;

import com.agregator.Agregator.Entity.Customer;
import com.agregator.Agregator.Entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Integer> {
    List<ServiceRequest> findByOrganization_OrganizationIdAndDateServiceBefore(int organizationId, LocalDateTime dateService);
    List<ServiceRequest> findByCustomer(Customer customer);
}

