package com.agregator.Agregator.Controllers;

import com.agregator.Agregator.DTO.*;
import com.agregator.Agregator.Entity.Address;
import com.agregator.Agregator.Entity.Customer;
import com.agregator.Agregator.Entity.Organization;
import com.agregator.Agregator.Services.CustumerService;
import com.agregator.Agregator.Services.OrganizationService;
import com.agregator.Agregator.Services.RegistrationService;
import com.agregator.Agregator.Services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private CustumerService customerService;
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private ServiceService serviceService;

    @GetMapping("/all/Customers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PostMapping("/Create/Customers")
    public Customer createCustomer(@RequestBody CustomerRegistrationDTO customer) {
        return registrationService.registerCustomer(customer);
    }

    @PutMapping("Update")
    public ResponseEntity<?> updateCustomer(@RequestBody CustumerDTO custumerDTO, @RequestParam Integer id, @RequestParam String email) {
        try {
            CustumerDTO updatedCustomer = customerService.updateCustumer(custumerDTO, id, email);
            return ResponseEntity.ok(updatedCustomer);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("Customer/{email}")
    public void deleteCustomer(@PathVariable String email) {
        customerService.deleteCustomerByEmail(email);
    }
    @GetMapping("/organizations")
    public List<OrganizationDTO> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    @GetMapping("Organization/{id}")
    public Organization getOrganization(@PathVariable Integer id) {
        return organizationService.getOrganizationById(id);
    }

    @PostMapping("Organization/create")
    public ResponseEntity<String>  createOrganization(@RequestBody CreateOrganizationDTO organization) {
        registrationService.registerOrganization(organization);
        return ResponseEntity.status(HttpStatus.CREATED).body("Организация создана");
    }

    @PutMapping("Organization/update/{id}")
    public OrganizationDTO updateOrganization(@PathVariable int id, @RequestBody OrganizationDTO org) {
        return organizationService.updateOrganization(id, org);
    }

    @DeleteMapping("Organization/delete/{id}")
    public ResponseEntity<String> deleteOrganization(@PathVariable int id) {
        organizationService.deleteOrganization(id);
        return ResponseEntity.ok("Организация удалена");
    }

    // Адреса конкретной организации
    @GetMapping("Organization/{id}/addresses")
    public List<Address> getOrganizationAddresses(@PathVariable int id) {
        return organizationService.getAddressesByOrganization(id);
    }

    @PostMapping("Organization/{id}/addresses")
    public Address addAddress(@PathVariable int id, @RequestBody Address address) {
        return organizationService.addAddressToOrganization(id, address);
    }

    @DeleteMapping("Organization/addresses/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Integer addressId) {
        organizationService.deleteAddress(addressId);
        return ResponseEntity.ok("Адрес удалён");
    }

    // Создание ServiceType
    @PostMapping("Service/serviceTypes")
    public ResponseEntity<ServiceTypeDTO> createServiceType(@RequestBody ServiceTypeDTO dto) {
        return ResponseEntity.ok(serviceService.createServiceType(dto));
    }

    // Обновление ServiceType
    @PutMapping("Service/serviceTypes/{id}")
    public ResponseEntity<ServiceTypeDTO> updateServiceType(@PathVariable Integer id, @RequestBody ServiceTypeDTO dto) {
        return ResponseEntity.ok(serviceService.updateServiceType(id, dto));
    }

    // Удаление ServiceType
    @DeleteMapping("Service/serviceTypes/{id}")
    public ResponseEntity<?> deleteServiceType(@PathVariable Integer id) {
        serviceService.deleteServiceType(id);
        return ResponseEntity.noContent().build();
    }

    // Создание ServiceDetail
    @PostMapping("Service/serviceDetails")
    public ResponseEntity<ServiceDetailWithtTypeDTO> createServiceDetail(@RequestBody ServiceDetailWithtTypeDTO dto) {
        return ResponseEntity.ok(serviceService.createServiceDetail(dto));
    }

    // Обновление ServiceDetail
    @PutMapping("Service/serviceDetails/{id}")
    public ResponseEntity<ServiceDetailWithtTypeDTO> updateServiceDetail(@PathVariable Integer id, @RequestBody ServiceDetailWithtTypeDTO dto) {
        return ResponseEntity.ok(serviceService.updateServiceDetail(id, dto));
    }

    // Удаление ServiceDetail
    @DeleteMapping("Service/serviceDetails/{id}")
    public ResponseEntity<Void> deleteServiceDetail(@PathVariable Integer id) {
        serviceService.deleteServiceDetail(id);
        return ResponseEntity.noContent().build();
    }
}
