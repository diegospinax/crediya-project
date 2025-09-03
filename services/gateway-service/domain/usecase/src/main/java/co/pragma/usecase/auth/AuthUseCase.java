package co.pragma.usecase.auth;

import co.pragma.model.auth.Auth;
import co.pragma.model.auth.User;
import co.pragma.model.auth.exception.AuthValidationException;
import co.pragma.model.auth.gateways.AuthRepository;
import co.pragma.model.auth.gateways.UserClient;
import co.pragma.model.auth.valueObject.AuthEmail;
import co.pragma.usecase.auth.cases.AuthFindByEmailUseCase;
import co.pragma.usecase.auth.cases.AuthLoginUseCase;
import co.pragma.usecase.auth.cases.AuthRegisterUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class AuthUseCase implements AuthRegisterUseCase, AuthLoginUseCase, AuthFindByEmailUseCase {

    private final AuthRepository authRepository;
    private final UserClient userClient;

    @Override
    public Mono<Auth> login(Auth auth) {
        return authRepository.findByEmail(auth.email())
                .switchIfEmpty(Mono.error(new AuthValidationException("User not found.")))
                .flatMap(existingAuth -> {
                    if (existingAuth.failedLoginAttempts().value == 5)
                        return Mono.error(new AuthValidationException("Too many login attempts."));
                    else
                        return Mono.just(existingAuth);
                })
                .flatMap(existingAuth -> {
                    if (existingAuth.password().value.equals(auth.password().value))
                        return Mono.just(existingAuth);
                    else
                        return Mono.error(new AuthValidationException("Invalid credentials."));
                });
    }

    @Override
    public Mono<Auth> register(Auth auth) {
        return authRepository.findByEmail(auth.email())
                .flatMap(existing -> Mono.error(new AuthValidationException("User with email already exists.")))
                .then(userClient.findUserById(auth.userId()))
                        .switchIfEmpty(Mono.error(new AuthValidationException("User does not exists.")))
                .then(authRepository.register(auth));

    }

    @Override
    public Mono<Auth> findByEmail(AuthEmail email) {
        return authRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new AuthValidationException("User not found.")));
    }
}
