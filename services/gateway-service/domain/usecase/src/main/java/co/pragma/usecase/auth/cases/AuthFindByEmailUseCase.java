package co.pragma.usecase.auth.cases;

import co.pragma.model.auth.Auth;
import co.pragma.model.auth.valueObject.AuthEmail;
import reactor.core.publisher.Mono;

public interface AuthFindByEmailUseCase {
    Mono<Auth> findByEmail(AuthEmail email);
}
