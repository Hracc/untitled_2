package com.agregator.Agregator.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Address")
@Getter
@Setter
public class Address {
    @EmbeddedId
    private AddressId id;

    // Связь с организацией. organization_id уже является частью составного ключа,
    // поэтому здесь используется insertable = false, updatable = false
    //@ManyToOne
   // @JoinColumn(name = "organization_id", insertable = false, updatable = false)
    //private Organization organization;
    @ManyToOne
    @JoinColumn(name = "address_type_id", referencedColumnName = "address_type_id", insertable = false, updatable = false)
    private AddressType addressType;

    @Column(name = "subject_name", length = 50)
    private String subjectName;

    @Column(name = "city_name", length = 50)
    private String cityName;

    @Column(name = "street_name", length = 50)
    private String streetName;

    @Column(name = "house_number", length = 10)
    private String houseNumber;

    @Column(name = "add_info", length = 250)
    private String additionalInfo;
}

