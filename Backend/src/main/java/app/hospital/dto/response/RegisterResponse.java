package app.hospital.dto.response;

import lombok.Data;
@Data
public class RegisterResponse {
	private String email;
	private String message;

	public RegisterResponse(String email, String message) {
		this.email = email;
		this.message = message;
	}

}
