package com.agregator.Agregator.Controllers;

import com.agregator.Agregator.DTO.*;
import com.agregator.Agregator.Entity.Organization;
import com.agregator.Agregator.Entity.ServiceType;
import com.agregator.Agregator.Services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Service")
public class ServiceControler {
    @Autowired
    public ServiceService serviceService;

    @PostMapping("/OrganizationsByCity")
    public List<Organization> getOrganizationsByCity(@RequestBody SearchCityDTO city) {
        return serviceService.getOrganizationsByCity(city.getName());
    }

    @PostMapping("/OrganizationByCityAndName")
    public List<SearchOrganizationDTO> getOrganizationsByCity(@RequestBody SearchDTO Search) {
        String city = Search.getCity();
        String name = Search.getName();
        return serviceService.getOrganizationsByCityAndName(city,name);
    }
    @GetMapping("/ServiceTypes")
    public List<ServiceType> getAllServiceType(){
        return serviceService.getAllServiceTypes();
    }

    @PostMapping("/details")
    public List<ServiceDetailDTO> getServiceDetailsByTypeCode(@RequestParam String typeCode) {
        return serviceService.getServiceDetailsByTypeCode(typeCode);
    }
}
