package com.agregator.Agregator.Services;

import com.agregator.Agregator.DTO.OrganizationDTO;
import com.agregator.Agregator.Entity.*;
import com.agregator.Agregator.Repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrganizationService {
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;
    @Autowired
    private ServiceRequestDetailRepository serviceRequestDetailRepository;
    @Autowired
    private UserRepository userRepository;

    // CRUD по организациям
    public List<OrganizationDTO> getAllOrganizations() {
        List<Organization> organizations = organizationRepository.findAll();
        return organizations.stream().map(this::convertToDTO).toList();
    }

    public Organization getOrganizationById(int id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Организация не найдена"));
    }
    @Transactional
    public Organization createOrganization(OrganizationDTO organization) {
        return registrationService.registerOrganization(organization);
    }
    @Transactional
    public OrganizationDTO updateOrganization(int id, OrganizationDTO orgDetails) {
        Organization organization = getOrganizationById(id);
        if(!orgDetails.getOrganizationFullName().isEmpty()) {
            organization.setOrganizationFullName(orgDetails.getOrganizationFullName());
        }
        if(!orgDetails.getOgrn().isEmpty()) {
            organization.setOgrn(orgDetails.getOgrn());
        }
        if(!orgDetails.getAddInfo().isEmpty()) {
            organization.setAddInfo(orgDetails.getAddInfo());
        }
        if(!orgDetails.getInn().isEmpty()) {
            organization.setInn(orgDetails.getInn());
        }
        if(!orgDetails.getKpp().isEmpty()) {
            organization.setKpp(orgDetails.getKpp());
        }
        if(!orgDetails.getOrganizationShortName().isEmpty()) {
            organization.setOrganizationShortName(orgDetails.getOrganizationShortName());
        }
        if(!orgDetails.getResponsiblePersonPatronymic().isEmpty()) {
            organization.setResponsiblePersonPatronymic(orgDetails.getResponsiblePersonPatronymic());
        }
        if(!orgDetails.getResponsiblePersonEmail().isEmpty()){
            organization.setResponsiblePersonEmail(orgDetails.getResponsiblePersonEmail());
        }
        if(!orgDetails.getResponsiblePersonName().isEmpty()){
            organization.setResponsiblePersonName(orgDetails.getResponsiblePersonName());
        }
        if(!orgDetails.getResponsiblePersonPhoneNumber().isEmpty()){
            organization.setResponsiblePersonPhoneNumber(orgDetails.getResponsiblePersonPhoneNumber());
        }
        if(!orgDetails.getResponsiblePersonSurname().isEmpty()){
            organization.setResponsiblePersonSurname(orgDetails.getResponsiblePersonSurname());
        }
        organizationRepository.save(organization);
        orgDetails.setId(id);
        return orgDetails;
    }
    @Transactional
    public void deleteOrganization(int id) {
        Optional<Organization> OrganizationOpt = organizationRepository.findById(id);
        if (OrganizationOpt.isEmpty()) {
            log.error("Организация не найдена по id: {}", id);
            throw new RuntimeException("Организация не найдена");
        }

        List<Address> addresses = addressRepository.findByOrganization_OrganizationId(OrganizationOpt.get().getOrganizationId());
        for (Address address : addresses) {
            addressRepository.delete(address);
            log.info("Адрес с id {} удален", address.getAddressId());
        }

        // Обновляем все записи в ServiceRequest, связанные с этим клиентом
        List<ServiceRequest> serviceRequests = serviceRequestRepository.findByOrganization(OrganizationOpt.get());
        if (!serviceRequests.isEmpty()) {
            // Обновляем customerId на NULL или на нового клиента (зависит от бизнес-логики)
            for (ServiceRequest serviceRequest : serviceRequests) {
                List<ServiceRequestDetail> serviceRequestDetails = serviceRequestDetailRepository.findByServiceRequest_ServiceRequestId(serviceRequest.getServiceRequestId());
                for (ServiceRequestDetail server : serviceRequestDetails){
                    serviceRequestDetailRepository.delete(server);
                }
                serviceRequestRepository.delete(serviceRequest);
            }
            log.info("Запросы обслуживания для Организации {} удалены", OrganizationOpt.get().getOrganizationFullName());
        }
        // Также можно удалить пользователя из users, если он есть
        userRepository.findByEmail(OrganizationOpt.get().getOrganizationFullName()).ifPresent(user -> {
            userRepository.delete(user);
            log.info("Пользователь {} удален", OrganizationOpt.get().getOrganizationFullName());
        });



        // Удаляем Customer
        organizationRepository.delete(OrganizationOpt.get());
        log.info("Организация {} удалена", OrganizationOpt.get().getOrganizationFullName());


        organizationRepository.deleteById(id);
    }

    // Получить все адреса по организации
    public List<Address> getAddressesByOrganization(int organizationId) {
        return addressRepository.findByOrganization_OrganizationId(organizationId);
    }


    public Address addAddressToOrganization(int organizationId, Address address) {
        Organization organization = getOrganizationById(organizationId);
        address.setOrganization(organization);
        return addressRepository.save(address);
    }
    @Transactional
    public void deleteAddress(int addressId) {
        addressRepository.deleteById(addressId);
    }

    private OrganizationDTO convertToDTO(Organization organization) {
        OrganizationDTO dto = new OrganizationDTO();
        dto.setId(organization.getOrganizationId());
        dto.setOrganizationFullName(organization.getOrganizationFullName());
        dto.setOrganizationShortName(organization.getOrganizationShortName());
        dto.setInn(organization.getInn());
        dto.setKpp(organization.getKpp());
        dto.setOgrn(organization.getOgrn());
        dto.setResponsiblePersonSurname(organization.getResponsiblePersonSurname());
        dto.setResponsiblePersonName(organization.getResponsiblePersonName());
        dto.setResponsiblePersonPatronymic(organization.getResponsiblePersonPatronymic());
        dto.setResponsiblePersonEmail(organization.getResponsiblePersonEmail());
        dto.setResponsiblePersonPhoneNumber(organization.getResponsiblePersonPhoneNumber());
        dto.setAddInfo(organization.getAddInfo());
        return dto;
    }

  //Другое
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
