package org.example.bookingnotifier.service;

import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String recipient, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("cal857723@gmail.com"); // Add this line
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(body, true); // Set 'true' for HTML support

            mailSender.send(message);
            logger.info("✅ Email sent successfully to: {}", recipient);
        } catch (Exception e) {
            logger.error("❌ Failed to send email to {}: {}", recipient, e.getMessage());
            e.printStackTrace();
        }
    }
}
