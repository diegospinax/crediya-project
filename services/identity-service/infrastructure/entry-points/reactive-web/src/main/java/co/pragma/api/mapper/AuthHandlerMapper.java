package co.pragma.api.mapper;

import co.pragma.api.dto.AuthResponse;
import co.pragma.api.dto.RequestLogin;
import co.pragma.api.dto.RequestRegister;
import co.pragma.model.auth.Auth;
import co.pragma.model.auth.dto.AuthRequest;
import co.pragma.model.auth.valueObject.AuthEmail;
import co.pragma.model.auth.valueObject.AuthPassword;
import co.pragma.model.auth.valueObject.AuthRole;
import co.pragma.model.auth.valueObject.AuthUserId;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class AuthHandlerMapper {

    public Mono<AuthRequest> mapToAuthRequest(RequestLogin requestLogin) {
        Mono<AuthEmail> authEmailMono = AuthEmail.create(requestLogin.email());
        Mono<AuthPassword> authPasswordMono = AuthPassword.create(requestLogin.password());

        return Mono.zip(authEmailMono, authPasswordMono)
                .map(tuple -> new AuthRequest(tuple.getT1(), tuple.getT2()));
    }

    public Mono<Auth> mapToDomain(RequestRegister requestRegister) {
        List<Mono<?>> fieldsMono = Arrays.asList(
                AuthEmail.create(requestRegister.email()),
                AuthPassword.create(requestRegister.password()),
                AuthUserId.create(requestRegister.userId())
        );
        return Mono.zip(fieldsMono, fields -> new Auth(
                null,
                (AuthEmail) fields[0],
                (AuthPassword) fields[1],
                null,
                null,
                (AuthUserId) fields[2],
                null
        ));
    }

    public AuthResponse mapToResponse(Auth auth) {
        return new AuthResponse(
                auth.id().value,
                auth.email().value,
                auth.failedLoginAttempts().value,
                auth.isLocked().value,
                auth.userId().value
        );
    }
}
