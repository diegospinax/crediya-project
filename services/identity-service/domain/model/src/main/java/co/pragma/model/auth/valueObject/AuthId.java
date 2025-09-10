package co.pragma.model.auth.valueObject;

import co.pragma.model.auth.exception.AuthValidationException;
import reactor.core.publisher.Mono;

public class AuthId extends AuthField<Long> {
    private AuthId(Long value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        if(value == null) {
            return Mono.error(new AuthValidationException("Invalid id provided."));
        }
        return Mono.empty();
    }

    public static Mono<AuthId> create(Long value) {
        AuthId authId = new AuthId(value);
        return authId.validate()
                .thenReturn(authId);
    }
}
