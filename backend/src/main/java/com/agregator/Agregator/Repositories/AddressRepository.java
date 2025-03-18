package com.agregator.Agregator.Repositories;

import com.agregator.Agregator.Entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Query("SELECT a FROM Address a WHERE LOWER(a.cityName) LIKE LOWER(CONCAT('%', :cityName, '%'))")
    List<Address> findByCityName(@Param("cityName") String cityName);

    @Query("SELECT a FROM Address a " +
            "WHERE LOWER(a.cityName) LIKE LOWER(CONCAT('%', :cityName, '%')) " +
            "AND LOWER(a.organization.organizationFullName) LIKE LOWER(CONCAT('%', :organizationName, '%'))")
    List<Address> findByCityAndOrganizationName(@Param("cityName") String cityName, @Param("organizationName") String organizationName);
}
