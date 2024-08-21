package com.sgic.semita.services;

public interface EmailService {
    void sendEmail(String userName, String to, String subject, String body);
}
