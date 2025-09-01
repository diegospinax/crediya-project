package co.pragma.model.loan;

import co.pragma.model.loan.valueObject.state.StateDescription;
import co.pragma.model.loan.valueObject.state.StateId;
import co.pragma.model.loan.valueObject.state.StateName;

public record State(
        StateId id,
        StateName name,
        StateDescription description
) {
}
