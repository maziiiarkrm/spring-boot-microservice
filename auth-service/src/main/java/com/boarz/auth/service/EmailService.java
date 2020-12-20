package com.boarz.auth.service;

public interface EmailService {
    void sendEmail(String from, String to, String subject, String content);

}
