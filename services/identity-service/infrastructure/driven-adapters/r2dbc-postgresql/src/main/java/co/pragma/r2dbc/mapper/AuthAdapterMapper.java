package co.pragma.r2dbc.mapper;

import co.pragma.model.auth.Auth;
import co.pragma.model.auth.valueObject.*;
import co.pragma.r2dbc.AuthEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class AuthAdapterMapper {

    public AuthEntity mapToEntity(Auth auth) {
        AuthEntity authEntity = new AuthEntity();
        authEntity.setEmail(auth.email().value);
        authEntity.setPassword(auth.password().value);
        authEntity.setUserId(auth.userId().value);
        return authEntity;
    }

    public Mono<Auth> mapToDomain(AuthEntity authEntity) {
        List<Mono<?>> fieldsMono = Arrays.asList(
                AuthId.create(authEntity.getId()),
                AuthEmail.create(authEntity.getEmail()),
                AuthPassword.create(authEntity.getPassword()),
                AuthFailedAttempts.create(authEntity.getFailedLoginAttempts()),
                AuthIsLocked.create(authEntity.getIsLocked()),
                AuthUserId.create(authEntity.getUserId())
        );

        return Mono.zip(fieldsMono, fields -> new Auth(
                (AuthId) fields[0],
                (AuthEmail) fields[1],
                (AuthPassword) fields[2],
                (AuthFailedAttempts) fields[3],
                (AuthIsLocked) fields[4],
                (AuthUserId) fields[5],
                null
        ));
    }

    public Mono<Auth> mapRegisterToDomain(AuthEntity authEntity) {
        List<Mono<?>> fieldsMono = Arrays.asList(
                AuthId.create(authEntity.getId()),
                AuthEmail.create(authEntity.getEmail()),
                AuthPassword.create(authEntity.getPassword()),
                AuthFailedAttempts.create(0),
                AuthIsLocked.create(false),
                AuthUserId.create(authEntity.getUserId())
        );

        return Mono.zip(fieldsMono, fields -> new Auth(
                (AuthId) fields[0],
                (AuthEmail) fields[1],
                (AuthPassword) fields[2],
                (AuthFailedAttempts) fields[3],
                (AuthIsLocked) fields[4],
                (AuthUserId) fields[5],
                null
        ));
    }
}
