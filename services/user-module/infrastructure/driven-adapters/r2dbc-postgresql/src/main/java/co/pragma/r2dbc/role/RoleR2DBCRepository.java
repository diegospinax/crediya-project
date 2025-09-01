package co.pragma.r2dbc.role;

import co.pragma.r2dbc.role.RoleEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface RoleR2DBCRepository extends R2dbcRepository<RoleEntity, Long> {

    Mono<RoleEntity> findByName(String name);
}
