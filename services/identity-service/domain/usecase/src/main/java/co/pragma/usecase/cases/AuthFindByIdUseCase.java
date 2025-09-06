package co.pragma.usecase.cases;

import co.pragma.model.auth.Auth;
import co.pragma.model.auth.valueObject.AuthId;
import reactor.core.publisher.Mono;

public interface AuthFindByIdUseCase {
    Mono<Auth> findById(AuthId authId);
}
