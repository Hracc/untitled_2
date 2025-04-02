package com.agregator.Agregator.Services;

import com.agregator.Agregator.DTO.ConnectionOrganizationDTO;
import com.agregator.Agregator.DTO.ConnectionRequestAdminDTO;
import com.agregator.Agregator.Entity.AggregatorSpecialist;
import com.agregator.Agregator.Entity.AggregatorSpecialistConnectorRequest;
import com.agregator.Agregator.Entity.ConnectionRequest;
import com.agregator.Agregator.Entity.Customer;
import com.agregator.Agregator.Repositories.AggregatorSpecialistConnectorRequestRepository;
import com.agregator.Agregator.Repositories.AggregatorSpecialistRepository;
import com.agregator.Agregator.Repositories.ConnectionRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private ConnectionRequestRepository connectionRequestRepository;
    @Autowired
    private AggregatorSpecialistRepository aggregatorSpecialistRepository;
    @Autowired
    private AggregatorSpecialistConnectorRequestRepository aggregatorSpecialistConnectorRequestRepository;

    public AggregatorSpecialist findCustomerByEmail(String email) {
        return aggregatorSpecialistRepository.findByaggregatorSpecialistsEmail(email).orElse(null);
    }

    public List<ConnectionRequestAdminDTO> getAllConnectionRequests() {
        return connectionRequestRepository.findAll().stream()
                .map(req -> new ConnectionRequestAdminDTO(
                        req.getConnectionRequestId(), // Передаем id
                        req.getOrganization().getOrganizationShortName(), // Передаем имя организации
                        req.getRegNumber(),
                        req.getDateBegin(),
                        req.getDateEnd(),
                        req.getStatus(),
                        req.getAddInfo() // Добавляем addInfo
                ))
                .collect(Collectors.toList());
    }

    public List<ConnectionRequestAdminDTO> getConnectionRequestsByStatus(String status) {
        return connectionRequestRepository.findByStatus(status).stream()
                .map(req -> new ConnectionRequestAdminDTO(
                        req.getConnectionRequestId(),
                        req.getOrganization().getOrganizationShortName(), // Передаем имя организации
                        req.getRegNumber(),
                        req.getDateBegin(),
                        req.getDateEnd(),
                        req.getStatus(),
                        req.getAddInfo() // Добавляем addInfo
                ))
                .collect(Collectors.toList());
    }


    @Transactional
    public ResponseEntity<String> UpdateStatus(int connectionRequestId, String email, String Status) {
        // Получаем заявку
        ConnectionRequest connectionRequest = connectionRequestRepository.findById(connectionRequestId)
                .orElseThrow(() -> new RuntimeException("Заявка не найдена"));

        // Проверяем, что заявка еще не в работе
        if ("В работе".equals(connectionRequest.getStatus())) {
            return ResponseEntity.badRequest().body("Заявка уже в статусе 'В работе'");
        }
        if ("Исполнено".equals(connectionRequest.getStatus())){
            connectionRequest.setDateEnd(LocalDate.now());
        }

        // Обновляем статус заявки
        connectionRequest.setStatus(Status);
        connectionRequestRepository.save(connectionRequest);


        // Получаем администратора
        AggregatorSpecialist admin = aggregatorSpecialistRepository.findByaggregatorSpecialistsEmail(email)
                .orElseThrow(() -> new RuntimeException("Администратор не найден"));

        // Создаем запись связи, если администратор еще не работал над этим запросом

        // Проверяем, существует ли уже связь между администратором и запросом на подключение
        boolean exists = aggregatorSpecialistConnectorRequestRepository.existsByAggregatorSpecialistAndConnectionRequest(admin, connectionRequest);
        if (exists) {
            return ResponseEntity.ok("Заявка переведена в статус "+Status+" администратором " + admin.getAggregatorSpecialistsId());
        }else {
            AggregatorSpecialistConnectorRequest connectorRequest = new AggregatorSpecialistConnectorRequest();
            connectorRequest.setAggregatorSpecialist(admin);
            connectorRequest.setConnectionRequest(connectionRequest);
            aggregatorSpecialistConnectorRequestRepository.save(connectorRequest);

            return ResponseEntity.ok("Заявка переведена в статус "+Status+" администратором " + admin.getAggregatorSpecialistsId());
        }
    }

    public ResponseEntity<ConnectionOrganizationDTO> getConnectionStatusByOrganization(int organizationId) {
        // Получаем последнюю заявку для организации
        Optional<ConnectionRequest> optionalRequest = connectionRequestRepository.findLatestConnectionRequestByOrganization(organizationId);

        if (optionalRequest.isPresent()) {
            ConnectionRequest request = optionalRequest.get();
            ConnectionOrganizationDTO dto = new ConnectionOrganizationDTO();
            dto.setStatus(request.getStatus());
            dto.setDate(request.getDateBegin().toString()); // Преобразуем LocalDate в строку
            dto.setDateExecution(request.getDateEnd() != null ? request.getDateEnd().toString() : null); // Если дата окончания существует, преобразуем, иначе ставим null
            return ResponseEntity.ok().body(dto);
        }

        return null; // Если заявки нет, возвращаем null или можно выбросить исключение
    }

}
