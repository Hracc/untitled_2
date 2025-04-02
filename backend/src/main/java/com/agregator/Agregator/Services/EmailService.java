package com.agregator.Agregator.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
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


        //emailSender.send(simpleMailMessage);
    }
}
