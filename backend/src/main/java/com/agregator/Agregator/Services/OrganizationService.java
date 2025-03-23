package com.agregator.Agregator.Services;

import com.agregator.Agregator.DTO.OrganizationDTO;
import com.agregator.Agregator.Entity.Organization;
import com.agregator.Agregator.Repositories.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public void create(OrganizationDTO dto) {
        Organization organization = new Organization();
        organization.setOrganizationFullName(dto.getOrganizationFullName());
        organization.setOrganizationShortName(dto.getOrganizationShortName());
        organization.setInn(dto.getInn());
        organization.setKpp(dto.getKpp());
        organization.setOgrn(dto.getOgrn());
        organization.setResponsiblePersonSurname(dto.getResponsiblePersonSurname());
        organization.setResponsiblePersonName(dto.getResponsiblePersonName());
        organization.setResponsiblePersonPatronymic(dto.getResponsiblePersonPatronymic());
        organization.setResponsiblePersonEmail(dto.getResponsiblePersonEmail());
        organization.setResponsiblePersonPhoneNumber(dto.getResponsiblePersonPhoneNumber());
        organization.setAddInfo(dto.getAddInfo());
        organizationRepository.save(organization);
    }

}
