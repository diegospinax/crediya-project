package co.pragma.r2dbc.state;

import co.pragma.model.loan.State;
import co.pragma.model.loan.gateways.StateRepository;
import co.pragma.model.loan.valueObject.state.StateId;
import co.pragma.model.loan.valueObject.state.StateName;
import co.pragma.r2dbc.state.mapper.StateAdapterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class StateRepositoryAdapter implements StateRepository {

    private final StateR2DBCRepository stateRepository;
    private final StateAdapterMapper adapterMapper;

    @Override
    public Mono<State> createState(State state) {
        StateEntity entity = adapterMapper.mapToEntity(state);
        return stateRepository.save(entity)
                .flatMap(adapterMapper::mapToDomain);
    }

    @Override
    public Flux<State> findAll() {
        return stateRepository.findAll()
                .flatMap(adapterMapper::mapToDomain);
    }

    @Override
    public Mono<State> findByName(StateName stateName) {
        return stateRepository.findByName(stateName.value)
                .flatMap(adapterMapper::mapToDomain);
    }

    @Override
    public Mono<State> findById(StateId stateId) {
        return stateRepository.findById(stateId.value)
                .flatMap(adapterMapper::mapToDomain);
    }
}
