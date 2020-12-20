package com.boarz.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;

@Transactional
@Component
public class EmailServiceImp implements EmailService {

    @Autowired
    public JavaMailSender emailSender;

    @Override
    public void sendEmail(String from, String to, String subject, String content) {
        sendMail(from, to, subject, content);
    }

    private void sendSimpleMessage(String from, String to, String subject, String text) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text, true);
            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void sendMail(String from, String to, String subject, String text) {
        sendSimpleMessage(from, to, subject, text);
    }


}



