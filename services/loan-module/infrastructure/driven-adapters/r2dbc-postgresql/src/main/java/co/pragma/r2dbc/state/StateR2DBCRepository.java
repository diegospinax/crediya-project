package co.pragma.r2dbc.state;

import co.pragma.model.loan.valueObject.state.StateId;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface StateR2DBCRepository extends R2dbcRepository<StateEntity, Long> {
    Mono<StateEntity> findByName(String name);
    Mono<StateEntity> findById(Long stateId);
}
