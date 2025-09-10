package co.pragma.model.auth.valueObject;

import co.pragma.model.auth.exception.AuthValidationException;
import reactor.core.publisher.Mono;

public class AuthPassword extends AuthField<String> {
    private AuthPassword(String value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        if (value == null || value.isBlank() || value.length() < 8) {
            return Mono.error(new AuthValidationException("Invalid password registered."));
        }
        return Mono.empty();
    }

    public static Mono<AuthPassword> create(String value) {
        AuthPassword authPassword = new AuthPassword(value);
        return authPassword.validate()
                .thenReturn(authPassword);
    }
}
