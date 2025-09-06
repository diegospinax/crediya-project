package co.pragma.r2dbc;

import co.pragma.model.auth.Auth;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface AuthR2DBCRepository extends R2dbcRepository<AuthEntity, Long> {
    Mono<AuthEntity> findByEmail(String email);
}
