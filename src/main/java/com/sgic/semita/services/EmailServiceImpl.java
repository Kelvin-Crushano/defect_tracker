package com.sgic.semita.services;

import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private Environment env;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String userName, String to, String subject, String body) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            try {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                String fromEmail = env.getProperty("mail.from", "n.sathuzan@gmail.com");
                helper.setFrom(fromEmail);
                helper.setTo(to);
                helper.setSubject(subject);
                Context context = new Context();
                context.setVariable("name", userName);
                context.setVariable("appName", "Semita");
                context.setVariable("body", body);
                String content = templateEngine.process("genericEmailTemplate", context);
                helper.setText(content, true);
            } catch (MessagingException e) {
                logger.error("Error while sending email to " + to, e);
                throw new RuntimeException("Error while sending email", e);
            }
        };

        mailSender.send(messagePreparator);
    }
}
