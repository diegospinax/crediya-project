package co.pragma.model.auth.valueObject;

import co.pragma.model.auth.exception.AuthValidationException;
import reactor.core.publisher.Mono;

public class AuthRole extends AuthField<String>{
    private AuthRole(String value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        String regex = "^[A-ZÑ]+(?:_[A-ZÑ]+)*$";
        if (value == null || !value.matches(regex)) {
            return Mono.error(new AuthValidationException("Invalid role provided."));
        }

        return Mono.empty();
    }

    public static Mono<AuthRole> create(String value) {
        AuthRole authRole = new AuthRole(value);
        return authRole.validate()
                .thenReturn(authRole);
    }
}
