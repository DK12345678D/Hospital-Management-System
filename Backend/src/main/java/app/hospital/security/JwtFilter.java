package app.hospital.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import app.hospital.model.Admin;
import app.hospital.model.Doctor;
import app.hospital.model.Patient;
import app.hospital.repository.AdminRepository;
import app.hospital.repository.DoctorRepository;
import app.hospital.repository.PatientRepository;
import app.hospital.service.LogoutService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private LogoutService logoutService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String header = req.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {

            String token = header.substring(7);

            try {

                // BLOCK blacklisted tokens
                if (logoutService.isTokenBlacklisted(token)) {
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    res.getWriter().write("Token is invalid or logged out");
                    return;
                }

                String email = jwtUtil.extractEmail(token);
                if (email != null)
                    email = email.trim();
                String role = jwtUtil.extractRole(token);

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    if ("ROLE_ADMIN".equals(role)) {
                        Admin user = adminRepository.findByEmail(email).orElse(null);
                        if (user != null) {
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                    user, null, user.getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        }
                    } else if ("ROLE_PATIENT".equals(role)) {
                        Patient patient = patientRepository.findByEmail(email).orElse(null);
                        if (patient != null) {
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                    patient, null, patient.getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        }
                    } else if ("ROLE_DOCTOR".equals(role)) {
                        Doctor doctor = doctorRepository.findByEmail(email).orElse(null);
                        if (doctor != null) {
                            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                    doctor, null, doctor.getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(authToken);
                        }
                    } else {
                        System.out.println(" Unknown role or record not found for: " + role + " - " + email);
                    }
                }

            } catch (Exception e) {
                System.out.println("Invalid JWT: " + e.getMessage());
            }
        }

        chain.doFilter(req, res);
    }
}
