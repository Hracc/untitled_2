package com.agregator.Agregator.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrganizationDTO {
    private String organizationFullName;
    private String organizationShortName;
    private String inn;
    private String kpp;
    private String ogrn;
    private String responsiblePersonSurname;
    private String responsiblePersonName;
    private String responsiblePersonPatronymic;
    private String responsiblePersonEmail;
    private String responsiblePersonPhoneNumber;
    private String addInfo;
}
