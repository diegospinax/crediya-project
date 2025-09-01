package co.pragma.r2dbc.state.mapper;

import co.pragma.model.loan.State;
import co.pragma.model.loan.valueObject.state.StateDescription;
import co.pragma.model.loan.valueObject.state.StateId;
import co.pragma.model.loan.valueObject.state.StateName;
import co.pragma.r2dbc.state.StateEntity;
import co.pragma.r2dbc.state.mapper.support.StateDomainToEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StateAdapterMapper {

    private final StateDomainToEntityMapper mapperToEntity;

    public StateEntity mapToEntity(State state) {
        return mapperToEntity.mapToEntity(state);
    }

    public Mono<State> mapToDomain(StateEntity entity) {
        List<Mono<?>> fieldsMono = Arrays.asList(
                StateId.create(entity.getId()),
                StateName.create(entity.getName()),
                StateDescription.create(entity.getDescription())
        );

        return Mono.zip(fieldsMono, fields -> new State (
                (StateId) fields[0],
                (StateName) fields[1],
                (StateDescription) fields[2]
        ));
    }
}
