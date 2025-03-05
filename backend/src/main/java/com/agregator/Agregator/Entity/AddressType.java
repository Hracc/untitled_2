package com.agregator.Agregator.Entity;

import com.agregator.Agregator.Enums.AddressTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Address_type")
@Getter
@Setter
public class AddressType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_type_id")
    private Integer id;

    @Column(name = "address_type_name", length = 30, nullable = false)
    private AddressTypeEnum type;

    @Column(name = "add_info",length = 250)
    private String addinfo;
}
