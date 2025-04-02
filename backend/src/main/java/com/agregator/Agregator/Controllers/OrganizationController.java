package com.agregator.Agregator.Controllers;

import com.agregator.Agregator.DTO.ConnectionRequestDTO;
import com.agregator.Agregator.DTO.CreateOrganizationDTO;
import com.agregator.Agregator.DTO.OrganizationDTO;
import com.agregator.Agregator.DTO.ServiceRequestDTO;
import com.agregator.Agregator.Entity.AggregatorSpecialist;
import com.agregator.Agregator.Entity.ConnectionRequest;
import com.agregator.Agregator.Entity.Organization;
import com.agregator.Agregator.Entity.ServiceRequest;
import com.agregator.Agregator.Services.OrganizationService;
import com.agregator.Agregator.Services.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/organization")
public class OrganizationController {
    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private OrganizationService organizationService;



    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateOrganizationDTO dto) {
        try {
            return organizationService.createOrganization(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при создании организации");
        }
    }
    @PreAuthorize("hasRole('ORGANIZATION')")
    @GetMapping("/Status")
    public ResponseEntity<List<ConnectionRequestDTO>> Status(){
        String Email = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        List<ConnectionRequestDTO> statusList = organizationService.UpdateStatus(Email);
        return ResponseEntity.ok(statusList);
    }

    @GetMapping("/all_servicerequest")
    @PreAuthorize("hasRole('ORGANIZATION')")
    public ResponseEntity<List<ServiceRequestDTO>> getServiceRequestsForOrganization(){
        String Email = SecurityContextHolder.getContext().getAuthentication().getName().toString();
        return organizationService.getServiceRequestsForOrganization(Email);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('ORGANIZATION')")
    public ResponseEntity<?> searchOrgByEmail() {
        try {
            // Получаем email из JWT
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            log.info("Email: "+ email);
            // Ищем пользователя по email
            return organizationService.findCustomerByEmail(email);
        } catch (Exception e) {
            log.error("Ошибка при поиске пользователя", e);
            return ResponseEntity.status(500).body("Ошибка при поиске покупателя");
        }
    }
}
