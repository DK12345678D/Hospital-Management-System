package app.hospital.dto.response;

import lombok.Data;

@Data

public class LoginResponse {
	
	private String email;
    private String name;
	private String token;
	

}
