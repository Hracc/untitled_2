package com.agregator.Agregator.Services;


import com.agregator.Agregator.DTO.ServiceDetailForEmailDTO;
import com.agregator.Agregator.DTO.ServiceRequestDTO;
import com.agregator.Agregator.DTO.ServiceRequestForEmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import java.util.Random;

@Service
public class EmailService {

    @Autowired
    public JavaMailSender emailSender;

    private static final Logger logger = Logger.getLogger(EmailService.class.getName());
    public void sendCodeToEmail(String toAddress, String code) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("chetvergovkirill@yandex.ru");
        simpleMailMessage.setTo(toAddress);
        logger.info("отправка от "+simpleMailMessage.getFrom()+"к кому "+toAddress );
        simpleMailMessage.setSubject("Тестирование ученического проекта");
        simpleMailMessage.setText("Ваш код подверждения: " + code);


        emailSender.send(simpleMailMessage);
    }

    public void sendServiceToEmail(String toAddress, ServiceRequestForEmailDTO serviceRequestDTO) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("chetvergovkirill@yandex.ru");
        simpleMailMessage.setTo(toAddress);
        logger.info("отправка от "+simpleMailMessage.getFrom()+"к кому "+toAddress );
        simpleMailMessage.setSubject("Тестирование ученического проекта");
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("К вам записался(ась): ")
//                .append(serviceRequestDTO.getCustomerName())
                .append("\nС почтой: ").append(serviceRequestDTO.getCustomerEmail())
                .append("\nНа время: ").append(serviceRequestDTO.getDateService());
//                .append("\nДоп. информация: ").append(serviceRequestDTO.getAddInfo());

        List<ServiceDetailForEmailDTO> details = serviceRequestDTO.getServiceDetails();
        if (details != null && !details.isEmpty()) {
            messageBuilder.append("\n\nВыбранные услуги:\n");
            for (ServiceDetailForEmailDTO detail : details) {
                messageBuilder.append("- ").append(detail.getName())
                        .append(": ").append(detail.getPrice()).append(" руб.\n");
            }
        } else {
            messageBuilder.append("\n\nУслуги не указаны.");
        }
        simpleMailMessage.setText(messageBuilder.toString());

//        emailSender.send(simpleMailMessage);
    }
}
