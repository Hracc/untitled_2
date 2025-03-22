package com.agregator.Agregator.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustumerDTO {
    private String customerSurname;

    private String customerName;

    private String customerPatronymic;

    private String email;

    private String addInfo;
}
