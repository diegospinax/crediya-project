package co.pragma.model.loan.gateways;

import co.pragma.model.loan.State;
import co.pragma.model.loan.valueObject.state.StateName;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StateRepository {
    Mono<State> createState(State state);
    Flux<State> findAll();
    Mono<State> findByName(StateName stateName);
}
