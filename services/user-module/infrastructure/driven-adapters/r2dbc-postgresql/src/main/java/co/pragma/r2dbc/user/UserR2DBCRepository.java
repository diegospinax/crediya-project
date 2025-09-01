package co.pragma.r2dbc.user;

import co.pragma.r2dbc.user.UserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserR2DBCRepository extends R2dbcRepository<UserEntity, Long> {

    Mono<UserEntity> findByEmail(String email);

    Mono<UserEntity> findByDocument(String document);

    @Query("SELECT * from users ORDER BY id")
    Flux<UserEntity> findAll();
}
