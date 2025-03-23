package com.agregator.Agregator.Repositories;

import com.agregator.Agregator.Entity.AddressType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressTypeRepository extends JpaRepository<AddressType, Integer> {
}
