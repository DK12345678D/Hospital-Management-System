package app.hospital.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.hospital.service.LogoutService;

import app.hospital.dto.request.ChangePasswordRequest;
import app.hospital.dto.request.ForgotPasswordRequest;
import app.hospital.dto.request.LoginRequest;
import app.hospital.dto.request.RegisterRequest;
import app.hospital.dto.request.ResetPasswordRequest;
import app.hospital.service.AdminService;
import app.hospital.service.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AdminService userService;
	private final AuthService authService;
	private final LogoutService logoutService;

	public AuthController(AdminService userService, AuthService authService, LogoutService logoutService) {
		this.userService = userService;
		this.authService = authService;
		this.logoutService = logoutService;

	}

	@PostMapping("/Admin/SignUp")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
		Map<String, Object> response = authService.register(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/Admin/SignIn")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		Map<String, Object> result = authService.login(request);
		return ResponseEntity.ok(Map.of("message", "Login successful", "data", result));
	}

	@PostMapping("/Doctor/SignUp")
	public ResponseEntity<?> doctorRegister(@Valid @RequestBody RegisterRequest request) {
		Map<String, Object> response = authService.registerDoctor(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/Doctor/SignIn")
	public ResponseEntity<?> doctorLogin(@RequestBody LoginRequest request) {
		Map<String, Object> result = authService.loginDoctor(request);
		return ResponseEntity.ok(Map.of("message", "Login successful", "data", result));
	}

	@PostMapping("/Patient/SignUp")
	public ResponseEntity<?> patientRegister(@Valid @RequestBody RegisterRequest request) {
		Map<String, Object> response = authService.registerPatient(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/Patient/SignIn")
	public ResponseEntity<?> patientLogin(@RequestBody LoginRequest request) {
		Map<String, Object> result = authService.loginPatient(request);
		return ResponseEntity.ok(Map.of("message", "Login successful", "data", result));
	}

	@PostMapping("/update-profile")
	public ResponseEntity<?> updateProfile(@RequestBody RegisterRequest request, Authentication auth) {
		if (auth == null || !auth.isAuthenticated()) {
			return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
		}
		Map<String, Object> response = authService.updateProfile(auth.getPrincipal(), request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("logout")
	public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader) {
		logoutService.logout(authHeader);
		return ResponseEntity.ok(Map.of("message", "logout successfully"));
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
		authService.forgotPassword(request);
		return ResponseEntity.ok(Map.of("message", "Reset OTP sent to your registered contact"));
	}

	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
		authService.resetPassword(request);
		return ResponseEntity.ok(Map.of("message", "Password reset successfully"));
	}

	@PostMapping("/change-password")
	public ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordRequest request, Authentication auth) {
		if (auth == null || !auth.isAuthenticated()) {
			return ResponseEntity.status(401).body(Map.of("error", "Unauthorized"));
		}
		authService.changePassword(auth.getPrincipal(), request);
		return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
	}

}