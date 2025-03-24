package com.agregator.Agregator.Services;

import com.agregator.Agregator.DTO.AggregatorSpecialistDTO;
import com.agregator.Agregator.DTO.CustomerRegistrationDTO;
import com.agregator.Agregator.DTO.OrganizationDTO;
import com.agregator.Agregator.Entity.AggregatorSpecialist;
import com.agregator.Agregator.Entity.Customer;
import com.agregator.Agregator.Entity.Organization;
import com.agregator.Agregator.Entity.User;
import com.agregator.Agregator.Enums.UserRole;
import com.agregator.Agregator.Repositories.AggregatorSpecialistRepository;
import com.agregator.Agregator.Repositories.CustomerRepository;
import com.agregator.Agregator.Repositories.OrganizationRepository;
import com.agregator.Agregator.Repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public User registerCustomer(CustomerRegistrationDTO customerDTO) {


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
        if(!customerDTO.getCustomerPatronymic().isEmpty()) {
            customer.setCustomerPatronymic(customerDTO.getCustomerPatronymic());
        }
        if (!customerDTO.getAddInfo().isEmpty()) {
            customer.setAddInfo(customerDTO.getAddInfo());
        }

        // Сохранение Customer
        customerRepository.save(customer);

        return user;
    }

    public User registerOrganization(OrganizationDTO organizationDTO) {


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
        if(!organizationDTO.getResponsiblePersonPatronymic().isEmpty()) {
            organization.setResponsiblePersonPatronymic(organizationDTO.getResponsiblePersonPatronymic());
        }
        organization.setResponsiblePersonEmail(user.getEmail());
        organization.setResponsiblePersonPhoneNumber(organizationDTO.getResponsiblePersonPhoneNumber());
        if(!organizationDTO.getAddInfo().isEmpty()) {
            organization.setAddInfo(organizationDTO.getAddInfo());
        }

        // Сохранение Organization
        organizationRepository.save(organization);

        return user;
    }

    public User registerAggregatorSpecialist(AggregatorSpecialistDTO aggregatorSpecialistDTO) {

        // Создание User для AggregatorSpecialist
        User user = new User();
        user.setEmail(aggregatorSpecialistDTO.getAggregatorSpecialistsPhoneNumber());  // или другой уникальный идентификатор
        user.setRole(UserRole.ADMINISTRATION); // или соответствующая роль для специалиста
        userRepository.save(user);

        // Создание AggregatorSpecialist
        AggregatorSpecialist aggregatorSpecialist = new AggregatorSpecialist();
        aggregatorSpecialist.setAggregatorSpecialistSurname(aggregatorSpecialistDTO.getAggregatorSpecialistSurname());
        aggregatorSpecialist.setAggregatorSpecialistName(aggregatorSpecialistDTO.getAggregatorSpecialistName());
        if(!aggregatorSpecialistDTO.getAggregatorSpecialistPatronymic().isEmpty()) {
            aggregatorSpecialist.setAggregatorSpecialistPatronymic(aggregatorSpecialistDTO.getAggregatorSpecialistPatronymic());
        }
        aggregatorSpecialist.setAggregatorSpecialistsDepartment(aggregatorSpecialistDTO.getAggregatorSpecialistsDepartment());
        aggregatorSpecialist.setAggregatorSpecialistsPosition(aggregatorSpecialistDTO.getAggregatorSpecialistsPosition());
        aggregatorSpecialist.setAggregatorSpecialistsPhoneNumber(aggregatorSpecialistDTO.getAggregatorSpecialistsPhoneNumber());
        if(!aggregatorSpecialistDTO.getAddInfo().isEmpty()) {
            aggregatorSpecialist.setAddInfo(aggregatorSpecialistDTO.getAddInfo());
        }

        // Сохранение AggregatorSpecialist
        aggregatorSpecialistRepository.save(aggregatorSpecialist);

        return user;
    }

}
