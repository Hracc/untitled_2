package com.agregator.Agregator.Services;

import com.agregator.Agregator.Controllers.TestController;
import com.agregator.Agregator.Entity.AggregatorSpecialist;
import com.agregator.Agregator.Entity.Customer;
import com.agregator.Agregator.Entity.User;
import com.agregator.Agregator.Enums.UserRole;
import com.agregator.Agregator.Repositories.CustomerRepository;
import com.agregator.Agregator.Repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class AuthService {

    private static final long CODE_LIFETIME_MILLIS = 5 * 60 * 1000; // 5 минут
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final Map<String, VerificationData> verificationCodes = new ConcurrentHashMap<>();
    private final Map<String, VerificationData> verificationAdmin = new ConcurrentHashMap<>();
    private final Map<String, VerificationData> verificationOrganization = new ConcurrentHashMap<>();

    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;


    public String sendVerificationCode(String email) {
        if(!isValidEmail(email)){
            return "Неверный формат email";
        }
        try {
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isEmpty()) {
                logger.info(email);
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setRole(UserRole.CUSTOMER);
                userRepository.save(newUser);


                Customer newcustomer = new Customer();
                newcustomer.setEmail(email);
                customerRepository.save(newcustomer);
            }else {
                if (!user.get().getRole().equals(UserRole.CUSTOMER)){
                    throw new RuntimeException("Ошибка авторизации");
                }
            }
        } catch (Exception e) {
            return "Ошибка при проверке пользователя";
        }
        String code = generateCode();
        verificationCodes.put(email, new VerificationData(code, Instant.now()));

        // Логируем вместо отправки SMS (замени на реальную отправку)

        emailService.sendCodeToEmail(email,code);
        logger.info("Отправлен код: " + code + " на почту: " + email);
        return "Код отправлен";
    }

    public String sendVerificationCodeAdmin(String email) {
        if(!isValidEmail(email)){
            return "Неверный формат email";
        }
        try {
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isEmpty()) {
                logger.info(email);
                logger.info("Попытка подбора Email у админа");
                throw new RuntimeException("Ошибка авторизации");
            }
            if (!user.get().getRole().equals(UserRole.ADMINISTRATION)){
                throw new RuntimeException("Ошибка авторизации");
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка авторизации");
        }
        String code = generateCode();
        verificationAdmin.put(email, new VerificationData(code, Instant.now()));

        // Логируем вместо отправки SMS (замени на реальную отправку)

        emailService.sendCodeToEmail(email,code);
        logger.info("Отправлен код: " + code + " на почту: " + email);
        return "Код отправлен";
    }

    public String verifyCode(String email, String code) {
        if(!isValidEmail(email)){
            return "Неверный формат email";
        }
        VerificationData data = verificationCodes.get(email);
        logger.info("data " + data, data.code);
        if (data != null && data.code().equals(code) && isCodeValid(data)) {
            verificationCodes.remove(data);

            String token = JwtService.generateToken(email, UserRole.CUSTOMER);

            logger.info("Код успешно Введен"+ email);
            logger.info("Токен: "+ token);
            return token;
        }
        return "false";
    }

    public String sendVerificationCodeOrganization(String email) {
        if(!isValidEmail(email)){
            return "Неверный формат email";
        }
        try {
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isEmpty()) {
                logger.info(email);
                logger.info("Попытка подбора Email у админа");
                throw new RuntimeException("Ошибка авторизации");
            }else {
                if (!user.get().getRole().equals(UserRole.ORGANIZATION)){
                    throw new RuntimeException("Ошибка авторизации");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Ошибка авторизации");
        }
        String code = generateCode();
        verificationOrganization.put(email, new VerificationData(code, Instant.now()));

        // Логируем вместо отправки SMS (замени на реальную отправку)

        emailService.sendCodeToEmail(email,code);
        logger.info("Отправлен код: " + code + " на почту: " + email);
        return "Код отправлен";
    }

    public String verifyCodeOrganization(String email, String code) {
        if(!isValidEmail(email)){
            return "Неверный формат email";
        }
        VerificationData data = verificationOrganization.get(email);
        logger.info("data " + data, data.code);
        if (data != null && data.code().equals(code) && isCodeValid(data)) {
            verificationOrganization.remove(data);

            String token = JwtService.generateToken(email, UserRole.ORGANIZATION);

            logger.info("Код успешно Введен"+ email);
            logger.info("Токен: "+ token);
            return token;
        }
        return "false";
    }


    public String verifyCodeAdmin(String email, String code) {
        if(!isValidEmail(email)){
            return "Неверный формат email";
        }
        VerificationData data = verificationAdmin.get(email);
        logger.info("data " + data, data.code);
        if (data != null && data.code().equals(code) && isCodeValid(data)) {
            verificationAdmin.remove(data);

            String token = JwtService.generateToken(email, UserRole.ADMINISTRATION);

            logger.info("Код успешно Введен"+ email);
            logger.info("Токен: "+ token);
            return token;
        }
        return "false";
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }
    private String generateCode() {
        return String.format("%04d", (int) (Math.random() * 1000)); //0.1456 => 1456
    }

    private boolean isCodeValid(VerificationData data) {
        return Instant.now().toEpochMilli() - data.timestamp().toEpochMilli() <= CODE_LIFETIME_MILLIS;
    }


    //Очистка просроченных кодов каждые 30 секунд
    @Scheduled(fixedRate = 30000)
    public void removeExpiredCodes() {
        Instant now = Instant.now();
        Iterator<Map.Entry<String, VerificationData>> iterator = verificationCodes.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, VerificationData> entry = iterator.next();
            if (now.toEpochMilli() - entry.getValue().timestamp().toEpochMilli() > CODE_LIFETIME_MILLIS) {
                iterator.remove();
                System.out.println("Удалён просроченный код для номера: " + entry.getKey());
            }
        }
    }

    private record VerificationData(String code, Instant timestamp) {}
}

