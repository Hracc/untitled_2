package com.agregator.Agregator.Repositories;

import com.agregator.Agregator.Entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
}
