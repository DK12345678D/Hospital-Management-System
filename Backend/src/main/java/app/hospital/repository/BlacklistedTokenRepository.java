package app.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.hospital.model.BlacklistedToken;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {
    boolean existsByToken(String token);
}
