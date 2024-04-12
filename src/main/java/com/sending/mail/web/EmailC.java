package com.sending.mail.web;

import com.sending.mail.dto.SendEmailDto;
import com.sending.mail.service.EmailServiceImpl;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class EmailC {

    @Autowired
    EmailServiceImpl webService;

    @PostMapping("/basicEmail")
    private ResponseEntity<String> sendEmail(@RequestBody SendEmailDto dto){
        webService.sendBasicEmail(dto);
        return ResponseEntity.ok("email sent successfully");
    }

    @PostMapping("/sendEmailWithImg")
    private ResponseEntity<String> sendEmailWithImg(@RequestBody SendEmailDto dto) throws MessagingException {
        webService.sendEmailWithImg(dto);
        return ResponseEntity.ok("email sent successfully");
    }

    @PostMapping("/sendEmailHtml")
    private ResponseEntity<String> sendEmailHtml(@RequestBody SendEmailDto dto) throws MessagingException {
        webService.sendEmailHtml(dto);
        return ResponseEntity.ok("email sent successfully");
    }
}
