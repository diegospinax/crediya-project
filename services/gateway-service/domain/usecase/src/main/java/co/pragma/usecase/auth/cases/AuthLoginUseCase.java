package co.pragma.usecase.auth.cases;

import co.pragma.model.auth.Auth;
import reactor.core.publisher.Mono;

public interface AuthLoginUseCase {
    Mono<Auth> login(Auth auth);
}
