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
//                           ConnectionRequestRepository connectionRequestRepository, UserRepository userRepository) {
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
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//
//
//        /*User user = new User();
//        user.setEmail(organization.getResponsiblePersonEmail());
//        user.setRole(UserRole.ORGANIZATION);
//        userRepository.save(user);*/
//
//        User user1 = createUser("johhcallofduty@yandex.ru", UserRole.CUSTOMER);
//        User user2 = createUser("klr1234@mail.ru", UserRole.CUSTOMER);
//        User user3 = createUser("artrub@yandex.ru", UserRole.CUSTOMER);
//        User user4 = createUser("grisha007@gmail.com", UserRole.CUSTOMER);
//        User user5 = createUser("a.ivanov@fastrepair.ru", UserRole.ORGANIZATION);
//        User user6 = createUser("e.smirnova@translogistic.ru", UserRole.ORGANIZATION);
//        User user7 = createUser("d.kuznetsov@gruzoperevozki.ru", UserRole.ORGANIZATION);
//        User user8 = createUser("o.petrova@atc-plus.ru", UserRole.ORGANIZATION);
//        User user9 = createUser("a.sokolov@dorpatrul.ru", UserRole.ORGANIZATION);
//        User user10 = createUser("dlgrkv@mail.ru", UserRole.ADMINISTRATION);
//        User user11 = createUser("goro@mail.ru", UserRole.ADMINISTRATION);
//
//
//
//        // Добавление данных в таблицу Custumer
///*Customer customer = new Customer();
//        customer.setCustomerName("John");
//        customer.setCustomerSurname("Doe");
//        customer.setEmail("johhcallofduty@yandex.ru");
//
//        customerRepositiry.save(customer);*/
//
//        Customer customer1 = createCustomer("Джон", "Каллофдути", "", "johhcallofduty@yandex.ru" );
//        Customer customer2 = createCustomer("Кирилл", "Магомедов", "Александрович", "klr1234@mail.ru" );
//        Customer customer3 = createCustomer("Артем", "Рубченко", "Иванович", "artrub@yandex.ru" );
//        Customer customer4 = createCustomer("Григорий", "Рыбков", "Григорьевич", "grisha007@gmail.com" );
//
//
//
//        /*// Добавление данных в таблицу AddressType
//AddressType addressType = new AddressType();
//        addressType.setAddressTypeName(AddressTypeEnum.Физический.name());
//        addressType.setAddInfo("главное место");
//        addressTypeRepository.save(addressType);*/
//
//
//        AddressType addressType1 = createAddressType(AddressTypeEnum.Физический.name(), "");
//        AddressType addressType2 = createAddressType(AddressTypeEnum.Юридический.name(), "");
//
//        /*// Добавление данных в таблицу ServiceType
//ServiceType serviceType = new ServiceType();
//        serviceType.setTypeCode("01");
//        serviceType.setTypeName("Мойка");
//        serviceTypeRepository.save(serviceType);*/
//
//
//        ServiceType serviceType1 = createServiceType("01", "Мойка");
//        ServiceType serviceType2 = createServiceType("02", "Шиномонтаж");
//
//
///*        // Добавление данных в таблицу Organization
//Organization organization = new Organization();
//        organization.setOrganizationFullName("Company XYZ");
//        organization.setOrganizationShortName("XYZ");
//        organization.setInn("1234567890");
//        organization.setKpp("987654321");
//        organization.setOgrn("1234567890123");
//        organization.setResponsiblePersonSurname("Doe");
//        organization.setResponsiblePersonName("John");
//        organization.setResponsiblePersonPatronymic("Smith");
//        organization.setUser("john.doe@example.com");
//        organization.setResponsiblePersonPhoneNumber("1234567890");
//        organization.setAddInfo("Additional info about company XYZ");
//        organizationRepository.save(organization);*/
//
//
//        Organization org1 = createOrganization(
//                "Автосервис Быстрый Ремонт",
//                "Быстрый ремонт",
//                "7712345678",
//                "770101001",
//                "1127712345678",
//                "Иванов",
//                "Алексей",
//                "Петрович",
//                "a.ivanov@fastrepair.ru",
//                "+7 (495) 123-45-67",
//                "");
//        Organization org2 = createOrganization(
//                "ТрансЛогистик Групп",
//                "ТрансЛогистик",
//                "7723456789",
//                "772001002",
//                "1137723456789",
//                "Смирнова",
//                "Елена",
//                "Владимировна",
//                "e.smirnova@translogistic.ru",
//                "+7 (495) 234-56-78",
//                ""
//        );
//
//        Organization org3 = createOrganization(
//                "Грузоперевозки 24/7",
//                "ГрузоПеревозки",
//                "7734567890",
//                "773002003",
//                "1147734567890",
//                "Кузнецов",
//                "Дмитрий",
//                "Сергеевич",
//                "d.kuznetsov@gruzoperevozki.ru",
//                "+7 (495) 345-67-89",
//                ""
//        );
//
//        Organization org4 = createOrganization(
//                "АвтоТехСервис Плюс",
//                "АТС Плюс",
//                "7745678901",
//                "774003004",
//                "1157745678901",
//                "Петрова",
//                "Ольга",
//                "Игоревна",
//                "o.petrova@atc-plus.ru",
//                "+7 (495) 456-78-90",
//                ""
//        );
//
//        Organization org5 = createOrganization(
//                "Дорожный Патруль",
//                "ДорПатруль",
//                "7756789012",
//                "775004005",
//                "1167756789012",
//                "Соколов",
//                "Андрей",
//                "Александрович",
//                "a.sokolov@dorpatrul.ru",
//                "+7 (495) 567-89-01",
//                ""
//        );
//
///* ConnectionRequest connectionRequest = new ConnectionRequest();
//        connectionRequest.setOrganization(organization);
//        connectionRequest.setRegNumber("ABC123"); // Уникальный регистрационный номер
//        connectionRequest.setDateBegin(LocalDate.now()); // Начало даты
//        connectionRequest.setDateEnd(LocalDate.now().plusDays(30)); // Пример окончания через 30 дней
//        connectionRequest.setStatus("Новый"); // Статус запроса
//        connectionRequest.setAddInfo("Дополнительная информация по запросу");
//
//        connectionRequestRepository.save(connectionRequest);*/
//
//
//        ConnectionRequest cnnRq1 = createConnectionRequest(org1, "130122001", LocalDate.now(), LocalDate.now().plusDays(30), "Исполнена", "Доп. информация");
//        ConnectionRequest cnnRq2 = createConnectionRequest(org2, "211012002", LocalDate.now(), LocalDate.now().plusDays(15), "Исполнена", "Доп. информация");
//        createConnectionRequest(org3, "322012002", LocalDate.now(), null, "Новая", "Доп. информация");
//        createConnectionRequest(org4, "422012002", LocalDate.now(), null, "Новая", "Доп. информация");
//        createConnectionRequest(org5, "522012002", LocalDate.now(), null, "Новая", "Доп. информация");
//
//
//        // Добавление данных в таблицу Address
///*        Address address = new Address();
//        address.setOrganization(organization);
//        address.setAddressType(addressType);
//        address.setSubjectName("Company XYZ");
//        address.setCityName("Moscow");
//        address.setStreetName("Tverskaya");
//        address.setHouseNumber("10");
//        address.setAddInfo("Additional address info");
//        addressRepository.save(address);*/
//
//        createAddress(org1, addressType1, "г. Москва", "Москва", "Ленинградский проспект", "78", "Бизнес-центр 'Ленинградский', 3 этаж");
//        createAddress(org2, addressType1, "Московская область", "Химки", "Ленинградское шоссе", "15", "Логистический терминал №3");
//        createAddress(org3, addressType1, "Ленинградская область", "Всеволожск", "Центральная", "42", "Складской комплекс 'Балтика'");
//        createAddress(org4, addressType1, "Свердловская область", "Екатеринбург", "Машиностроителей", "19", "Автотехцентр, въезд с ул. Репина");
//        createAddress(org5, addressType1, "Республика Татарстан", "Казань", "Декабристов", "85Б", "БЦ 'Деловой', 4 этаж");
//
//        /*// Добавление данных в таблицу ServiceDetail
//        ServiceDetail serviceDetail = new ServiceDetail();
//        serviceDetail.setServiceType(serviceType);
//        serviceDetail.setServiceDetailCode("001");
//        serviceDetail.setServiceDetailName("Standard Cleaning");
//        serviceDetail.setServiceDetailCost(1000);
//        serviceDetail.setServiceDetailDuration(60);
//        serviceDetail.setAddInfo("Standard cleaning service");
//        serviceDetailRepository.save(serviceDetail);*/
//
//        ServiceDetail srvcDtl1 = createServiceDetail(serviceType1, "001", "Мойка днища", 1000, 20,  "Доп. информация");
//        ServiceDetail srvcDtl2 = createServiceDetail(serviceType1, "002", "Наномойка", 2300, 40,  "Доп. информация");
//        ServiceDetail srvcDtl3 = createServiceDetail(serviceType1, "003", "Мойка двигателя", 5500, 60,  "Доп. информация");
//
//        ServiceDetail srvcDtl4 = createServiceDetail(serviceType2, "010", "Сезонная замена шин", 5500, 30,  "Доп. информация");
//        ServiceDetail srvcDtl5 = createServiceDetail(serviceType2, "011", "Замена колеса", 3000, 60,  "Доп. информация");
//        ServiceDetail srvcDtl6 = createServiceDetail(serviceType2, "012", "Покраска шин", 1800, 60,  "Доп. информация");
//
//        /*// Создание ServiceRequest
//        ServiceRequest serviceRequest = new ServiceRequest();
//        serviceRequest.setCustomer(customer);  // Вставьте реального клиента
//        serviceRequest.setOrganization(organization);
//        serviceRequest.setDateService(LocalDateTime.now());
//        serviceRequest.setAddInfo("Request for cleaning service");
//        serviceRequestRepository.save(serviceRequest);*/
//
//        ServiceRequest srvcRqst1 = createServiceRequest(customer1, org1, LocalDateTime.now().plusDays(1), "Доп. информация" );
//        ServiceRequest srvcRqst2 = createServiceRequest(customer2, org1, LocalDateTime.now().plusDays(2), "Доп. информация" );
//        ServiceRequest srvcRqst3 = createServiceRequest(customer1, org2, LocalDateTime.now().plusDays(1), "Доп. информация" );
//        ServiceRequest srvcRqst4 = createServiceRequest(customer2, org2, LocalDateTime.now().plusDays(2), "Доп. информация" );
//
//        /*// Создание ServiceRequestDetail
//        ServiceRequestDetail serviceRequestDetail = new ServiceRequestDetail();
//        serviceRequestDetail.setServiceRequest(serviceRequest);
//        serviceRequestDetail.setServiceDetail(serviceDetail);
//        serviceRequestDetailRepository.save(serviceRequestDetail);*/
//
//        createServiceRequestDetail(srvcRqst1, srvcDtl1);
//        createServiceRequestDetail(srvcRqst1, srvcDtl2);
//
//        createServiceRequestDetail(srvcRqst2, srvcDtl3);
//
//        createServiceRequestDetail(srvcRqst3, srvcDtl4);
//        createServiceRequestDetail(srvcRqst3, srvcDtl5);
//        createServiceRequestDetail(srvcRqst3, srvcDtl6);
//
//        createServiceRequestDetail(srvcRqst4, srvcDtl5);
//
//        AggregatorSpecialist agrSpclst1 = createAggregatorSpecialist("Долгоруков", "Иван", "Абрамович", "Отдел администрации", "Администратор", "dlgrkv@mail.ru", "");
//        AggregatorSpecialist agrSpclst2 = createAggregatorSpecialist("Горов", "Горо", "Горович", "Отдел администрации", "Администратор", "goro@mail.ru", "");
//
//        createAggregatorSpecialistConnectorRequest(agrSpclst1, cnnRq1);
//        createAggregatorSpecialistConnectorRequest(agrSpclst2, cnnRq2);
//
//
//
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
//
//    private User createUser (String email, UserRole role) {
//        User user = new User();
//        user.setEmail(email);
//        user.setRole(role);
//        return userRepository.save(user);
//    }
//}
//
//