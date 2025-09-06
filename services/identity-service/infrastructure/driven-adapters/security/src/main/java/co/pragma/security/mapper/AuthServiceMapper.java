package co.pragma.security.mapper;

import co.pragma.model.auth.Auth;
import co.pragma.security.userdetails.AuthDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthServiceMapper {

    public Mono<AuthDetails> mapToUserDetails(Auth auth) {
        return Mono.just(new AuthDetails (
                auth.id().value,
                auth.email().value,
                auth.password().value,
                auth.failedLoginAttempts().value,
                auth.isLocked().value,
                auth.userId().value,
                auth.role().value
        ));
    }
}
