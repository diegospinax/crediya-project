package co.pragma.model.auth.gateways;

import co.pragma.model.auth.Auth;
import co.pragma.model.auth.valueObject.AuthEmail;
import co.pragma.model.auth.valueObject.AuthId;
import reactor.core.publisher.Mono;

public interface AuthRepository {
    Mono<Auth> register(Auth auth);
    Mono<Auth> findByEmail(AuthEmail email);
    Mono<Auth> findById(AuthId authId);
}
