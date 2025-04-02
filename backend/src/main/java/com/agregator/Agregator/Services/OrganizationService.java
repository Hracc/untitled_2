package com.agregator.Agregator.Services;

import com.agregator.Agregator.DTO.ConnectionRequestDTO;
import com.agregator.Agregator.DTO.CreateOrganizationDTO;
import com.agregator.Agregator.DTO.OrganizationDTO;
import com.agregator.Agregator.DTO.ServiceRequestDTO;
import com.agregator.Agregator.Entity.*;
import com.agregator.Agregator.Repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    @Autowired
    private ConnectionRequestRepository connectionRequestRepository;

    public ResponseEntity<OrganizationDTO> findCustomerByEmail(String email) {
        Optional<Organization> organization = organizationRepository.findByResponsiblePersonEmail(email);
        if (organization.isEmpty()){
            return ResponseEntity.notFound().build();
        }else {
            OrganizationDTO organizationDTO = convertToDTO(organization.get());
            return ResponseEntity.ok(organizationDTO);
        }
    }

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
    public ResponseEntity<?> createOrganization(CreateOrganizationDTO organization) {
        return registrationService.registerOrganization(organization);
    }
    @Transactional
    public ResponseEntity<?> updateOrganization(int id, OrganizationDTO orgDetails) {
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
            if (isValidEmail(orgDetails.getResponsiblePersonEmail())){
                if (!isEmailExist(orgDetails.getResponsiblePersonEmail())){
                    userRepository.findByEmail(organization.getResponsiblePersonEmail()).ifPresent(user -> {
                        user.setEmail(orgDetails.getResponsiblePersonEmail());
                        userRepository.save(user);
                    });
                    organization.setResponsiblePersonEmail(orgDetails.getResponsiblePersonEmail());

                }else {
                    return ResponseEntity.badRequest().body("Этот email уже занят");
                }
            }else {
                return ResponseEntity.badRequest().body("Email не правильного вида");
            }
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
        return ResponseEntity.ok(orgDetails);
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

    public List<ConnectionRequestDTO> UpdateStatus(String email) {
        Optional<Organization> organization = organizationRepository.findByResponsiblePersonEmail(email);

        if (organization.isEmpty()) {
            throw new RuntimeException("Такая организация не найдена");
        } else {
            // Получаем список запросов на подключение
            List<ConnectionRequest> requests = connectionRequestRepository.findByOrganization_OrganizationId(organization.get().getOrganizationId());

            return requests.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
    }

    private ConnectionRequestDTO convertToDTO(ConnectionRequest connectionRequest) {
        // Маппинг из ConnectionRequest в ConnectionRequestDTO
        return new ConnectionRequestDTO(
                connectionRequest.getRegNumber(),
                connectionRequest.getDateBegin(),
                connectionRequest.getDateEnd(),
                connectionRequest.getStatus(),
                connectionRequest.getAddInfo()
        );
    }

    public ResponseEntity<List<ServiceRequestDTO>> getServiceRequestsForOrganization(String email) {
        // Находим организацию по email
        int organizationId = organizationRepository.findByResponsiblePersonEmail(email)
                .orElseThrow().getOrganizationId();

        // Получаем все заявки для организации
        List<ServiceRequest> requests = serviceRequestRepository.findByOrganization_OrganizationId(organizationId);

        // Маппируем заявки в DTO
        List<ServiceRequestDTO> dtoList = requests.stream()
                .map(r -> new ServiceRequestDTO(
                        r.getCustomer().getCustomerName(),
                        r.getCustomer().getEmail(),
                        r.getDateService().toLocalDate(),
                        r.getAddInfo(),
                        getServiceDetailsForRequest(r.getServiceRequestId())
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    private List<String> getServiceDetailsForRequest(int serviceRequestId) {
        // Получаем все детали услуги для конкретной заявки
        List<ServiceRequestDetail> requestDetails = serviceRequestDetailRepository.findByServiceRequest_ServiceRequestId(serviceRequestId);

        // Возвращаем список имен выбранных услуг
        return requestDetails.stream()
                .map(srd -> srd.getServiceDetail().getServiceDetailName())
                .collect(Collectors.toList());
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

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    private boolean isEmailExist(String email){
        if(userRepository.findByEmail(email).isPresent()){
            return true;
        }else {
            return false;
        }
    }
}
