package com.agregator.Agregator.Repositories;

import com.agregator.Agregator.Entity.ServiceDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ServiceDetailRepository extends JpaRepository<ServiceDetail, Integer> {

    List<ServiceDetail> findByServiceTypeTypeCode(String typeCode);

    Optional<ServiceDetail> findById(int id);

    List<ServiceDetail> findByServiceType_TypeId(int typeId);
}
