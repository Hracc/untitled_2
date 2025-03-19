package com.agregator.Agregator.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Entity
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

    @Column( length = 50)
    private String customerSurname;

    @Column( length = 50)
    private String customerName;

    @Column(length = 50)
    private String customerPatronymic;

    @Column(nullable = false, length = 20)
    private String email;

    @Column(length = 250)
    private String addInfo;

}
