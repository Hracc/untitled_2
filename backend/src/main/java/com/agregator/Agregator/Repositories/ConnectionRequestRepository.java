package com.agregator.Agregator.Repositories;

import com.agregator.Agregator.Entity.ConnectionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectionRequestRepository extends JpaRepository<ConnectionRequest, Integer> {

    List<ConnectionRequest> findByOrganization_OrganizationId(int organizationId);

    List<ConnectionRequest> findByStatus(String status);

    ConnectionRequest findTopByOrganization_OrganizationIdOrderByDateEndDesc(int organizationId);

    @Query("SELECT req FROM ConnectionRequest req JOIN FETCH req.organization WHERE req.status = :status")
    List<ConnectionRequest> findByStatusWithOrganization(@Param("status") String status);

    @Query("SELECT req FROM ConnectionRequest req WHERE req.organization.organizationId = :organizationId ORDER BY req.dateBegin DESC")
    Optional<ConnectionRequest> findLatestConnectionRequestByOrganization(@Param("organizationId") int organizationId);
}
