package co.pragma.usecase;

import co.pragma.model.auth.Auth;
import co.pragma.model.auth.User;
import co.pragma.model.auth.valueObject.AuthPassword;
import co.pragma.model.auth.valueObject.Token;
import co.pragma.model.auth.dto.AuthRequest;
import co.pragma.model.auth.exception.AuthValidationException;
import co.pragma.model.auth.gateways.AuthRepository;
import co.pragma.model.auth.gateways.AuthService;
import co.pragma.model.auth.gateways.UserClient;
import co.pragma.model.auth.valueObject.AuthId;
import co.pragma.model.auth.valueObject.AuthRole;
import co.pragma.usecase.cases.AuthFindByIdUseCase;
import co.pragma.usecase.cases.AuthLoginUseCase;
import co.pragma.usecase.cases.AuthRegisterUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class AuthUseCase implements AuthRegisterUseCase, AuthLoginUseCase, AuthFindByIdUseCase {

    private final AuthRepository authRepository;
    private final UserClient userClient;
    private final AuthService authService;

    @Override
    public Mono<Token> login(AuthRequest authRequest) {
        Mono<Auth> existingUser = authRepository.findByEmail(authRequest.email())
                .switchIfEmpty(Mono.error(new AuthValidationException("User not found.")));
        return existingUser
                .flatMap(auth -> userClient.findUserById(auth.userId())
                    .switchIfEmpty(Mono.error(new AuthValidationException("No user associated with those credentials.")))
                    .flatMap(user -> AuthRole.create(user.role())
                            .map(auth::withRole))
                )
                .flatMap(user -> authService.authenticate(authRequest, user));

    }

    @Override
    public Mono<Auth> register(Auth auth) {
        return authRepository.findByEmail(auth.email())
                .flatMap(existing -> Mono.error(new AuthValidationException("User with email already exists.")))
                .then(userClient.findUserById(auth.userId()))
                        .switchIfEmpty(Mono.error(new AuthValidationException("User does not exists.")))
                .then(authService.encryptPassword(auth.password()))
                .map(auth::withPassword)
                .flatMap(authRepository::register);
    }

    @Override
    public Mono<Auth> findById(AuthId authId) {
        return authRepository.findById(authId)
                .switchIfEmpty(Mono.error(new AuthValidationException("User not found.")));
    }
}
