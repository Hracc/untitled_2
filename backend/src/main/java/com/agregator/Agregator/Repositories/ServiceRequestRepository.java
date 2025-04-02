package com.agregator.Agregator.Repositories;

import com.agregator.Agregator.DTO.ServiceRequestDTO;
import com.agregator.Agregator.Entity.Customer;
import com.agregator.Agregator.Entity.Organization;
import com.agregator.Agregator.Entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Integer> {

    List<ServiceRequest> findByOrganization_OrganizationIdAndDateServiceBefore(int organizationId, LocalDateTime dateService);
    List<ServiceRequest> findByCustomer(Customer customer);

    List<ServiceRequest> findByOrganization(Organization organization);

    List<ServiceRequest> findByOrganization_OrganizationId(int organizationId);


}

