package com.agregator.Agregator.Repositories;

import com.agregator.Agregator.Entity.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Query("SELECT a FROM Address a WHERE LOWER(a.cityName) LIKE LOWER(CONCAT('%', :cityName, '%'))")
    List<Address> findByCityName(@Param("cityName") String cityName);

    @Query("SELECT a FROM Address a " +
            "WHERE LOWER(a.cityName) LIKE LOWER(CONCAT('%', :cityName, '%')) " +
            "AND LOWER(a.organization.organizationFullName) LIKE LOWER(CONCAT('%', :organizationName, '%'))")
    List<Address> findByCityAndOrganizationName(@Param("cityName") String cityName, @Param("organizationName") String organizationName);
}
