package com.agregator.Agregator.Services;

import com.agregator.Agregator.DTO.AggregatorSpecialistDTO;
import com.agregator.Agregator.DTO.CreateOrganizationDTO;
import com.agregator.Agregator.DTO.CustomerRegistrationDTO;
import com.agregator.Agregator.DTO.OrganizationDTO;
import com.agregator.Agregator.Entity.*;
import com.agregator.Agregator.Enums.UserRole;
import com.agregator.Agregator.Repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RegistrationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private AggregatorSpecialistRepository aggregatorSpecialistRepository;

    @Autowired
    private ConnectionRequestRepository connectionRequestRepository;

    @Transactional
    public Customer registerCustomer(CustomerRegistrationDTO customerDTO) {
        // Создание User для Customer
        User user = new User();
        user.setEmail(customerDTO.getEmail());
        user.setRole(UserRole.CUSTOMER);
        userRepository.save(user);
        // Создание Customer
        Customer customer = new Customer();
        customer.setCustomerSurname(customerDTO.getCustomerSurname());
        customer.setCustomerName(customerDTO.getCustomerName());
        customer.setEmail(user.getEmail());
        if(customerDTO.getCustomerPatronymic() != null && !customerDTO.getCustomerPatronymic().isEmpty()) {
            customer.setCustomerPatronymic(customerDTO.getCustomerPatronymic());
        }
        if (customerDTO.getAddInfo() != null && !customerDTO.getAddInfo().isEmpty()) {
            customer.setAddInfo(customerDTO.getAddInfo());
        }
        // Сохранение Customer
        customerRepository.save(customer);
        return customer;
    }
    @Transactional
    public void registerOrganization(CreateOrganizationDTO organizationDTO) {
        // Создание User для Organization
        User user = new User();
        user.setEmail(organizationDTO.getResponsiblePersonEmail());
        user.setRole(UserRole.ORGANIZATION);
        userRepository.save(user);
        // Создание Organization
        Organization organization = new Organization();
        organization.setOrganizationFullName(organizationDTO.getOrganizationFullName());
        organization.setOrganizationShortName(organizationDTO.getOrganizationShortName());
        organization.setInn(organizationDTO.getInn());
        organization.setKpp(organizationDTO.getKpp());
        organization.setOgrn(organizationDTO.getOgrn());
        organization.setResponsiblePersonSurname(organizationDTO.getResponsiblePersonSurname());
        organization.setResponsiblePersonName(organizationDTO.getResponsiblePersonName());
        if (organizationDTO.getResponsiblePersonPatronymic() != null && !organizationDTO.getResponsiblePersonPatronymic().isEmpty()) {
            organization.setResponsiblePersonPatronymic(organizationDTO.getResponsiblePersonPatronymic());
        }
        organization.setResponsiblePersonEmail(user.getEmail());
        organization.setResponsiblePersonPhoneNumber(organizationDTO.getResponsiblePersonPhoneNumber());
        if (organizationDTO.getAddInfo() != null && !organizationDTO.getAddInfo().isEmpty()) {
            organization.setAddInfo(organizationDTO.getAddInfo());
        }
        organizationRepository.save(organization);

        ConnectionRequest connectionRequest = new ConnectionRequest();
        connectionRequest.setOrganization(organization);
        connectionRequest.setRegNumber("temp");
        connectionRequest.setDateBegin(LocalDate.now());
        connectionRequest.setStatus("Новая");
        log.info("Ответ: ", connectionRequest.getOrganization());

        ConnectionRequest savedRequest = connectionRequestRepository.save(connectionRequest);
        // Генерируем regNumber на основе connectionRequestId
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        String regNumber = savedRequest.getConnectionRequestId() + currentDate;

        // Обновляем regNumber
        savedRequest.setRegNumber(regNumber);

        // Сохраняем сущность с обновлённым regNumber
        connectionRequestRepository.save(connectionRequest);
    }

    /*public ConnectionRequest createConnectionRequest (Organization organization) {
        ConnectionRequest connectionRequest = new ConnectionRequest();
        connectionRequest.setOrganization(organization);
        connectionRequest.setRegNumber("temp");
        connectionRequest.setDateBegin(LocalDate.now());
        connectionRequest.setStatus("Новая");

        ConnectionRequest savedRequest = connectionRequestRepository.save(connectionRequest);
        // Генерируем regNumber на основе connectionRequestId
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String regNumber = savedRequest.getConnectionRequestId() + " " + currentDate;

        // Обновляем regNumber
        savedRequest.setRegNumber(regNumber);

        // Сохраняем сущность с обновлённым regNumber
        return connectionRequestRepository.save(savedRequest);
    }*/
    @Transactional
    public User registerAggregatorSpecialist(AggregatorSpecialistDTO aggregatorSpecialistDTO) {

        // Создание User для AggregatorSpecialist
        User user = new User();
        user.setEmail(aggregatorSpecialistDTO.getAggregatorSpecialistsEmail());  // или другой уникальный идентификатор
        user.setRole(UserRole.ADMINISTRATION); // или соответствующая роль для специалиста
        userRepository.save(user);

        // Создание AggregatorSpecialist
        AggregatorSpecialist aggregatorSpecialist = new AggregatorSpecialist();
        aggregatorSpecialist.setAggregatorSpecialistSurname(aggregatorSpecialistDTO.getAggregatorSpecialistSurname());
        aggregatorSpecialist.setAggregatorSpecialistName(aggregatorSpecialistDTO.getAggregatorSpecialistName());
        if(aggregatorSpecialist.getAggregatorSpecialistPatronymic() != null &&!aggregatorSpecialistDTO.getAggregatorSpecialistPatronymic().isEmpty()) {
            aggregatorSpecialist.setAggregatorSpecialistPatronymic(aggregatorSpecialistDTO.getAggregatorSpecialistPatronymic());
        }
        aggregatorSpecialist.setAggregatorSpecialistsDepartment(aggregatorSpecialistDTO.getAggregatorSpecialistsDepartment());
        aggregatorSpecialist.setAggregatorSpecialistsPosition(aggregatorSpecialistDTO.getAggregatorSpecialistsPosition());
        aggregatorSpecialist.setAggregatorSpecialistsEmail(aggregatorSpecialistDTO.getAggregatorSpecialistsEmail());
        if(aggregatorSpecialist.getAddInfo() != null && !aggregatorSpecialistDTO.getAddInfo().isEmpty()) {
            aggregatorSpecialist.setAddInfo(aggregatorSpecialistDTO.getAddInfo());
        }

        // Сохранение AggregatorSpecialist
        aggregatorSpecialistRepository.save(aggregatorSpecialist);

        return user;
    }

}
