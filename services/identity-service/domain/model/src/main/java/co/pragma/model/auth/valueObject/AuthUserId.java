package co.pragma.model.auth.valueObject;

import co.pragma.model.auth.exception.AuthValidationException;
import reactor.core.publisher.Mono;

public class AuthUserId extends AuthField<Long>{
    private AuthUserId(Long value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        if (value == null) {
            return Mono.error(new AuthValidationException("User id is required"));
        }
        return Mono.empty();
    }

    public static Mono<AuthUserId> create(Long value) {
        AuthUserId authUserId = new AuthUserId(value);
        return authUserId.validate()
                .thenReturn(authUserId);
    }
}
