package com.codecool.codekickfc.controller;

import com.codecool.codekickfc.dto.email.EmailConfirmationDTO;
import com.codecool.codekickfc.dto.email.EmailDTO;
import com.codecool.codekickfc.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailDTO request) {
        emailService.sendEmail(request.subject(), request.description());
        return "Email sent successfully!";
    }

    @PostMapping("/confirmation")
    public String confirmationEmail(@RequestBody EmailConfirmationDTO request) {
        emailService.sendConfirmationEmail(request);
        return "Email sent successfully!";
    }
}

