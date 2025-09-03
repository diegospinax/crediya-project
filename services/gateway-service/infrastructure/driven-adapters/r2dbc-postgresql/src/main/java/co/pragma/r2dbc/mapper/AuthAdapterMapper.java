package co.pragma.r2dbc.mapper;

import co.pragma.model.auth.Auth;
import co.pragma.model.auth.valueObject.*;
import co.pragma.r2dbc.AuthEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class AuthAdapterMapper {

    public AuthEntity mapToEntity(Auth auth) {
        return new AuthEntity(
                null,
                auth.email().value,
                auth.password().value,
                auth.failedLoginAttempts().value,
                auth.isLocked().value,
                auth.userId().value,
                auth.role().value
        );
    }

    public Mono<Auth> mapToDomain(AuthEntity authEntity) {
        List<Mono<?>> fieldsMono = Arrays.asList(
                AuthId.create(authEntity.getId()),
                AuthEmail.create(authEntity.getEmail()),
                AuthPassword.create(authEntity.getPassword()),
                AuthFailedAttempts.create(authEntity.getFailedLoginAttempts()),
                AuthIsLocked.create(authEntity.getIsLocked()),
                AuthUserId.create(authEntity.getUserId()),
                AuthRole.create(authEntity.getRole())
        );

        return Mono.zip(fieldsMono, fields -> new Auth(
                (AuthId) fields[0],
                (AuthEmail) fields[1],
                (AuthPassword) fields[2],
                (AuthFailedAttempts) fields[3],
                (AuthIsLocked) fields[4],
                (AuthUserId) fields[5],
                (AuthRole) fields[6]
        ));
    }
}
