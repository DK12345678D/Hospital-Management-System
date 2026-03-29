package app.hospital.dto.response;

public class JwtResponse {

	private String token;
	private String email;

	public JwtResponse(String token, String email) {
		this.token = token;
		this.email = email;
	}
}
