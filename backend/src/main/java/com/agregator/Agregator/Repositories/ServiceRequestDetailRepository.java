package com.agregator.Agregator.Repositories;

import com.agregator.Agregator.Entity.ServiceDetail;
import com.agregator.Agregator.Entity.ServiceRequestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ServiceRequestDetailRepository extends JpaRepository<ServiceRequestDetail, Integer> {
    List<ServiceRequestDetail> findByServiceRequest_ServiceRequestId(int serviceRequestId);
    // Найти все ServiceRequestDetail, связанные с определенным ServiceDetail
    List<ServiceRequestDetail> findByServiceDetailIn(List<ServiceDetail> serviceDetails);
    List<ServiceRequestDetail> findByServiceDetail(ServiceDetail serviceDetail);
}

