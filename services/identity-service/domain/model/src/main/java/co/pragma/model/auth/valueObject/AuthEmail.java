package co.pragma.model.auth.valueObject;

import co.pragma.model.auth.exception.AuthValidationException;
import reactor.core.publisher.Mono;

public class AuthEmail extends AuthField<String> {
    private AuthEmail(String value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (value == null || !value.matches(regex)) {
            return Mono.error(new AuthValidationException("Invalid email registered."));
        }
        return Mono.empty();
    }

    public static Mono<AuthEmail> create(String value) {
        AuthEmail authEmail = new AuthEmail(value);
        return authEmail.validate()
                .thenReturn(authEmail);
    }
}
