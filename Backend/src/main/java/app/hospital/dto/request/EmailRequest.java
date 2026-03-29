package app.hospital.dto.request;

import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class EmailRequest {
    private String reportType;
    private Map<String, Object> filters;

    @NotEmpty(message = "Recipients cannot be empty")
    private List<@Email String> recipients;

    private String subject;
    private String message;
}
