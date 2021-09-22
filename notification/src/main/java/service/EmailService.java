package service;


import org.springframework.beans.factory.annotation.Value;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.example.emmiter.service.dto.EmployeeAlertDTO;

@Service
public class EmailService {

    private JavaMailSender emailSender;

    @Value("${alert.distribution-list}")
    private String distributionList;
    public EmailService(JavaMailSender emailSender){

        this.emailSender = emailSender;
    }

    public void sendSimpleMessage(EmployeeAlertDTO alertDTO){

        try {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(distributionList);
            message.setSubject("Store Alert: " + alertDTO.getStoreName());
            message.setText(alertDTO.getStoreStatus());
            message.setFrom("StoreAlert");
            emailSender.send(message);
        } catch (Exception exception) {
            throw new EmailServiceException(exception);
        }
    }
}
