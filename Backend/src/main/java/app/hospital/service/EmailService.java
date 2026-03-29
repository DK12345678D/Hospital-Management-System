package app.hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            System.out.println("Email sent successfully to " + to);
        } catch (Exception e) {
            System.err.println("Error sending email to " + to + ": " + e.getMessage());
           
        }
    }

    /**
     * Sends a report to multiple recipients
     */
    public void sendReportEmail(app.hospital.dto.request.EmailRequest request) {
        String body = request.getMessage() != null ? request.getMessage() : "Please find the requested report below.";
        String subject = request.getSubject() != null ? request.getSubject() : "Exam Portal Report - " + request.getReportType();

        for (String recipient : request.getRecipients()) {
            sendEmail(recipient, subject, body);
        }
    }
}
