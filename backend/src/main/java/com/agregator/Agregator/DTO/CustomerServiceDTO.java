package com.agregator.Agregator.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomerServiceDTO extends ServiceDetailWithtTypeDTO{

    private String name;
    private String email;
}
