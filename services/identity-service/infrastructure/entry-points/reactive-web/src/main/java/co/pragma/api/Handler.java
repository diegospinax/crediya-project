package co.pragma.api;

import co.pragma.api.dto.RequestLogin;
import co.pragma.api.dto.RequestRegister;
import co.pragma.api.dto.TokenResponse;
import co.pragma.api.mapper.AuthHandlerMapper;
import co.pragma.model.auth.exception.AuthValidationException;
import co.pragma.model.auth.valueObject.AuthEmail;
import co.pragma.model.auth.valueObject.AuthId;
import co.pragma.usecase.cases.AuthFindByIdUseCase;
import co.pragma.usecase.cases.AuthLoginUseCase;
import co.pragma.usecase.cases.AuthRegisterUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class Handler {

    private final AuthRegisterUseCase registerUseCase;
    private final AuthLoginUseCase loginUseCase;
    private final AuthFindByIdUseCase findByIdUseCase;
    private final AuthHandlerMapper handlerMapper;

    public Mono<ServerResponse> login(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RequestLogin.class)
                .flatMap(handlerMapper::mapToAuthRequest)
                .flatMap(loginUseCase::login)
                .flatMap(token -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(new TokenResponse(token.value)));
    }

    public Mono<ServerResponse> register(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RequestRegister.class)
                .flatMap(handlerMapper::mapToDomain)
                .flatMap(registerUseCase::register)
                .flatMap(auth ->  {
                    URI location = UriComponentsBuilder
                            .fromPath("/auth/{id}")
                            .buildAndExpand(auth.id().value)
                            .toUri();

                    return ServerResponse.created(location).build();
                });
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        return AuthId.create(Long.parseLong(serverRequest.pathVariable("id")))
                .flatMap(findByIdUseCase::findById)
                .flatMap(auth -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(handlerMapper.mapToResponse(auth)))
                .onErrorResume(AuthValidationException.class, e -> ServerResponse.notFound().build());
    }
}
