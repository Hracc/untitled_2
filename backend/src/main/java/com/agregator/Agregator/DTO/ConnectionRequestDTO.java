package com.agregator.Agregator.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ConnectionRequestDTO {
    private String RegNumber;
    private LocalDate dateBegin;
    private LocalDate dateEnd;
    private String status;
    private String addInfo;
}
