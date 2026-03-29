package app.hospital.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
	private String identifier; // email or phone number
	private String password;
}