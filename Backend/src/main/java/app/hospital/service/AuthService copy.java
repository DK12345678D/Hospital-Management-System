// package app.hospital.service;

// import java.time.LocalDateTime;
// import java.util.Map;
// import java.util.Optional;

// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.stereotype.Service;

// import app.exam.model.CommonOtp;
// import app.exam.repo.CommonOtpRepository;
// import app.hospital.dto.request.ChangePasswordRequest;
// import app.hospital.dto.request.ForgotPasswordRequest;
// import app.hospital.dto.request.LoginRequest;
// import app.hospital.dto.request.RegisterRequest;
// import app.hospital.dto.request.ResetPasswordRequest;
// import app.hospital.exception.ResourceNotFoundException;
// import app.hospital.model.Admin;
// import app.hospital.model.Candidate;
// import app.hospital.repository.AdminRepository;
// import app.hospital.repository.CandidateRepository;
// import app.hospital.security.JwtUtil;

// @Service
// public class AuthService {

// 	private final AdminRepository adminRepository;
// 	private final CandidateRepository candidateRepository;
// 	private final PasswordEncoder passwordEncoder;
// 	private final EmailService emailService;
// 	private final JwtUtil jwtUtil;
// 	private final CommonOtpRepository commonOtpRepository;
// 	private final SmsService smsService;
// 	private final AuditLogService auditLogService;

// 	public AuthService(AdminRepository adminRepository, CandidateRepository candidateRepository,
// 			PasswordEncoder passwordEncoder, JwtUtil jwtUtil, EmailService emailService,
// 			CommonOtpRepository commonOtpRepository, SmsService smsService, AuditLogService auditLogService) {
// 		this.adminRepository = adminRepository;
// 		this.candidateRepository = candidateRepository;
// 		this.passwordEncoder = passwordEncoder;
// 		this.jwtUtil = jwtUtil;
// 		this.emailService = emailService;
// 		this.commonOtpRepository = commonOtpRepository;
// 		this.smsService = smsService;
// 		this.auditLogService = auditLogService;
// 	}

// 	public Map<String, Object> register(RegisterRequest request) {

// 		String phone = request.getPhoneNumber();

// 		if (!phone.matches("^[0-9]{10}$")) {
// 			throw new IllegalArgumentException("Phone number must be exactly 10 digits");
// 		}

// 		if (!request.getPassword().equals(request.getConfirmPassword())) {
// 			throw new IllegalArgumentException("Passwords do not match");
// 		}

// 		adminRepository.findByPhoneNumber(request.getPhoneNumber()).ifPresent(u -> {
// 			throw new IllegalArgumentException("Phone already registered");
// 		});

// 		if (request.getEmail() != null) {
// 			adminRepository.findByEmail(request.getEmail()).ifPresent(u -> {
// 				throw new IllegalArgumentException("Email already registered");
// 			});
// 		}

// 		Admin user = new Admin();
// 		user.setFullName(request.getFullName());
// 		user.setPhoneNumber(request.getPhoneNumber());
// 		user.setEmail(request.getEmail());

// 		user.setPassword(passwordEncoder.encode(request.getPassword()));

// 		Admin savedUser = adminRepository.save(user);
// 		String token = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getRole());

// 		// Audit log for Admin registration
// 		auditLogService.log(
// 				savedUser.getId().toString(),
// 				"ADMIN",
// 				savedUser.getFullName(),
// 				"Registration",
// 				"New Admin '" + savedUser.getFullName() + "' registered",
// 				"WEB",
// 				"Admin Registration");

// 		return Map.of("message", "Admin registered successfully", "adminId", savedUser.getId(), "token", token);
// 	}

// 	public Map<String, Object> adminRegister(RegisterRequest request) {

// 		String phone = request.getPhoneNumber();

// 		if (!phone.matches("^[0-9]{10}$")) {
// 			throw new IllegalArgumentException("Phone number must be exactly 10 digits");
// 		}

// 		if (!request.getPassword().equals(request.getConfirmPassword())) {
// 			throw new IllegalArgumentException("Passwords do not match");
// 		}

// 		adminRepository.findByPhoneNumber(request.getPhoneNumber()).ifPresent(u -> {
// 			throw new IllegalArgumentException("Phone already registered");
// 		});

// 		if (request.getEmail() != null) {
// 			adminRepository.findByEmail(request.getEmail()).ifPresent(u -> {
// 				throw new IllegalArgumentException("Email already registered");
// 			});
// 		}

// 		Admin user = new Admin();
// 		user.setFullName(request.getFullName());
// 		user.setPhoneNumber(request.getPhoneNumber());
// 		user.setEmail(request.getEmail());
// 		user.setRole("ROLE_ADMIN");

// 		user.setPassword(passwordEncoder.encode(request.getPassword()));

// 		Admin savedUser = adminRepository.save(user);
// 		String token = jwtUtil.generateToken(savedUser.getPhoneNumber(), savedUser.getRole());

// 		return Map.of("message", "Admin registered successfully", "adminId", savedUser.getId(), "token", token);
// 	}

// 	public Map<String, Object> login(LoginRequest request) {
// 		String identifier = request.getIdentifier();
// 		Admin user;
// 		if (identifier.contains("@")) {
// 			user = adminRepository.findByEmail(identifier)
// 					.orElseThrow(() -> new ResourceNotFoundException("Admin Email ID not found"));
// 		} else {
// 			user = adminRepository.findByPhoneNumber(identifier)
// 					.orElseThrow(() -> new ResourceNotFoundException("Admin Phone Number not found"));
// 		}

// 		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
// 			throw new ResourceNotFoundException("Invalid password");
// 		}

// 		String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

// 		// Audit log for Admin login
// 		auditLogService.log(
// 				user.getId().toString(),
// 				"ADMIN",
// 				user.getFullName(),
// 				"Login",
// 				"Admin '" + user.getFullName() + "' logged in",
// 				"WEB",
// 				"Admin Authentication");

// 		return Map.of("token", token, "admin",
// 				Map.of("id", user.getId(), "fullName", user.getFullName(), "email", user.getEmail()));
// 	}

// 	public Map<String, Object> candidateRegister(RegisterRequest request) {

// 		String phone = request.getPhoneNumber();

// 		if (!phone.matches("^[0-9]{10}$")) {
// 			throw new IllegalArgumentException("Phone number must be exactly 10 digits");
// 		}

// 		if (!request.getPassword().equals(request.getConfirmPassword())) {
// 			throw new IllegalArgumentException("Passwords do not match");
// 		}

// 		if (candidateRepository.existsByMobileNumber(request.getPhoneNumber())) {
// 			throw new IllegalArgumentException("Phone already registered");
// 		}

// 		if (request.getEmail() != null && candidateRepository.existsByEmail(request.getEmail())) {
// 			throw new IllegalArgumentException("Email already registered");
// 		}

// 		Candidate user = new Candidate();
// 		user.setCandidateName(request.getFullName());
// 		user.setMobileNumber(request.getPhoneNumber());
// 		user.setEmail(request.getEmail());
// 		user.setRole("ROLE_CANDIDATE");
// 		user.setPassword(passwordEncoder.encode(request.getPassword()));
// 		user.setGroupId(request.getGroupId());

// 		app.exam.model.Candidate savedUser = candidateRepository.save(user);
// 		String token = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getRole());

// 		// Audit log
// 		auditLogService.log(
// 				savedUser.getId().toString(),
// 				"CANDIDATE",
// 				savedUser.getCandidateName(),
// 				"Registration",
// 				savedUser.getCandidateName() + " registered successfully",
// 				"WEB",
// 				"Candidate Registration");

// 		return Map.of("message", "Candidate registered successfully", "candidateId", savedUser.getId(), "token", token);
// 	}

// 	public Map<String, Object> candidateLogin(LoginRequest request) {
// 		String identifier = request.getIdentifier();
// 		app.exam.model.Candidate user;
// 		if (identifier.contains("@")) {
// 			user = candidateRepository.findByEmail(identifier)
// 					.orElseThrow(() -> new ResourceNotFoundException("Candidate Email ID not found"));
// 		} else {
// 			user = candidateRepository.findByMobileNumber(identifier)
// 					.orElseThrow(() -> new ResourceNotFoundException("Candidate Phone Number not found"));
// 		}

// 		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
// 			throw new ResourceNotFoundException("Invalid password");
// 		}

// 		String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

// 		return Map.of("token", token, "candidate",
// 				Map.of("id", user.getId(), "fullName", user.getCandidateName(), "email", user.getEmail()));
// 	}

// 	public void forgotPassword(ForgotPasswordRequest request) {
// 		String identifier = request.getIdentifier();
// 		String destination = null;

// 		// Check AdminRepository
// 		if (identifier.contains("@")) {
// 			destination = adminRepository.findByEmail(identifier).map(Admin::getEmail).orElse(null);
// 		} else {
// 			destination = adminRepository.findByPhoneNumber(identifier).map(Admin::getPhoneNumber).orElse(null);
// 		}

// 		// If not found in Admin, check CandidateRepository
// 		if (destination == null) {
// 			if (identifier.contains("@")) {
// 				destination = candidateRepository.findByEmail(identifier).map(Candidate::getEmail).orElse(null);
// 			} else {
// 				destination = candidateRepository.findByMobileNumber(identifier).map(Candidate::getMobileNumber)
// 						.orElse(null);
// 			}
// 		}

// 		if (destination == null) {
// 			throw new ResourceNotFoundException(
// 					"User not found with provided " + (identifier.contains("@") ? "email" : "phone number"));
// 		}

// 		// Cleanup expired (only if not blocked)
// 		CommonOtp existingOtp = commonOtpRepository.findByIdentifier(destination).orElse(null);
// 		if (existingOtp != null && existingOtp.getBlockedUntil() != null) {
// 			if (existingOtp.getBlockedUntil().isAfter(LocalDateTime.now())) {
// 				throw new RuntimeException("Account temporarily blocked. Try again later.");
// 			} else {
// 				commonOtpRepository.delete(existingOtp);
// 				existingOtp = null;
// 			}
// 		} else if (existingOtp != null && existingOtp.getExpiresAt().isBefore(LocalDateTime.now())) {
// 			commonOtpRepository.delete(existingOtp);
// 			existingOtp = null;
// 		}

// 		if (existingOtp != null) {
// 			long secondsSinceLast = java.time.Duration.between(existingOtp.getLastSentAt(), LocalDateTime.now())
// 					.getSeconds();
// 			if (secondsSinceLast < 90) {
// 				throw new ResourceNotFoundException("Please wait for 1 minute 30 seconds before resending OTP");
// 			}
// 			if (existingOtp.getAttemptCount() >= 3) {
// 				throw new ResourceNotFoundException("Max OTP attempts reached. Please wait for 24 hr expiry.");
// 			}
// 		}

// 		// Generate 6-digit OTP
// 		String otp = String.valueOf((int) (Math.random() * 900000) + 100000);
// 		String encryptedOtp = passwordEncoder.encode(otp);

// 		CommonOtp userOtp = existingOtp != null ? existingOtp : new CommonOtp();
// 		userOtp.setIdentifier(destination);
// 		userOtp.setOtpHash(encryptedOtp);
// 		userOtp.setExpiresAt(LocalDateTime.now().plusMinutes(10));
// 		userOtp.setLastSentAt(LocalDateTime.now());
// 		userOtp.setAttemptCount(userOtp.getAttemptCount() + 1);

// 		commonOtpRepository.save(userOtp);

// 		if (destination.contains("@")) {
// 			emailService.sendEmail(destination, "Password Reset OTP", "Your OTP is: " + otp);
// 		} else {
// 			// Send SMS via MSG91
// 			smsService.sendOtp(destination, otp);
// 		}
// 	}

// 	public void resetPassword(ResetPasswordRequest request) {
// 		if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
// 			throw new IllegalArgumentException("Passwords do not match");
// 		}

// 		String identifier = request.getIdentifier();
// 		String otp = request.getOtp().trim();

// 		CommonOtp userOtp = commonOtpRepository.findByIdentifier(identifier)
// 				.orElseThrow(() -> new ResourceNotFoundException("Invalid Request or OTP expired"));

// 		if (userOtp.getBlockedUntil() != null && userOtp.getBlockedUntil().isAfter(LocalDateTime.now())) {
// 			throw new ResourceNotFoundException("Account temporarily blocked. Try again later.");
// 		}

// 		if (userOtp.getExpiresAt().isBefore(LocalDateTime.now())) {
// 			if (userOtp.getBlockedUntil() == null) {
// 				commonOtpRepository.delete(userOtp);
// 			}
// 			throw new ResourceNotFoundException("OTP expired");
// 		}

// 		if (!passwordEncoder.matches(otp, userOtp.getOtpHash())) {
// 			userOtp.setFailedAttempts(userOtp.getFailedAttempts() + 1);
// 			if (userOtp.getFailedAttempts() >= 3) {
// 				userOtp.setBlockedUntil(LocalDateTime.now().plusHours(24));
// 				commonOtpRepository.save(userOtp);
// 				throw new ResourceNotFoundException("Account blocked. Try again in 24 hours.");
// 			}
// 			commonOtpRepository.save(userOtp);
// 			throw new ResourceNotFoundException("Invalid OTP. Attempts left: " + (3 - userOtp.getFailedAttempts()));
// 		}

// 		// Update Admin or Candidate password
// 		boolean updated = false;

// 		// Check Admin
// 		Optional<Admin> adminOpt = identifier.contains("@") ? adminRepository.findByEmail(identifier)
// 				: adminRepository.findByPhoneNumber(identifier);
// 		if (adminOpt.isPresent()) {
// 			Admin admin = adminOpt.get();
// 			admin.setPassword(passwordEncoder.encode(request.getNewPassword()));
// 			adminRepository.save(admin);
// 			updated = true;
// 		} else {
// 			// Check Candidate
// 			Optional<Candidate> candOpt = identifier.contains("@") ? candidateRepository.findByEmail(identifier)
// 					: candidateRepository.findByMobileNumber(identifier);
// 			if (candOpt.isPresent()) {
// 				Candidate cand = candOpt.get();
// 				cand.setPassword(passwordEncoder.encode(request.getNewPassword()));
// 				candidateRepository.save(cand);
// 				updated = true;
// 			}
// 		}

// 		if (!updated) {
// 			throw new ResourceNotFoundException("User not found during password reset");
// 		}

// 		// Delete used OTP
// 		commonOtpRepository.delete(userOtp);
// 	}

// 	public void changePassword(Object principal, ChangePasswordRequest request) {
// 		if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
// 			throw new IllegalArgumentException("New passwords do not match");
// 		}

// 		if (principal instanceof Admin) {
// 			Admin user = (Admin) principal;

// 			if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
// 				throw new RuntimeException("Invalid old password");
// 			}
// 			user.setPassword(passwordEncoder.encode(request.getNewPassword()));
// 			adminRepository.save(user);
// 		} else if (principal instanceof Candidate) {
// 			Candidate user = (Candidate) principal;

// 			if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
// 				throw new RuntimeException("Invalid old password");
// 			}
// 			user.setPassword(passwordEncoder.encode(request.getNewPassword()));
// 			candidateRepository.save(user);
// 		} else {
// 			throw new RuntimeException("Unknown User type");
// 		}
// 	}
// }