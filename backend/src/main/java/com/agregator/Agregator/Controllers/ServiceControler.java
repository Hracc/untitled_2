package com.agregator.Agregator.Controllers;

import com.agregator.Agregator.DTO.*;
import com.agregator.Agregator.Entity.ServiceType;
import com.agregator.Agregator.Services.ServiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Service")
public class ServiceControler {
    private static final Logger log = LoggerFactory.getLogger(ServiceControler.class);
    @Autowired
    public ServiceService serviceService;

    /*@GetMapping("/OrganizationsByCity")
    public List<SearchOrganizationDTO> getOrganizationsByCity(@RequestParam String city) {
        return serviceService.getOrganizationsByCity(city);
    }*/
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/CITY")
    public List<CityDTO> getListOfSity(@RequestParam String city){
        return serviceService.getCity(city);
    }
    @GetMapping("/OrganizationByCityAndName")
    public List<SearchOrganizationDTO> getOrganizationsByCity(@RequestParam String city, @RequestParam(required = false) String name) {
        log.info(city);
        log.info(name);
        return serviceService.getOrganizationsByCityAndName(city,name);
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/ServiceTypes")
    public List<ServiceType> getAllServiceType(){
        return serviceService.getAllServiceTypes();
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/details")
    public List<ServiceDetailDTO> getServiceDetailsByTypeCode(@RequestParam String typeCode) {
        log.info(typeCode);
        return serviceService.getServiceDetailsByTypeCode(typeCode);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/createRequest")
    public ResponseEntity<String> createServiceRequest(@RequestBody CreateServiceRequestDTO serviceRequestDTO) {
        try {
            for (Integer serviceDetailId : serviceRequestDTO.getServiceDetailId()) {
                boolean isAvailable = serviceService.isTimeAvailable(
                        serviceRequestDTO.getOrganizationId(),
                        serviceRequestDTO.getDateTime(),
                        serviceDetailId
                );
                if (!isAvailable) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Время для выбранной услуги уже занято");
                }
            }

            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            log.info("email: "+ email);
            serviceService.createServiceRequest(email,serviceRequestDTO);
            return ResponseEntity.ok("Заявка успешно создана");
        } catch (Exception e) {
            log.error("Ошибка при создании заявки: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при создании заявки");
        }
    }
}
