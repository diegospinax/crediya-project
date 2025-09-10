package co.pragma.model.auth.valueObject;

import co.pragma.model.auth.exception.AuthValidationException;
import reactor.core.publisher.Mono;

public class AuthFailedAttempts extends AuthField<Integer> {
    private AuthFailedAttempts(Integer value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        if (value == null || value < 0 || value > 5) {
            return Mono.error(new AuthValidationException("Invalid login attempts registered."));
        }
        return Mono.empty();
    }

    public static Mono<AuthFailedAttempts> create(Integer value) {
        AuthFailedAttempts authFailedAttempts = new AuthFailedAttempts(value);
        return authFailedAttempts.validate()
                .thenReturn(authFailedAttempts);
    }
}
