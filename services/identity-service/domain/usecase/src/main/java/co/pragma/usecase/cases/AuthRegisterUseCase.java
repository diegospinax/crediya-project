package co.pragma.usecase.cases;

import co.pragma.model.auth.Auth;
import reactor.core.publisher.Mono;

public interface AuthRegisterUseCase {
    Mono<Auth> register(Auth auth);
}
