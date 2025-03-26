package com.agregator.Agregator.Utilis;

import com.agregator.Agregator.Entity.*;
import com.agregator.Agregator.Enums.AddressTypeEnum;
import com.agregator.Agregator.Repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final OrganizationRepository organizationRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final ServiceDetailRepository serviceDetailRepository;
    private final ServiceRequestRepository serviceRequestRepository;
    private final ServiceRequestDetailRepository serviceRequestDetailRepository;
    private final AddressRepository addressRepository;
    private final AddressTypeRepository addressTypeRepository;
    private final CustomerRepositiry customerRepositiry;

    public DataInitializer(OrganizationRepository organizationRepository,
                           ServiceTypeRepository serviceTypeRepository,
                           ServiceDetailRepository serviceDetailRepository,
                           ServiceRequestRepository serviceRequestRepository,
                           ServiceRequestDetailRepository serviceRequestDetailRepository, AddressRepository addressRepository, AddressTypeRepository addressTypeRepository, CustomerRepositiry customerRepositiry) {
        this.organizationRepository = organizationRepository;
        this.serviceTypeRepository = serviceTypeRepository;
        this.serviceDetailRepository = serviceDetailRepository;
        this.serviceRequestRepository = serviceRequestRepository;
        this.serviceRequestDetailRepository = serviceRequestDetailRepository;
        this.addressRepository = addressRepository;
        this.addressTypeRepository = addressTypeRepository;
        this.customerRepositiry = customerRepositiry;
    }

    @Override
    public void run(String... args) throws Exception {
        // Добавление данных в таблицу Custumer
        Customer customer = new Customer();
        customer.setCustomerName("John");
        customer.setCustomerSurname("Doe");
        customer.setEmail("kir@yandex.ru");

        customerRepositiry.save(customer);

        // Добавление данных в таблицу AddressType
        AddressType addressType = new AddressType();
        addressType.setAddressTypeName(AddressTypeEnum.Физический.name());
        addressType.setAddInfo("главное место");
        addressTypeRepository.save(addressType);

        // Добавление данных в таблицу ServiceType
        ServiceType serviceType = new ServiceType();
        serviceType.setTypeCode("01");
        serviceType.setTypeName("Мойка");
        serviceTypeRepository.save(serviceType);


        // Добавление данных в таблицу Organization
        Organization organization = new Organization();
        organization.setOrganizationFullName("Company XYZ");
        organization.setOrganizationShortName("XYZ");
        organization.setInn("1234567890");
        organization.setKpp("987654321");
        organization.setOgrn("1234567890123");
        organization.setResponsiblePersonSurname("Doe");
        organization.setResponsiblePersonName("John");
        organization.setResponsiblePersonPatronymic("Smith");
        organization.setResponsiblePersonEmail("john.doe@example.com");
        organization.setResponsiblePersonPhoneNumber("1234567890");
        organization.setAddInfo("Additional info about company XYZ");
        organizationRepository.save(organization);

        // Добавление данных в таблицу Address
        Address address = new Address();
        address.setOrganization(organization);
        address.setAddressType(addressType);
        address.setSubjectName("Company XYZ");
        address.setCityName("Moscow");
        address.setStreetName("Tverskaya");
        address.setHouseNumber("10");
        address.setAddInfo("Additional address info");
        addressRepository.save(address);

        // Добавление данных в таблицу ServiceDetail
        ServiceDetail serviceDetail = new ServiceDetail();
        serviceDetail.setServiceType(serviceType);
        serviceDetail.setServiceDetailCode("001");
        serviceDetail.setServiceDetailName("Standard Cleaning");
        serviceDetail.setServiceDetailCost(1000);
        serviceDetail.setServiceDetailDuration(60);
        serviceDetail.setAddInfo("Standard cleaning service");
        serviceDetailRepository.save(serviceDetail);

        // Создание ServiceRequest
        ServiceRequest serviceRequest = new ServiceRequest();
        serviceRequest.setCustomer(customer);  // Вставьте реального клиента
        serviceRequest.setOrganization(organization);
        serviceRequest.setDateService(LocalDateTime.now());
        serviceRequest.setAddInfo("Request for cleaning service");
        serviceRequestRepository.save(serviceRequest);

        // Создание ServiceRequestDetail
        ServiceRequestDetail serviceRequestDetail = new ServiceRequestDetail();
        serviceRequestDetail.setServiceRequest(serviceRequest);
        serviceRequestDetail.setServiceDetail(serviceDetail);
        serviceRequestDetailRepository.save(serviceRequestDetail);


        System.out.println("Database has been initialized!");
    }
}

