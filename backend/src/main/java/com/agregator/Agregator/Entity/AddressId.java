package com.agregator.Agregator.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
@Getter
@Setter
public class AddressId implements Serializable {
    @Column(name = "organization_id")
    private Integer organizationId;

    @Column(name = "address_id")
    private Integer addressId;

    @Column(name = "address_type_id")  // Добавь, если это часть ключа
    private Integer addressTypeId;
}
