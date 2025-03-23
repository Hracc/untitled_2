package com.agregator.Agregator.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Entity
@Getter
@Setter
public class AggregatorSpecialist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aggregatorSpecialistsId;

    @Column(nullable = false, length = 50)
    private String aggregatorSpecialistSurname;

    @Column(nullable = false, length = 50)
    private String aggregatorSpecialistName;

    @Column(length = 50)
    private String aggregatorSpecialistPatronymic;

    @Column(nullable = false, length = 30)
    private String aggregatorSpecialistsDepartment;

    @Column(nullable = false, length = 20)
    private String aggregatorSpecialistsPosition;

    @Column(nullable = false, unique = true)
    private String aggregatorSpecialistsEmail;

    @Column(length = 250)
    private String addInfo;
}
