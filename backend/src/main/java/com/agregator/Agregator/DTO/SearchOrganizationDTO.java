package com.agregator.Agregator.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchOrganizationDTO{

    private int organizationId;
    private String cityName;
    private String organizationFullName;
    private String streetName;
    private String houseNumber;

}
