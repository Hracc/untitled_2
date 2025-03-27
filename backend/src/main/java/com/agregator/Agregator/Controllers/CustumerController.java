package com.agregator.Agregator.Controllers;

import com.agregator.Agregator.DTO.CustumerDTO;
import com.agregator.Agregator.Entity.Customer;
import com.agregator.Agregator.Repositories.UserRepository;
import com.agregator.Agregator.Services.CustumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController()
@Slf4j
@PreAuthorize("hasRole('CUSTOMER')")
@RequestMapping("/customer")
public class CustumerController {
    @Autowired
    private CustumerService custumerService;

    @GetMapping("/TEST")
    public String test(){
        return "Все хорошо";
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchCustomerByEmail() {
        try {
            // Получаем email из JWT
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            log.info("Email: "+ email);
            // Ищем пользователя по email
            Customer customer = custumerService.findCustomerByEmail(email);
            if (customer != null) {
                return ResponseEntity.ok(customer);
            } else {
                return ResponseEntity.status(404).body("Customer not found");
            }
        } catch (Exception e) {
            log.error("Ошибка при поиске пользователя", e);
            return ResponseEntity.status(500).body("Ошибка при поиске покупателя");
        }
    }


    @PutMapping("/edit")
    public ResponseEntity<?> editCustomer(@RequestBody CustumerDTO custumerDTO) {
        try {
            log.info("Зашел внутрь");
            String oldEmail = SecurityContextHolder.getContext().getAuthentication().getName().toString();
            String newToken = custumerService.editCustumers(custumerDTO, oldEmail);

            if (newToken != null) {
                // Email поменялся — вернем новый токен
                return ResponseEntity.ok().body("Почта поменялась. Новый токен: " + newToken);
            }

            return ResponseEntity.ok("Покупатель успешно изменен");
        } catch (Exception e) {
            log.error("Ошибка при редактировании пользователя", e);
            return ResponseEntity.badRequest().body("Ошибка при редактировании");
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCustomer() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            custumerService.deleteCustomerByEmail(email);
            return ResponseEntity.ok("Покупатель и его записи удалены");
        } catch (Exception e) {
            log.error("Ошибка при удалении пользователя", e);
            return ResponseEntity.badRequest().body("Ошибка при удалении");
        }
    }
}
