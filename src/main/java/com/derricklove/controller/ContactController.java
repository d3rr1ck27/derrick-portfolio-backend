package com.derricklove.controller;

import com.derricklove.model.ContactMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ContactController {

    private final JavaMailSender mailSender;

    @PostMapping
    public String sendMessage(@RequestBody ContactMessage message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("derricklove2704@gmail.com");
        mail.setTo("derricklovebus@gmail.com");
        mail.setSubject("Portfolio Contact: " + message.getSubject());
        mail.setText(
                "Name: " + message.getName() + "\n" +
                        "Email: " + message.getEmail() + "\n\n" +
                        "Message:\n" + message.getMessage()
        );
        mailSender.send(mail);
        return "Message sent successfully";
    }
}