
package com.example.lerningai.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    // Constructor injection is better practice than @Autowired on fields
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendAIResponse(String toEmail, String userName, String aiResponse) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            // "true" indicates this is a multipart message (allows HTML and attachments)
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Your Resume Analysis Report 📄 - Smart Place ");

            // Building a cleaner HTML template
            String htmlContent = String.format(
                    "<div style='font-family: Arial, sans-serif; line-height: 1.6; color: #333;'>" +
                            "<h2>Hello %s,</h2>" +
                            "<p>Your AI Resume Analysis is ready:</p>" +
                            "<div style='background:#f4f4f4; padding:15px; border-radius:8px; border-left: 5px solid #007bff; white-space: pre-wrap;'>" +
                            "%s" +
                            "</div>" +
                            "<br><p>Regards,<br><strong>SmartPlace.com - AI Resume Analyzer By Abhishek Meena </strong></p>" +
                            "</div>",
                    userName, aiResponse
            );

            // Set text to true to enable HTML rendering
            helper.setText(htmlContent, true);

            mailSender.send(message);
            System.out.println("Email sent successfully to: " + toEmail);

        } catch (MessagingException e) {
            // Use a logger in production instead of printStackTrace
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}