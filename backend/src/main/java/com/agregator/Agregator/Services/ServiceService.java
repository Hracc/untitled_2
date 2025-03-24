package com.agregator.Agregator.Services;

import com.agregator.Agregator.DTO.CreateServiceRequestDTO;
import com.agregator.Agregator.DTO.SearchOrganizationDTO;
import com.agregator.Agregator.DTO.ServiceDetailDTO;
import com.agregator.Agregator.Entity.*;
import com.agregator.Agregator.Repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    @Autowired
    private CustomerRepositiry customerRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;
    @Autowired
    private ServiceRequestDetailRepository serviceRequestDetailRepository;


    public List<SearchOrganizationDTO> getOrganizationsByCity(String cityName) {
        List<Address> addresses = addressRepository.findByCityName(cityName);
        return addresses.stream()
                .map(address -> new SearchOrganizationDTO(
                        address.getCityName(),
                        address.getOrganization().getOrganizationFullName(),
                        address.getStreetName(),
                        address.getHouseNumber()))
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Organization> getOrganizationsByCityAndName(String cityName, String Name) {
        List<Address> addresses = addressRepository.findByCityAndOrganizationName(cityName, Name);
        return addresses.stream().map(Address::getOrganization).distinct().collect(Collectors.toList());
    }

    public  List<ServiceType> getAllServiceTypes() {
        return serviceTypeRepository.findAll();
    }

    public  List<ServiceDetailDTO> getServiceDetailsByTypeCode(String typeCode) {
        List<ServiceDetail> serviceDetails = serviceDetailRepository.findByServiceTypeTypeCode(typeCode);

        log.info("Ответ: "+ serviceDetails.stream().map(detail -> new ServiceDetailDTO(
                detail.getServiceDetailId(),
                detail.getServiceDetailCode(),
                detail.getServiceDetailName(),
                detail.getServiceDetailCost(),
                detail.getServiceDetailDuration()
        )).collect(Collectors.toList()));
        // Преобразуем данные в DTO для удобства
        return serviceDetails.stream().map(detail -> new ServiceDetailDTO(
                detail.getServiceDetailId(),
                detail.getServiceDetailCode(),
                detail.getServiceDetailName(),
                detail.getServiceDetailCost(),
                detail.getServiceDetailDuration()
        )).collect(Collectors.toList());
    }


    public boolean isTimeAvailable(int organizationId, LocalDateTime newStart, int requestedServiceDetailId) {
        // Получаем выбранную услугу и её длительность
        ServiceDetail requestedDetail = serviceDetailRepository.findById(requestedServiceDetailId)
                .orElseThrow(() -> new RuntimeException("ServiceDetail не найден"));
        int requestedDuration = requestedDetail.getServiceDetailDuration();
        LocalDateTime newEnd = newStart.plusMinutes(requestedDuration);

        // Получаем все заявки организации, начало которых меньше нового окончания
        List<ServiceRequest> requests = serviceRequestRepository.findByOrganization_OrganizationIdAndDateServiceBefore(organizationId, newEnd);

        // Для каждой заявки проверяем все её детали
        for (ServiceRequest request : requests) {
            LocalDateTime existingStart = request.getDateService();
            // Получаем список деталей для заявки
            List<ServiceRequestDetail> details = serviceRequestDetailRepository.findByServiceRequest_ServiceRequestId(request.getServiceRequestId());
            for (ServiceRequestDetail detail : details) {
                ServiceDetail existingDetail = detail.getServiceDetail();
                int existingDuration = existingDetail.getServiceDetailDuration();
                LocalDateTime existingEnd = existingStart.plusMinutes(existingDuration);
                // Проверяем пересечение интервалов
                if (newStart.isBefore(existingEnd) && existingStart.isBefore(newEnd)) {
                    // Есть пересечение – время занято
                    return false;
                }
            }
        }
        return true; // Если пересечений не найдено – время доступно
    }


    @Transactional
    public void createServiceRequest(String customerEmail, CreateServiceRequestDTO dto) {
        Customer customer = customerRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new RuntimeException("Покупатель не найден"));

        Organization organization = organizationRepository.findById(dto.getOrganizationId())
                .orElseThrow(() -> new RuntimeException("Организация не найдена"));

        // Проверка, доступно ли время для выбранной услуги с учётом длительности
        for (Integer serviceDetailId : dto.getServiceDetailId()) {
            boolean isAvailable = isTimeAvailable(dto.getOrganizationId(), dto.getDateTime(), serviceDetailId);
            if (!isAvailable) {
                throw new RuntimeException("Данное время для этой организации занято.");
            }
        }

        ServiceRequest serviceRequest = new ServiceRequest();

        serviceRequest.setCustomer(customer);
        serviceRequest.setOrganization(organization);

        serviceRequest.setDateService(dto.getDateTime());
        serviceRequest.setAddInfo(dto.getAddInfo());
        serviceRequestRepository.save(serviceRequest);

        // Создание ServiceRequestDetail для каждого ServiceDetail
        for (Integer serviceDetailId : dto.getServiceDetailId()) {
            ServiceDetail serviceDetail = serviceDetailRepository.findById(serviceDetailId)
                    .orElseThrow(() -> new RuntimeException("ServiceDetail с ID " + serviceDetailId + " не найден"));

            ServiceRequestDetail serviceRequestDetail = new ServiceRequestDetail();
            serviceRequestDetail.setServiceRequest(serviceRequest);
            serviceRequestDetail.setServiceDetail(serviceDetail);
            serviceRequestDetailRepository.save(serviceRequestDetail);
        }

    }
}
