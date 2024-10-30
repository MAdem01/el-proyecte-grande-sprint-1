package com.codecool.codekickfc.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${EMAIL_USERNAME}")
    private String EMAIL_USERNAME;

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String subject, String description) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setSubject(subject);
            message.setText(description);
            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(EMAIL_USERNAME));

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}

