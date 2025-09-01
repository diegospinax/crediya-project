package co.pragma.r2dbc.state.mapper.support;

import co.pragma.model.loan.State;
import co.pragma.r2dbc.state.StateEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StateDomainToEntityMapper {

    @StateMapDomainToEntity
    StateEntity mapToEntity(State state);
}
