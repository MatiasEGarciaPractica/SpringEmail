package com.sending.mail.service;

import com.sending.mail.dto.SendEmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl {

    private final JavaMailSender javaMailSender; //config in MailConfig
    private final SimpleMailMessage templateMessage;
    private final TemplateEngine templateEngine;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, SimpleMailMessage simpleMailMessage, TemplateEngine templateEngine){
        this.javaMailSender = javaMailSender;
        this.templateMessage = simpleMailMessage;
        this.templateEngine = templateEngine;
    }

    /**
     * Basic email , just text only emails. without formatting or attachments-
     * @param sendEmailDto - object with mail info.
     */
    public void sendBasicEmail(SendEmailDto sendEmailDto){
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo(sendEmailDto.target());
        msg.setText(sendEmailDto.message());
        msg.setSubject(sendEmailDto.issue());
        try{
            this.javaMailSender.send(msg);
        }catch(MailException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Send email with MimeMessageHelper( More complex mails, with multimedia, or attachments).
     * Send image of charizard
     * @param sendEmailDto - object with mail info.
     * @throws MessagingException - if there was an error creating email.
     */
    public void sendEmailWithImg(SendEmailDto sendEmailDto) throws MessagingException {
        MimeMessage message = this.javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true, "UTF-8");
        helper.setTo(sendEmailDto.target());
        helper.setText("Mira esta imagen");

        FileSystemResource file = new FileSystemResource("src/main/resources/static/img/CharizardImg.jpg");
        helper.addAttachment("CharizarImg.jpg", file);

        try{
            this.javaMailSender.send(message);
        }catch(MailException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Send email with html content.
     * @param sendEmailDto- object with mail info.
     * @throws MessagingException - if there was an error creating email.
     */
    public void sendEmailHtml(SendEmailDto sendEmailDto) throws MessagingException {
       try{
           MimeMessage message = this.javaMailSender.createMimeMessage();
           MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

           Context context = new Context();
           context.setVariable("message", sendEmailDto.message());
           String contentHtml = templateEngine.process("email", //same name that html template
                   context);

           helper.setTo(sendEmailDto.target());
           helper.setText(contentHtml, true); //without true, send plane text
           javaMailSender.send(message);
       }catch(Exception e){
           System.err.println(e.getMessage());
       }
    }

}
