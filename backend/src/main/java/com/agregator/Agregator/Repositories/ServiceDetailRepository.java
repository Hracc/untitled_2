package com.agregator.Agregator.Repositories;

import com.agregator.Agregator.Entity.ServiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceDetailRepository extends JpaRepository<ServiceDetail, Integer> {
    List<ServiceDetail> findByServiceTypeTypeCode(String typeCode);
    Optional<ServiceDetail> findById(int id);
}
