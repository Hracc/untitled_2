package com.agregator.Agregator.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Customer")
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer id;

    @Column(name = "customer_surname", length = 50)
    private String surname;

    @Column(name = "customer_name", length = 50)
    private String name;

    @Column(name = "customer_patronymic", length = 50)
    private String patronymic;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "add_info", length = 250)
    private String info;
}
