package com.agregator.Agregator.DTO;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRequestDTO {

    private String customerName;
    private String customerEmail;
    private LocalDate dateService;
    private String addInfo;
    private List<String> serviceDetails;
}
