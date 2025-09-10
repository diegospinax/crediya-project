package co.pragma.model.auth.valueObject;

import co.pragma.model.auth.exception.AuthValidationException;
import reactor.core.publisher.Mono;

public class AuthIsLocked extends AuthField<Boolean>{
    private AuthIsLocked(Boolean value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        if (value == null) {
            return Mono.error(new AuthValidationException("Locked validation is required."));
        }
        return Mono.empty();
    }

    public static Mono<AuthIsLocked> create(Boolean value) {
        AuthIsLocked authIsLocked = new AuthIsLocked(value);
        return authIsLocked.validate()
                .thenReturn(authIsLocked);
    }
}
