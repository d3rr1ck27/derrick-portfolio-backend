package com.derricklove.controller;

import com.derricklove.model.ContactMessage;
import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = {"http://localhost:3000", "https://derrick-portfollio-frontend.vercel.app"})
@RequiredArgsConstructor
public class ContactController {

    @Value("${RESEND_API_KEY}")
    private String resendApiKey;

    @PostMapping
    public String sendMessage(@RequestBody ContactMessage message) {
        try {
            Resend resend = new Resend(resendApiKey);

            CreateEmailOptions params = CreateEmailOptions.builder()
                .from("Portfolio Contact <onboarding@resend.dev>")
                .to("derricklovebus@gmail.com")
                .subject("Portfolio Contact: " + message.getSubject())
                .html(
                    "<p><strong>Name:</strong> " + message.getName() + "</p>" +
                    "<p><strong>Email:</strong> " + message.getEmail() + "</p>" +
                    "<p><strong>Message:</strong><br>" + message.getMessage() + "</p>"
                )
                .build();

            CreateEmailResponse response = resend.emails().send(params);
            return "Message sent successfully";

        } catch (ResendException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
}
