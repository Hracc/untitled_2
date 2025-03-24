package com.agregator.Agregator.Repositories;

import com.agregator.Agregator.Entity.ServiceRequestDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRequestDetailRepository extends JpaRepository<ServiceRequestDetail, Integer> {
    List<ServiceRequestDetail> findByServiceRequest_ServiceRequestId(int serviceRequestId);
}

