package com.sending.mail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.beans.factory.annotation.Value;

import java.util.Properties;
import java.util.ResourceBundle;

@Configuration
@PropertySource("classpath:email.properties")
public class EmailConfig {

    @Value("${email.username}")
    private String email;
    @Value("${email.password}")
    private String password;

    private Properties getMailProperties(){
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls" + ".enable", "true");
        prop.put("mail.smtp.host", "smtp" + ".gmail.com");// it depend of you mail provider
        prop.put("mail.smtp.port", "587"); // port depend on your mail provider(this one is for gmail.)
        //next are to avoid a thread block.
        prop.put("mail.smtp.connectiontimeout", "5000");
        prop.put("mail.smtp.timeout", "3000");
        prop.put("mail.smtp.writetimeout", "5000");
        return prop;
    }

    @Bean //java email sender object to send emails later.
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setJavaMailProperties(this.getMailProperties());
        mailSender.setUsername(email);
        mailSender.setPassword(password);
        return mailSender;
    }

    @Bean
    public SimpleMailMessage templateMessage(){
        SimpleMailMessage s = new SimpleMailMessage();//I suppose that uses the javaMailSender bean
        s.setFrom(email);
        return s;
    }

    @Bean
    public ResourceLoader ResourceLoader(){
        return new DefaultResourceLoader();
    }
}
