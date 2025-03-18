package com.agregator.Agregator.Services;

import com.agregator.Agregator.DTO.SearchOrganizationDTO;
import com.agregator.Agregator.DTO.ServiceDetailDTO;
import com.agregator.Agregator.Entity.Address;
import com.agregator.Agregator.Entity.Organization;
import com.agregator.Agregator.Entity.ServiceDetail;
import com.agregator.Agregator.Entity.ServiceType;
import com.agregator.Agregator.Repositories.AddressRepository;
import com.agregator.Agregator.Repositories.ServiceDetailRepository;
import com.agregator.Agregator.Repositories.ServiceTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@Slf4j
public class ServiceService {
    @Autowired
    private  AddressRepository addressRepository;
    @Autowired
    private  ServiceTypeRepository serviceTypeRepository;
    @Autowired
    private  ServiceDetailRepository serviceDetailRepository;


    public List<Organization> getOrganizationsByCity(String cityName) {
        List<Address> addresses = addressRepository.findByCityName(cityName);
        return addresses.stream().map(Address::getOrganization).distinct().collect(Collectors.toList());
    }

    public  List<SearchOrganizationDTO> getOrganizationsByCityAndName(String cityName, String Name) {
        List<Address> addresses = addressRepository.findByCityAndOrganizationName(cityName, Name);
        return addresses.stream()
                .map(address -> new SearchOrganizationDTO(
                        address.getCityName(),
                        address.getOrganization().getOrganizationFullName(),
                        address.getStreetName(),
                        address.getHouseNumber()))
                .distinct()
                .collect(Collectors.toList());
    }

    public  List<ServiceType> getAllServiceTypes() {
        return serviceTypeRepository.findAll();
    }

    public  List<ServiceDetailDTO> getServiceDetailsByTypeCode(String typeCode) {
        List<ServiceDetail> serviceDetails = serviceDetailRepository.findByServiceTypeTypeCode(typeCode);

        // Преобразуем данные в DTO для удобства
        return serviceDetails.stream().map(detail -> new ServiceDetailDTO(
                detail.getServiceDetailCode(),
                detail.getServiceDetailName(),
                detail.getServiceDetailCost(),
                detail.getServiceDetailDuration()
        )).collect(Collectors.toList());
    }
}
