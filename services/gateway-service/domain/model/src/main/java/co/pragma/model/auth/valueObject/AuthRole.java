package co.pragma.model.auth.valueObject;

import co.pragma.model.auth.enums.Role;
import co.pragma.model.auth.exception.AuthValidationException;
import reactor.core.publisher.Mono;

public class AuthRole extends AuthField<Role>{
    private AuthRole(Role value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        if (value == null) {
            return Mono.error(new AuthValidationException("Role is required."));
        }

        return Mono.empty();
    }

    public static Mono<AuthRole> create(Role value) {
        AuthRole authRole = new AuthRole(value);
        return authRole.validate()
                .thenReturn(authRole);
    }
}
