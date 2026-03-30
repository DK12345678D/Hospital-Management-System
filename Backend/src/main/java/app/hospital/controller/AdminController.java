package app.hospital.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.hospital.dto.response.DashboardResponse;
import app.hospital.model.Admin;
import app.hospital.repository.AdminRepository;
import app.hospital.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	private final AdminRepository adminRepository;
	private final AdminService adminService;

	public AdminController(AdminRepository adminRepository, AdminService adminService) {
		this.adminRepository = adminRepository;
		this.adminService = adminService;
	}

	private Admin getAdminOrThrow(Authentication auth) {
		if (auth == null || !auth.isAuthenticated()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Admin Not authenticated");
		}
		return (Admin) auth.getPrincipal();
	}

	@GetMapping("/dashboard")
	public ResponseEntity<DashboardResponse> getDashboard(Authentication auth) {
		getAdminOrThrow(auth);
		return ResponseEntity.ok(adminService.getDashboardStats());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Admin> getUserById(Authentication auth, @PathVariable Long id) {
		Admin currentUser = getAdminOrThrow(auth);
		if (!currentUser.getId().equals(id)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can not access others profile");
		}
		return ResponseEntity.ok(currentUser);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Admin> updateUser(Authentication auth, @PathVariable Long id,
			@RequestBody Admin updatedUser) {
		Admin currentUser = getAdminOrThrow(auth);
		if (!currentUser.getId().equals(id)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can update only your own profile");
		}

		currentUser.setFullName(updatedUser.getFullName());
		currentUser.setPhoneNumber(updatedUser.getPhoneNumber());
		currentUser.setEmail(updatedUser.getEmail());
		currentUser.setGender(updatedUser.getGender());
		currentUser.setDob(updatedUser.getDob());

		Admin savedUser = adminRepository.save(currentUser);

		return ResponseEntity.ok(savedUser);
	}

}