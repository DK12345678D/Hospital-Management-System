package app.hospital.service;

public interface LogoutService {
    void logout(String authHeader);
    boolean isTokenBlacklisted(String token);
}
