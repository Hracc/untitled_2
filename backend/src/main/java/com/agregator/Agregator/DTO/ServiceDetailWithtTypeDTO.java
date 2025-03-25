package com.agregator.Agregator.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceDetailWithtTypeDTO{
    private Integer serviceDetailId;
    private Integer serviceTypeId;
    private String serviceDetailCode;
    private String serviceDetailName;
    private int serviceDetailCost;
    private int serviceDetailDuration;
    private String addInfo;
}
