//
//package com.agregator.Agregator.Utilis;
//
//import com.agregator.Agregator.Entity.*;
//import com.agregator.Agregator.Enums.AddressTypeEnum;
//import com.agregator.Agregator.Enums.UserRole;
//import com.agregator.Agregator.Repositories.*;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//
//    private final OrganizationRepository organizationRepository;
//    private final ServiceTypeRepository serviceTypeRepository;
//    private final ServiceDetailRepository serviceDetailRepository;
//    private final ServiceRequestRepository serviceRequestRepository;
//    private final ServiceRequestDetailRepository serviceRequestDetailRepository;
//    private final AddressRepository addressRepository;
//    private final AddressTypeRepository addressTypeRepository;
//    private final CustomerRepository customerRepositiry;
//    private final AggregatorSpecialistRepository aggregatorSpecialistRepository;
//    private final AggregatorSpecialistConnectorRequestRepository aggregatorSpecialistConnectorRequestRepository;
//    private final ConnectionRequestRepository connectionRequestRepository;
//    private final UserRepository userRepository;
//    private final ConnectionRequest connectionRequest;
//
//    public DataInitializer(OrganizationRepository organizationRepository,
//                           ServiceTypeRepository serviceTypeRepository,
//                           ServiceDetailRepository serviceDetailRepository,
//                           ServiceRequestRepository serviceRequestRepository,
//                           ServiceRequestDetailRepository serviceRequestDetailRepository,
//                           AddressRepository addressRepository,
//                           AddressTypeRepository addressTypeRepository,
//                           CustomerRepository customerRepositiry,
//                           AggregatorSpecialistRepository aggregatorSpecialistRepository,
//                           AggregatorSpecialistConnectorRequestRepository aggregatorSpecialistConnectorRequestRepository,
//                           ConnectionRequestRepository connectionRequestRepository, UserRepository userRepository, ConnectionRequest connectionRequest) {
//        this.organizationRepository = organizationRepository;
//        this.serviceTypeRepository = serviceTypeRepository;
//        this.serviceDetailRepository = serviceDetailRepository;
//        this.serviceRequestRepository = serviceRequestRepository;
//        this.serviceRequestDetailRepository = serviceRequestDetailRepository;
//        this.addressRepository = addressRepository;
//        this.addressTypeRepository = addressTypeRepository;
//        this.customerRepositiry = customerRepositiry;
//        this.aggregatorSpecialistRepository = aggregatorSpecialistRepository;
//        this.aggregatorSpecialistConnectorRequestRepository = aggregatorSpecialistConnectorRequestRepository;
//        this.connectionRequestRepository = connectionRequestRepository;
//        this.userRepository = userRepository;
//        this.connectionRequest = connectionRequest;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        // Добавление данных в таблицу Custumer
//        Customer customer = new Customer();
//        customer.setCustomerName("John");
//        customer.setCustomerSurname("Doe");
//        customer.setEmail("kir@yandex.ru");
//
//        customerRepositiry.save(customer);
//
//        // Добавление данных в таблицу AddressType
//        AddressType addressType = new AddressType();
//        addressType.setAddressTypeName(AddressTypeEnum.Физический.name());
//        addressType.setAddInfo("главное место");
//        addressTypeRepository.save(addressType);
//
//        // Добавление данных в таблицу ServiceType
//        ServiceType serviceType = new ServiceType();
//        serviceType.setTypeCode("01");
//        serviceType.setTypeName("Мойка");
//        serviceTypeRepository.save(serviceType);
//
//
//        // Добавление данных в таблицу Organization
//        Organization organization = new Organization();
//        organization.setOrganizationFullName("Company XYZ");
//        organization.setOrganizationShortName("XYZ");
//        organization.setInn("1234567890");
//        organization.setKpp("987654321");
//        organization.setOgrn("1234567890123");
//        organization.setResponsiblePersonSurname("Doe");
//        organization.setResponsiblePersonName("John");
//        organization.setResponsiblePersonPatronymic("Smith");
//        organization.setResponsiblePersonEmail("john.doe@example.com");
//        organization.setResponsiblePersonPhoneNumber("1234567890");
//        organization.setAddInfo("Additional info about company XYZ");
//        organizationRepository.save(organization);
//
//        ConnectionRequest connectionRequest = new ConnectionRequest();
//        connectionRequest.setOrganization(organization);
//        connectionRequest.setRegNumber("ABC123"); // Уникальный регистрационный номер
//        connectionRequest.setDateBegin(LocalDate.now()); // Начало даты
//        connectionRequest.setDateEnd(LocalDate.now().plusDays(30)); // Пример окончания через 30 дней
//        connectionRequest.setStatus("Новый"); // Статус запроса
//        connectionRequest.setAddInfo("Дополнительная информация по запросу");
//
//        connectionRequestRepository.save(connectionRequest);
//
//        User user = new User();
//        user.setEmail(organization.getResponsiblePersonEmail());
//        user.setRole(UserRole.ORGANIZATION);
//        userRepository.save(user);
//
//        // Добавление данных в таблицу Address
//        Address address = new Address();
//        address.setOrganization(organization);
//        address.setAddressType(addressType);
//        address.setSubjectName("Company XYZ");
//        address.setCityName("Moscow");
//        address.setStreetName("Tverskaya");
//        address.setHouseNumber("10");
//        address.setAddInfo("Additional address info");
//        addressRepository.save(address);
//
//        // Добавление данных в таблицу ServiceDetail
//        ServiceDetail serviceDetail = new ServiceDetail();
//        serviceDetail.setServiceType(serviceType);
//        serviceDetail.setServiceDetailCode("001");
//        serviceDetail.setServiceDetailName("Standard Cleaning");
//        serviceDetail.setServiceDetailCost(1000);
//        serviceDetail.setServiceDetailDuration(60);
//        serviceDetail.setAddInfo("Standard cleaning service");
//        serviceDetailRepository.save(serviceDetail);
//
//        // Создание ServiceRequest
//        ServiceRequest serviceRequest = new ServiceRequest();
//        serviceRequest.setCustomer(customer);  // Вставьте реального клиента
//        serviceRequest.setOrganization(organization);
//        serviceRequest.setDateService(LocalDateTime.now());
//        serviceRequest.setAddInfo("Request for cleaning service");
//        serviceRequestRepository.save(serviceRequest);
//
//        // Создание ServiceRequestDetail
//        ServiceRequestDetail serviceRequestDetail = new ServiceRequestDetail();
//        serviceRequestDetail.setServiceRequest(serviceRequest);
//        serviceRequestDetail.setServiceDetail(serviceDetail);
//        serviceRequestDetailRepository.save(serviceRequestDetail);
//
//
//        System.out.println("Database has been initialized!");
//    }
//
//    private AddressType createAddressType(String typeName, String addInfo) {
//        AddressType addressType = new AddressType();
//        addressType.setAddressTypeName(typeName);
//        addressType.setAddInfo(addInfo);
//        return addressTypeRepository.save(addressType);
//    }
//
//    private Customer createCustomer (String name, String surname, String patronymic, String email) {
//        Customer customer = new Customer();
//        customer.setCustomerName(name);
//        customer.setCustomerSurname(surname);
//        customer.setCustomerPatronymic(patronymic);
//        customer.setEmail(email);
//        return customerRepositiry.save(customer);
//    }
//
//    private ServiceType createServiceType (String typeCode, String typeName) {
//        ServiceType serviceType = new ServiceType();
//        serviceType.setTypeCode(typeCode);
//        serviceType.setTypeName(typeName);
//        return serviceTypeRepository.save(serviceType);
//    }
//
//    private Organization createOrganization (String fullName, String shortName, String inn,
//                                             String kpp, String ogrn, String personSurname,
//                                             String personName, String personPatronymic, String email,
//                                             String phone, String addInfo) {
//        Organization organization = new Organization();
//        organization.setOrganizationFullName(fullName);
//        organization.setOrganizationShortName(shortName);
//        organization.setInn(inn);
//        organization.setKpp(kpp);
//        organization.setOgrn(ogrn);
//        organization.setResponsiblePersonSurname(personSurname);
//        organization.setResponsiblePersonName(personName);
//        organization.setResponsiblePersonPatronymic(personPatronymic);
//        organization.setResponsiblePersonEmail(email);
//        organization.setResponsiblePersonPhoneNumber(phone);
//        organization.setAddInfo(addInfo);
//        return organizationRepository.save(organization);
//    }
//
//    private Address createAddress (Organization organization, AddressType addressType, String subjectName,
//                                   String cityName, String streetName, String houseNumber, String addInfo) {
//        Address address = new Address();
//        address.setOrganization(organization);
//        address.setAddressType(addressType);
//        address.setSubjectName(subjectName);
//        address.setCityName(cityName);
//        address.setStreetName(streetName);
//        address.setHouseNumber(houseNumber);
//        address.setAddInfo(addInfo);
//        return addressRepository.save(address);
//    }
//
//    private ServiceDetail createServiceDetail (ServiceType serviceType, String code, String name, int cost, int duration, String addInfo) {
//        ServiceDetail serviceDetail = new ServiceDetail();
//        serviceDetail.setServiceType(serviceType);
//        serviceDetail.setServiceDetailCode(code);
//        serviceDetail.setServiceDetailName(name);
//        serviceDetail.setServiceDetailCost(cost);
//        serviceDetail.setServiceDetailDuration(duration);
//        serviceDetail.setAddInfo(addInfo);
//        return serviceDetailRepository.save(serviceDetail);
//    }
//
//    private ServiceRequestDetail createServiceRequestDetail (ServiceRequest serviceRequest, ServiceDetail serviceDetail) {
//        ServiceRequestDetail serviceRequestDetail = new ServiceRequestDetail();
//        serviceRequestDetail.setServiceRequest(serviceRequest);
//        serviceRequestDetail.setServiceDetail(serviceDetail);
//        return serviceRequestDetailRepository.save(serviceRequestDetail);
//    }
//
//    private ServiceRequest createServiceRequest (Customer customer, Organization organization, LocalDateTime date, String addInfo) {
//        ServiceRequest serviceRequest = new ServiceRequest();
//        serviceRequest.setCustomer(customer);
//        serviceRequest.setOrganization(organization);
//        serviceRequest.setDateService(date);
//        serviceRequest.setAddInfo(addInfo);
//        return serviceRequestRepository.save(serviceRequest);
//    }
//
//    private AggregatorSpecialist createAggregatorSpecialist (String surname, String name, String patronymic, String department, String position, String email, String addInfo) {
//        AggregatorSpecialist aggregatorSpecialist = new AggregatorSpecialist();
//        aggregatorSpecialist.setAggregatorSpecialistSurname(surname);
//        aggregatorSpecialist.setAggregatorSpecialistName(name);
//        aggregatorSpecialist.setAggregatorSpecialistPatronymic(patronymic);
//        aggregatorSpecialist.setAggregatorSpecialistsDepartment(department);
//        aggregatorSpecialist.setAggregatorSpecialistsPosition(position);
//        aggregatorSpecialist.setAggregatorSpecialistsEmail(email);
//        aggregatorSpecialist.setAddInfo(addInfo);
//        return aggregatorSpecialistRepository.save(aggregatorSpecialist);
//    }
//
//    private AggregatorSpecialistConnectorRequest createAggregatorSpecialistConnectorRequest (AggregatorSpecialist aggregatorSpecialist, ConnectionRequest connectionRequest) {
//        AggregatorSpecialistConnectorRequest aggregatorSpecialistConnectorRequest = new AggregatorSpecialistConnectorRequest();
//        aggregatorSpecialistConnectorRequest.setAggregatorSpecialist(aggregatorSpecialist);
//        aggregatorSpecialistConnectorRequest.setConnectionRequest(connectionRequest);
//        return aggregatorSpecialistConnectorRequestRepository.save(aggregatorSpecialistConnectorRequest);
//    }
//
//    private ConnectionRequest createConnectionRequest (Organization organization, String regNumber, LocalDate dateBegin, LocalDate dateEnd, String status, String addInfo ) {
//        ConnectionRequest connectionRequest = new ConnectionRequest();
//        connectionRequest.setOrganization(organization);
//        connectionRequest.setRegNumber(regNumber);
//        connectionRequest.setDateBegin(dateBegin);
//        connectionRequest.setDateEnd(dateEnd);
//        connectionRequest.setStatus(status);
//        connectionRequest.setAddInfo(addInfo);
//        return connectionRequestRepository.save(connectionRequest);
//    }
//}
//
//
