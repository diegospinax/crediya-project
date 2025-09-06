package co.pragma.api.dto;

public record AuthResponse(
        Long id,
        String email,
        Integer failedLoginAttempts,
        Boolean isLocked,
        Long userId
) {
}
