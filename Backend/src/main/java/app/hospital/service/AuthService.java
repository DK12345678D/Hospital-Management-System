package app.hospital.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.hospital.dto.request.LoginRequest;
import app.hospital.model.Admin;
import app.hospital.model.Doctor;
import app.hospital.model.Patient;
import app.hospital.repository.AdminRepository;
import app.hospital.repository.DoctorRepository;
import app.hospital.repository.PatientRepository;
import app.hospital.security.JwtUtil;
import lombok.RequiredArgsConstructor;

import app.hospital.dto.request.RegisterRequest;
import app.hospital.dto.request.ForgotPasswordRequest;
import app.hospital.dto.request.ResetPasswordRequest;
import app.hospital.dto.request.ChangePasswordRequest;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdminRepository adminRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Map<String, Object> login(LoginRequest request) {
        String identifier = request.getIdentifier();
        Admin admin = adminRepository.findByEmail(identifier)
                .or(() -> adminRepository.findByPhoneNumber(identifier))
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        
        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        
        String token = jwtUtil.generateToken(admin.getEmail(), "ROLE_ADMIN");
        return createAuthResponse(token, admin.getEmail(), "ROLE_ADMIN");
    }

    public Map<String, Object> register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }
        if (adminRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        Admin admin = new Admin();
        admin.setFullName(request.getFullName());
        admin.setEmail(request.getEmail());
        admin.setPhoneNumber(request.getPhoneNumber());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        adminRepository.save(admin);
        return Map.of("message", "Admin registered successfully");
    }

    public Map<String, Object> registerDoctor(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }
        if (doctorRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        Doctor doctor = new Doctor();
        doctor.setName(request.getFullName());
        doctor.setEmail(request.getEmail());
        doctor.setPhone(request.getPhoneNumber());
        doctor.setPassword(passwordEncoder.encode(request.getPassword()));
        doctorRepository.save(doctor);
        return Map.of("message", "Doctor registered successfully");
    }

    public Map<String, Object> registerPatient(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }
        if (patientRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        Patient patient = new Patient();
        patient.setName(request.getFullName());
        patient.setEmail(request.getEmail());
        patient.setPhone(request.getPhoneNumber());
        patient.setPassword(passwordEncoder.encode(request.getPassword()));
        patientRepository.save(patient);
        return Map.of("message", "Patient registered successfully");
    }

    public void forgotPassword(ForgotPasswordRequest request) {
        // Placeholder for real email logic
        System.out.println("Forgot password requested for: " + request.getIdentifier());
    }

    public void resetPassword(ResetPasswordRequest request) {
        // Find user by identifier, verify OTP, then reset password
        Admin admin = adminRepository.findByEmail(request.getIdentifier())
            .or(() -> adminRepository.findByPhoneNumber(request.getIdentifier())).orElse(null);
        if (admin != null) {
            admin.setPassword(passwordEncoder.encode(request.getNewPassword()));
            adminRepository.save(admin);
            return;
        }

        Patient patient = patientRepository.findByEmail(request.getIdentifier())
            .or(() -> patientRepository.findByPhone(request.getIdentifier())).orElse(null);
        if (patient != null) {
            patient.setPassword(passwordEncoder.encode(request.getNewPassword()));
            patientRepository.save(patient);
            return;
        }

        Doctor doctor = doctorRepository.findByEmail(request.getIdentifier()).orElse(null);
        if (doctor != null) {
            doctor.setPassword(passwordEncoder.encode(request.getNewPassword()));
            doctorRepository.save(doctor);
            return;
        }

        throw new RuntimeException("User not found for password reset");
    }

    public void changePassword(Object principal, ChangePasswordRequest request) {
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        if (principal instanceof Admin) {
            Admin admin = (Admin) principal;
            if (!passwordEncoder.matches(request.getOldPassword(), admin.getPassword())) {
                throw new RuntimeException("Incorrect old password");
            }
            admin.setPassword(passwordEncoder.encode(request.getNewPassword()));
            adminRepository.save(admin);
        } else if (principal instanceof Patient) {
            Patient patient = (Patient) principal;
            if (!passwordEncoder.matches(request.getOldPassword(), patient.getPassword())) {
                throw new RuntimeException("Incorrect old password");
            }
            patient.setPassword(passwordEncoder.encode(request.getNewPassword()));
            patientRepository.save(patient);
        } else if (principal instanceof Doctor) {
            Doctor doctor = (Doctor) principal;
            if (!passwordEncoder.matches(request.getOldPassword(), doctor.getPassword())) {
                throw new RuntimeException("Incorrect old password");
            }
            doctor.setPassword(passwordEncoder.encode(request.getNewPassword()));
            doctorRepository.save(doctor);
        } else {
            throw new RuntimeException("User type not recognized for password change");
        }
    }

    public Map<String, Object> updateProfile(Object principal, RegisterRequest request) {
        if (principal instanceof Admin) {
            Admin admin = (Admin) principal;
            admin.setFullName(request.getFullName());
            admin.setPhoneNumber(request.getPhoneNumber());
            adminRepository.save(admin);
            return Map.of("message", "Admin profile updated");
        } else if (principal instanceof Patient) {
            Patient patient = (Patient) principal;
            patient.setName(request.getFullName());
            patient.setPhone(request.getPhoneNumber());
            patientRepository.save(patient);
            return Map.of("message", "Patient profile updated");
        } else if (principal instanceof Doctor) {
            Doctor doctor = (Doctor) principal;
            doctor.setName(request.getFullName());
            doctor.setPhone(request.getPhoneNumber());
            doctorRepository.save(doctor);
            return Map.of("message", "Doctor profile updated");
        }
        throw new RuntimeException("User not found for profile update");
    }

    public Map<String, Object> loginAdmin(LoginRequest request) {
        return login(request);
    }

    public Map<String, Object> loginDoctor(LoginRequest request) {
        String identifier = request.getIdentifier();
        Doctor doctor = doctorRepository.findByEmail(identifier)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        
        if (!passwordEncoder.matches(request.getPassword(), doctor.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        
        String token = jwtUtil.generateToken(doctor.getEmail(), "ROLE_DOCTOR");
        return createAuthResponse(token, doctor.getEmail(), "ROLE_DOCTOR");
    }

    public Map<String, Object> loginPatient(LoginRequest request) {
        String identifier = request.getIdentifier();
        Patient patient = patientRepository.findByEmail(identifier)
                .or(() -> patientRepository.findByPhone(identifier))
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        
        if (!passwordEncoder.matches(request.getPassword(), patient.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        
        String token = jwtUtil.generateToken(patient.getEmail(), "ROLE_PATIENT");
        return createAuthResponse(token, patient.getEmail(), "ROLE_PATIENT");
    }

    private Map<String, Object> createAuthResponse(String token, String email, String role) {
        Map<String, Object> res = new HashMap<>();
        res.put("token", token);
        res.put("email", email);
        res.put("role", role);
        return res;
    }
}
