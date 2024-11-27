package com.codecool.codekickfc.controller.email;

import com.codecool.codekickfc.controller.dto.email.EmailConfirmationDTO;
import com.codecool.codekickfc.controller.dto.email.EmailDTO;
import com.codecool.codekickfc.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

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

