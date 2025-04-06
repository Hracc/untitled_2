package com.agregator.Agregator.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequestForEmailDTO {
    private String customerName;
    private String customerEmail;
    private LocalDateTime dateService;
    private String addInfo;
    private List<ServiceDetailForEmailDTO> serviceDetails;
}
