package co.pragma.model.loan.valueObject.state;

import co.pragma.model.loan.exception.StateValidationException;
import reactor.core.publisher.Mono;

public class StateId extends StateField<Long> {
    public StateId(Long value) {
        super(value);
    }

    @Override
    public Mono<Void> validate() {
        if (value == null) {
            return Mono.error(new StateValidationException("State id is required."));
        }

        return Mono.empty();
    }

    public static Mono<StateId> create(Long value) {
        StateId id = new StateId(value);
        return id.validate()
                .thenReturn(id);
    }
}
