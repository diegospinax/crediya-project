package co.pragma.api.dto;

public record RequestLogin(
        String email,
        String password
) {
}
