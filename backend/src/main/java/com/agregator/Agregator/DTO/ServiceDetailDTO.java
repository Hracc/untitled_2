package com.agregator.Agregator.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDetailDTO {
    private int serviceDetailId;
    private String serviceDetailCode;
    private String serviceDetailName;
    private int serviceDetailCost;
    private int serviceDetailDuration;
}
