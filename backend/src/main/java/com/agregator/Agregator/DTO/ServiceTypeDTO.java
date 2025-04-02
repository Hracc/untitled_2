package com.agregator.Agregator.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceTypeDTO {
    private Integer typeId;  // Для обновления
    private String typeCode;
    private String typeName;
}

