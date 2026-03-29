package app.hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmailService emailService;
    private final SmsService smsService;

    public void sendAppointmentConfirmation(String email, String phone, String appointmentTime, String doctorName) {
        String subject = "Appointment Confirmation - Hospital Management System";
        String message = String.format("Dear Patient, your appointment with Dr. %s is confirmed for %s.", doctorName, appointmentTime);
        
        if (email != null) {
            emailService.sendEmail(email, subject, message);
        }
        
        if (phone != null) {
            // Using a simple message instead of OTP for generic notifications if SmsService allows
            // or just log it if SmsService is only for OTP
            System.out.println("SMS notification sent to " + phone + ": " + message);
        }
    }

    public void sendRegistrationSuccess(String email, String name) {
        String subject = "Welcome to Hospital Management System";
        String message = String.format("Hello %s, your registration was successful. Welcome to our healthcare family!", name);
        
        if (email != null) {
            emailService.sendEmail(email, subject, message);
        }
    }

    public void sendBillNotification(String email, String patientName, Double amount) {
        String subject = "New Bill Generated";
        String message = String.format("Hello %s, a new bill of amount %.2f has been generated for your recent visit.", patientName, amount);
        
        if (email != null) {
            emailService.sendEmail(email, subject, message);
        }
    }
}
