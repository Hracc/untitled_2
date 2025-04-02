package com.agregator.Agregator.Services;

import com.agregator.Agregator.DTO.CustumerDTO;
import com.agregator.Agregator.Entity.Customer;
import com.agregator.Agregator.Entity.ServiceRequest;
import com.agregator.Agregator.Entity.ServiceRequestDetail;
import com.agregator.Agregator.Repositories.CustomerRepository;
import com.agregator.Agregator.Repositories.ServiceRequestDetailRepository;
import com.agregator.Agregator.Repositories.ServiceRequestRepository;
import com.agregator.Agregator.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CustumerService {
    private static final Logger log = LoggerFactory.getLogger(CustumerService.class);
    @Autowired
    private CustomerRepository customerRepositiry;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;
    @Autowired
    private ServiceRequestDetailRepository serviceRequestDetailRepository;

    public Customer findCustomerByEmail(String email) {
        return customerRepositiry.findByEmail(email).orElse(null);
    }
    @Transactional
    public String editCustumers(CustumerDTO custumerDTO, String email){
        try {
            Optional<Customer> customerOpt  = customerRepositiry.findByEmail(email);
            if (customerOpt.isEmpty()) {
                log.error("Покупатель не найден по email: {}", email);
                throw new RuntimeException("Покупатель не найден по email");
            }
            Customer customer = customerOpt.get();

            if (custumerDTO.getCustomerName() != null && !custumerDTO.getCustomerName().isEmpty()) {
                customer.setCustomerName(custumerDTO.getCustomerName());
            }
            if (custumerDTO.getCustomerPatronymic() != null && !custumerDTO.getCustomerPatronymic().isEmpty()) {
                customer.setCustomerPatronymic(custumerDTO.getCustomerPatronymic());
            }
            if (custumerDTO.getCustomerSurname() != null && !custumerDTO.getCustomerSurname().isEmpty()) {
                customer.setCustomerSurname(custumerDTO.getCustomerSurname());
            }
            if (custumerDTO.getAddInfo() != null && !custumerDTO.getAddInfo().isEmpty()) {
                customer.setAddInfo(custumerDTO.getAddInfo());
            }
            String newEmail = email;

            if (custumerDTO.getEmail() != null && !custumerDTO.getEmail().isEmpty() && !custumerDTO.getEmail().equals(email)) {
                customer.setEmail(custumerDTO.getEmail());
                //проверяем что email правильного вида
                if (isValidEmail(custumerDTO.getEmail())) {
                    //Проверяем, что новый email не занят
                    if (!isEmailExist(custumerDTO.getEmail())) {
                        customer.setEmail(custumerDTO.getEmail());
                        // Обновим email и в таблице users
                        userRepository.findByEmail(email).ifPresent(user -> {
                            user.setEmail(custumerDTO.getEmail());
                            userRepository.save(user);
                        });
                    }
                }
                newEmail = custumerDTO.getEmail();
            }

            customerRepositiry.save(customer);
            log.info("Покупатель успешно обновлен");

            if (!newEmail.equals(email)) {
                var user = userRepository.findByEmail(newEmail).orElseThrow(() -> new RuntimeException("User not found for token refresh"));
                return jwtService.generateToken(newEmail, user.getRole());
            }

            return null;
        }catch (Exception e){
            log.error(e.getMessage());
            log.error("Ошибка при изменении пользователя");
            return "Ошибка при изменении пользователя";
        }
    }
    @Transactional
    public CustumerDTO updateCustumer(CustumerDTO custumerDTO,Integer id, String email){
        try {
            Optional<Customer> customerOpt  = customerRepositiry.findById(id);
            if (customerOpt.isEmpty()) {
                log.error("Покупатель не найден по id: {}", id);
                throw new RuntimeException("Покупатель не найден по email");
            }
            Customer customer = customerOpt.get();

            if (custumerDTO.getCustomerName() != null && !custumerDTO.getCustomerName().isEmpty()) {
                customer.setCustomerName(custumerDTO.getCustomerName());
            }
            if (custumerDTO.getCustomerPatronymic() != null && !custumerDTO.getCustomerPatronymic().isEmpty()) {
                customer.setCustomerPatronymic(custumerDTO.getCustomerPatronymic());
            }
            if (custumerDTO.getCustomerSurname() != null && !custumerDTO.getCustomerSurname().isEmpty()) {
                customer.setCustomerSurname(custumerDTO.getCustomerSurname());
            }
            if (custumerDTO.getAddInfo() != null && !custumerDTO.getAddInfo().isEmpty()) {
                customer.setAddInfo(custumerDTO.getAddInfo());
            }

            if (custumerDTO.getEmail() != null && !custumerDTO.getEmail().isEmpty()) {
                //проверяем что email правильного вида
                if (isValidEmail(custumerDTO.getEmail())) {
                    //Проверяем, что новый email не занят
                    if (!isEmailExist(custumerDTO.getEmail())) {
                        customer.setEmail(custumerDTO.getEmail());
                        // Обновим email и в таблице users
                        userRepository.findByEmail(email).ifPresent(user -> {
                            user.setEmail(custumerDTO.getEmail());
                            userRepository.save(user);
                        });
                    }
                }
            }

            customerRepositiry.save(customer);
            CustumerDTO custumerDTO1 = new CustumerDTO();
            custumerDTO1.setEmail(customer.getEmail());
            custumerDTO1.setCustomerName(customer.getCustomerName());
            custumerDTO1.setCustomerSurname(customer.getCustomerSurname());
            custumerDTO1.setCustomerPatronymic(customer.getCustomerPatronymic());
            custumerDTO1.setAddInfo(customer.getAddInfo());
            log.info("Покупатель успешно обновлен");

            return custumerDTO1;
        }catch (Exception e){
            log.error(e.getMessage());
            log.error("Ошибка при изменении пользователя");
            throw new RuntimeException("Ошибка при изменении пользователя");
        }
    }
    @Transactional
    public void deleteCustomerByEmail(String email) {
        Optional<Customer> customerOpt = customerRepositiry.findByEmail(email);
        if (customerOpt.isEmpty()) {
            log.error("Покупатель не найден по почте: {}", email);
            throw new RuntimeException("Покупатель не найден");
        }
        // Обновляем все записи в ServiceRequest, связанные с этим клиентом
        List<ServiceRequest> serviceRequests = serviceRequestRepository.findByCustomer(customerOpt.get());
        if (!serviceRequests.isEmpty()) {
            // Обновляем customerId на NULL или на нового клиента (зависит от бизнес-логики)
            for (ServiceRequest serviceRequest : serviceRequests) {
                List<ServiceRequestDetail> serviceRequestDetails = serviceRequestDetailRepository.findByServiceRequest_ServiceRequestId(serviceRequest.getServiceRequestId());
                for (ServiceRequestDetail server : serviceRequestDetails){
                    serviceRequestDetailRepository.delete(server);
                }
                serviceRequestRepository.delete(serviceRequest);
            }
            log.info("Запросы обслуживания для клиента {} обновлены", email);
        }

        // Удаляем Customer
        customerRepositiry.delete(customerOpt.get());
        log.info("Покупатель {} удален", email);

        // Также можно удалить пользователя из users, если он есть
        userRepository.findByEmail(email).ifPresent(user -> {
            userRepository.delete(user);
            log.info("Пользователь {} удален", email);
        });
    }
    public List<Customer> getAllCustomers() {
        return customerRepositiry.findAll();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    private boolean isEmailExist(String email){
        if(userRepository.findByEmail(email).isPresent()){
            return true;
        }else {
            return false;
        }
    }
}
