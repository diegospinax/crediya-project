package co.pragma.api.dto;

public record RequestRegister(
        String email,
        String password,
        Long userId
) {
}
