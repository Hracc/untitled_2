package com.agregator.Agregator.Repositories;

import com.agregator.Agregator.Entity.Customer;
import com.agregator.Agregator.Entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    Optional<Organization> findByResponsiblePersonEmail(String email);
}
