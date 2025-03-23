package com.agregator.Agregator.Services;

import com.agregator.Agregator.DTO.*;
import com.agregator.Agregator.Entity.*;
import com.agregator.Agregator.Repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
    public List<CityDTO> getCity(String city){
        List<Address> addresses = addressRepository.findByCityName(city);
        return  addresses.stream().map(
                address -> new CityDTO(
                        address.getCityName()
                )
        ).distinct()
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
                detail.getServiceDetailCode(),
                detail.getServiceDetailName(),
                detail.getServiceDetailCost(),
                detail.getServiceDetailDuration()
        )).collect(Collectors.toList()));
        // Преобразуем данные в DTO для удобства
        return serviceDetails.stream().map(detail -> new ServiceDetailDTO(
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
        boolean isAvailable = isTimeAvailable(dto.getOrganizationId(), dto.getDateTime(), dto.getServiceDetailId());
        if (!isAvailable) {
            throw new RuntimeException("Данное время для этой организации занято.");
        }

        ServiceRequest serviceRequest = new ServiceRequest();

        serviceRequest.setCustomer(customer);
        serviceRequest.setOrganization(organization);

        serviceRequest.setDateService(dto.getDateTime());
        serviceRequest.setAddInfo(dto.getAddInfo());
        serviceRequestRepository.save(serviceRequest);

        ServiceRequestDetail serviceRequestDetail = new ServiceRequestDetail();
        serviceRequestDetail.setServiceRequest(serviceRequest);

        ServiceDetail serviceDetail = serviceDetailRepository.findById(dto.getServiceDetailId())
                .orElseThrow(() -> new RuntimeException("ServiceDetail не найден"));
        serviceRequestDetail.setServiceDetail(serviceDetail);

        serviceRequestDetailRepository.save(serviceRequestDetail);
    }
    @Transactional
    public ServiceTypeDTO createServiceType(ServiceTypeDTO dto) {
        ServiceType serviceType = new ServiceType();
        serviceType.setTypeCode(dto.getTypeCode());
        serviceType.setTypeName(dto.getTypeName());

        serviceType = serviceTypeRepository.save(serviceType);

        dto.setTypeId(serviceType.getTypeId());
        return dto;
    }

    @Transactional
    public ServiceTypeDTO updateServiceType(Integer id, ServiceTypeDTO dto) {
        ServiceType serviceType = serviceTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ServiceType not found"));
        dto.setTypeId(serviceType.getTypeId());

        serviceType.setTypeCode(dto.getTypeCode());
        serviceType.setTypeName(dto.getTypeName());

        serviceTypeRepository.save(serviceType);
        return dto;
    }
    @Transactional
    public void deleteServiceType(Integer id) {
        try {
            // Извлекаем ServiceType из репозитория
            Optional<ServiceType> serviceTypeOptional = serviceTypeRepository.findById(id);

            // Проверяем, если ServiceType не найден, выбрасываем исключение
            if (serviceTypeOptional.isEmpty()) {
                throw new RuntimeException("ServiceType с id " + id + " не найден.");
            }

            ServiceType serviceType = serviceTypeOptional.get();

            // Находим все ServiceDetail, связанные с данным ServiceType
            List<ServiceDetail> serviceDetails = serviceDetailRepository.findByServiceType_TypeId(serviceType.getTypeId());

            // Если такие ServiceDetail найдены, находим и удаляем все соответствующие ServiceRequestDetail
            if (!serviceDetails.isEmpty()) {
                List<ServiceRequestDetail> serviceRequestDetails = serviceRequestDetailRepository.findByServiceDetailIn(serviceDetails);

                if (!serviceRequestDetails.isEmpty()) {
                    // Удаляем ServiceRequestDetail
                    serviceRequestDetailRepository.deleteAll(serviceRequestDetails);
                }

                // Удаляем ServiceDetail
                serviceDetailRepository.deleteAll(serviceDetails);
            }

            // Удаляем сам ServiceType
            serviceTypeRepository.deleteById(serviceType.getTypeId());

        } catch (Exception e) {
            // Логируем ошибку и выбрасываем исключение
            throw new RuntimeException("Произошла ошибка при удалении ServiceType: " + e.getMessage(), e);
        }
    }

    // Создание нового ServiceDetail
    @Transactional
    public ServiceDetailWithtTypeDTO createServiceDetail(ServiceDetailWithtTypeDTO dto) {
        ServiceDetail serviceDetail = new ServiceDetail();

        // Присваиваем ServiceType
        ServiceType serviceType = serviceTypeRepository.findById(dto.getServiceTypeId())
                .orElseThrow(() -> new RuntimeException("ServiceType not found"));
        serviceDetail.setServiceType(serviceType);

        serviceDetail.setServiceDetailCode(dto.getServiceDetailCode());
        serviceDetail.setServiceDetailName(dto.getServiceDetailName());
        serviceDetail.setServiceDetailCost(dto.getServiceDetailCost());
        serviceDetail.setServiceDetailDuration(dto.getServiceDetailDuration());
        serviceDetail.setAddInfo(dto.getAddInfo());

        serviceDetail = serviceDetailRepository.save(serviceDetail);
        dto.setServiceDetailId(serviceDetail.getServiceDetailId());

        return dto;
    }

    // Обновление ServiceDetail
    @Transactional
    public ServiceDetailWithtTypeDTO updateServiceDetail(Integer id, ServiceDetailWithtTypeDTO dto) {
        ServiceDetail serviceDetail = serviceDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ServiceDetail not found"));

        if (dto.getServiceTypeId() != null) {
            ServiceType serviceType = serviceTypeRepository.findById(dto.getServiceTypeId())
                    .orElseThrow(() -> new RuntimeException("ServiceType not found"));
            serviceDetail.setServiceType(serviceType);
        }

        serviceDetail.setServiceDetailCode(dto.getServiceDetailCode());
        serviceDetail.setServiceDetailName(dto.getServiceDetailName());
        serviceDetail.setServiceDetailCost(dto.getServiceDetailCost());
        serviceDetail.setServiceDetailDuration(dto.getServiceDetailDuration());
        serviceDetail.setAddInfo(dto.getAddInfo());

        serviceDetailRepository.save(serviceDetail);
        dto.setServiceDetailId(serviceDetail.getServiceDetailId());

        return dto;
    }

    // Удаление ServiceDetail
    @Transactional
    public void deleteServiceDetail(Integer id) {
        try {
            // Находим ServiceDetail по ID
            Optional<ServiceDetail> serviceDetailOptional = serviceDetailRepository.findById(id);

            // Если ServiceDetail не найден, выбрасываем исключение
            if (serviceDetailOptional.isEmpty()) {
                throw new RuntimeException("ServiceDetail с id " + id + " не найден.");
            }

            ServiceDetail serviceDetail = serviceDetailOptional.get();

            // Находим все ServiceRequestDetail, связанные с данным ServiceDetail
            List<ServiceRequestDetail> serviceRequestDetails = serviceRequestDetailRepository.findByServiceDetail(serviceDetail);

            // Если есть связанные ServiceRequestDetail, удаляем их
            if (!serviceRequestDetails.isEmpty()) {
                serviceRequestDetailRepository.deleteAll(serviceRequestDetails);
            }

            // Удаляем сам ServiceDetail
            serviceDetailRepository.deleteById(serviceDetail.getServiceDetailId());

        } catch (Exception e) {
            // Логируем ошибку и выбрасываем исключение
            throw new RuntimeException("Произошла ошибка при удалении ServiceDetail: " + e.getMessage(), e);
        }
    }
}
