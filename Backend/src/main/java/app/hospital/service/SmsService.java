package app.hospital.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.util.HashMap;
import java.util.Map;

@Service
public class SmsService {

    @Value("${msg91.authkey}")
    private String authKey;

    @Value("${msg91.template_id}")
    private String templateId;

    @Value("${msg91.url.send}")
    private String sendUrl;

    private final RestTemplate restTemplate;

    public SmsService() {
        this.restTemplate = new RestTemplate();
    }

    public void sendOtp(String phoneNumber, String otp) {
        try {
            // MSG91 v5 API requires authkey in headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("authkey", authKey);

            // Construct payload according to MSG91 v5 documentation
            Map<String, Object> payload = new HashMap<>();
            payload.put("template_id", templateId);
            payload.put("mobile", "91" + phoneNumber); // Assuming Indian 10-digit number
            payload.put("otp", otp);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

            restTemplate.postForEntity(sendUrl, request, String.class);
            System.out.println("OTP " + otp + " sent to " + phoneNumber + " via MSG91");
            
        } catch (Exception e) {
            System.err.println("Failed to send SMS via MSG91: " + e.getMessage());
            // In a real scenario, you might want to throw a custom exception or handle it more gracefully
        }
    }
}
